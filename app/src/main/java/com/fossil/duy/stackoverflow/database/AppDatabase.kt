package com.fossil.duy.stackoverflow.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fossil.duy.stackoverflow.userdetail.data.UserDetailDao
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import com.fossil.duy.stackoverflow.users.data.UsersDao
import com.fossil.duy.stackoverflow.users.models.UserEntity

/**
 * The Room database for this app
 */
@Database(
    entities = [UserEntity::class,
        UserDetailEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun userDetailsDao(): UserDetailDao

    companion object {
        const val STACK_DB = "STACKOVERFLOW"
    }
}
