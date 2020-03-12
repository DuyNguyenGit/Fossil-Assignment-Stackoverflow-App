package com.fossil.duy.stackoverflow.users.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fossil.duy.stackoverflow.database.resultLiveData
import com.fossil.duy.stackoverflow.users.models.UserDomain
import com.fossil.duy.stackoverflow.users.models.UsersEntityMapper
import com.fossil.duy.stackoverflow.users.models.asDomainModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class UsersRepository @Inject constructor(private val dao: UsersDao,
                                          private val remoteSource: UsersRemoteDataSource,
                                          private val usersEntityMapper: UsersEntityMapper) {

    var page: Page = Page()
        set(value) {field = value}
    val cache: LiveData<List<UserDomain>> = Transformations.map(dao.getUsers()) {
        it.asDomainModel()
    }
    val users = resultLiveData(
            databaseQuery = { cache },
            networkCall = { remoteSource.fetchData(page) },
            saveCallResult = { dao.insertAllUser(usersEntityMapper.mapFrom(it)) })

}
