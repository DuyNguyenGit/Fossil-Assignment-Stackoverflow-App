package com.fossil.duy.stackoverflow.di

import android.content.Context
import androidx.room.Room
import com.ashleyfigueira.domain.di.ApplicationContext
import com.ashleyfigueira.domain.di.PerApplication
import com.fossil.duy.stackoverflow.api.UserService
import com.fossil.duy.stackoverflow.database.AppDatabase
import com.fossil.duy.stackoverflow.di.annotations.CoroutineScropeIO
import com.fossil.duy.stackoverflow.userdetail.data.UserDetailsRemoteDataSource
import com.fossil.duy.stackoverflow.userdetail.data.UserDetailsRepository
import com.fossil.duy.stackoverflow.users.data.UsersDao
import com.fossil.duy.stackoverflow.users.data.UsersRemoteDataSource
import com.fossil.duy.stackoverflow.users.data.UsersRepository
import com.fossil.duy.stackoverflow.users.models.UsersEntityMapper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [CoreDataModule::class])
class DataModule {

    @PerApplication
    @Provides
    fun provideUsersRemoteDataSource(userService: UserService) = UsersRemoteDataSource(userService)

    @PerApplication
    @Provides
    fun provideUserDetailsRemoteDataSource(userService: UserService) =
        UserDetailsRemoteDataSource(userService)

    @PerApplication
    @Provides
    fun provideUsersDao(stackDatabase: AppDatabase): UsersDao = stackDatabase.usersDao()

    @PerApplication
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java,
            AppDatabase.STACK_DB
        ).build()

    @PerApplication
    @Provides
    fun provideUsersEntityMapper(userDao: UsersDao): UsersEntityMapper = UsersEntityMapper(userDao)

    @PerApplication
    @Provides
    fun provideUsersRepository(
        userDao: UsersDao, dataSource: UsersRemoteDataSource,
        entityMapper: UsersEntityMapper
    ): UsersRepository = UsersRepository(userDao, dataSource, entityMapper)

    @PerApplication
    @Provides
    fun provideUserDetailsRepository(
        userDao: UsersDao, dataSource: UserDetailsRemoteDataSource
    ): UserDetailsRepository = UserDetailsRepository(userDao, dataSource)

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @PerApplication
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(UserService.ENDPOINT)
        .build()

    @PerApplication
    @Provides
    fun provideUsersService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}
