package com.fossil.duy.stackoverflow.users.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import com.fossil.duy.stackoverflow.users.models.UserEntity

/**
 * The Data Access Object for the User class.
 */
@Dao
interface UsersDao {

    // This is queries for User List
    @Query("SELECT * FROM users ORDER BY reputation DESC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Long): LiveData<UserEntity>

    @Query("SELECT * from users WHERE id = :id")
    suspend fun getUserNoLiveData(id: Long): UserEntity

    @Query("SELECT * from users WHERE id = :id")
    fun getUserNoLiveDataNoSuspend(id: Long): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(plants: List<UserEntity>)

    @Update
    suspend fun update(user: UserEntity)
    //**********************


    // This is queries for user details
    @Query("SELECT * FROM user_details WHERE user_id = :userId ORDER BY creation_date DESC")
    fun getUserDetails(userId: Long): LiveData<List<UserDetailEntity>>

    @Query("SELECT * FROM user_details WHERE user_id = :userId ORDER BY creation_date DESC")
    fun getPagedUserDetailsByUserId(userId: Long): DataSource.Factory<Int, UserDetailEntity>

    @Query("SELECT * FROM user_details ORDER BY creation_date DESC")
    fun getPagedUserDetails(): DataSource.Factory<Int, UserDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUserDetails(userDetails: List<UserDetailEntity>)
    //**********************
}
