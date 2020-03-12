package com.fossil.duy.stackoverflow.userdetail.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import com.fossil.duy.stackoverflow.users.models.UserDomain
import com.fossil.duy.stackoverflow.users.models.asDomainModel
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class UserDetailsRepository @Inject constructor(
    private val dao: UserDetailDao,
    private val userDetailsRemoteDataSource: UserDetailsRemoteDataSource
) {

//    val cache: LiveData<UserDomain> = Transformations.map(dao.getUsers()) {
//        it.asDomainModel()
//    }

    fun observePagedUserDetails(
        connectivityAvailable: Boolean, userId: Long? = null,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<UserDetailEntity>> {
        val result = if (connectivityAvailable) observeRemotePagedUserDetails(userId, coroutineScope)
        else observeLocalPagedUserDetails(userId)
        Timber.d(result.value?.size.toString())
        return result
    }


    private fun observeLocalPagedUserDetails(userId: Long? = null): LiveData<PagedList<UserDetailEntity>> {
        Timber.d("DUY: NO NETWORK")
        val dataSourceFactory =
            if (userId == null) dao.getPagedUserDetails()
            else dao.getPagedUserDetailsByUserId(userId)

        return LivePagedListBuilder(
            dataSourceFactory,
            UserDetailsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedUserDetails(userId: Long? = null, ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<UserDetailEntity>> {
        Timber.d("DUY: HAS NETWORK")
        val dataSourceFactory = UserDetailsPageDataSourceFactory(
            userId, userDetailsRemoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            UserDetailsPageDataSourceFactory.pagedListConfig()
        ).build()
    }
}