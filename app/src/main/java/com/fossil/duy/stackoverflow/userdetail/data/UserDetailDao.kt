package com.fossil.duy.stackoverflow.userdetail.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity

/**
 * The Data Access Object for the @{UserDetailEntity} class.
 */
@Dao
interface UserDetailDao {

    @Query("SELECT * FROM user_details WHERE user_id = :userId ORDER BY creation_date DESC")
    fun getUserDetails(userId: Long): LiveData<List<UserDetailEntity>>

    @Query("SELECT * FROM user_details WHERE user_id = :userId ORDER BY creation_date DESC")
    fun getPagedUserDetailsByUserId(userId: Long): DataSource.Factory<Int, UserDetailEntity>

    @Query("SELECT * FROM user_details ORDER BY creation_date DESC")
    fun getPagedUserDetails(): DataSource.Factory<Int, UserDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userDetails: List<UserDetailEntity>)

}
