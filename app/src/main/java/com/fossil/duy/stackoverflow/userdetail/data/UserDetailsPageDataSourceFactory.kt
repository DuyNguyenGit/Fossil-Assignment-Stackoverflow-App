package com.fossil.duy.stackoverflow.userdetail.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserDetailsPageDataSourceFactory @Inject constructor(
    private val userId: Long? = null,
    private val dataSource: UserDetailsRemoteDataSource,
    private val dao: UserDetailDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, UserDetailEntity>() {

    private val liveData = MutableLiveData<UserDetailsPageDataSource>()

    override fun create(): DataSource<Int, UserDetailEntity> {
        val source = UserDetailsPageDataSource(userId, dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 30

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
    }

}