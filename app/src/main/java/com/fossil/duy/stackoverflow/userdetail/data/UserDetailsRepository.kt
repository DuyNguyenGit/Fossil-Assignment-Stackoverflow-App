package com.fossil.duy.stackoverflow.userdetail.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import com.fossil.duy.stackoverflow.users.data.UsersDao
import com.fossil.duy.stackoverflow.users.models.UserDomain
import com.fossil.duy.stackoverflow.users.models.mapToUserDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class UserDetailsRepository @Inject constructor(
    private val dao: UsersDao,
    private val userDetailsRemoteDataSource: UserDetailsRemoteDataSource
) {

    fun getCachedUser(userId: Long): LiveData<UserDomain> =
        Transformations.map(dao.getUser(userId)) {
            it.mapToUserDomain()
        }

    fun updateUserBookmark(userId: Long): LiveData<UserDomain> = liveData(Dispatchers.IO) {
        val source = dao.getUserNoLiveData(userId)
        source.isBookmarked = !source.isBookmarked
        dao.update(source)
        emit(source.mapToUserDomain())
    }

    fun observePagedUserDetails(
        connectivityAvailable: Boolean, userId: Long? = null,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<UserDetailEntity>> {
        val result =
            if (connectivityAvailable) observeRemotePagedUserDetails(userId, coroutineScope)
            else observeLocalPagedUserDetails(userId)
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

    private fun observeRemotePagedUserDetails(
        userId: Long? = null,
        ioCoroutineScope: CoroutineScope
    )
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