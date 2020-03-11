package com.fossil.duy.stackoverflow.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fossil.duy.stackoverflow.users.data.UsersDao
import com.fossil.duy.stackoverflow.users.models.UserEntity

/**
 * The Room database for this app
 */
@Database(
    entities = [UserEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {
        const val STACK_DB = "stack_db"
    }
}
