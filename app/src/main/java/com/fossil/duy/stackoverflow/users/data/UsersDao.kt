package com.fossil.duy.stackoverflow.users.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fossil.duy.stackoverflow.users.models.UserEntity

/**
 * The Data Access Object for the User class.
 */
@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<UserEntity>)
}
