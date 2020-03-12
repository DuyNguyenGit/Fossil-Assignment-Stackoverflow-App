package com.fossil.duy.stackoverflow.userdetail.data

import androidx.paging.PageKeyedDataSource
import com.fossil.duy.stackoverflow.database.DataResult
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for user details pagination via paging library
 */
class UserDetailsPageDataSource @Inject constructor(
    private val userId: Long? = null,
    private val dataSource: UserDetailsRemoteDataSource,
    private val dao: UserDetailDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, UserDetailEntity>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserDetailEntity>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserDetailEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UserDetailEntity>
    ) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<UserDetailEntity>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchUserDetails(userId.toString(), page, pageSize)
            if (response.status == DataResult.Status.SUCCESS) {
                val results = response.data!!.userDetails
                dao.insertAll(results)
                callback(results)
            } else if (response.status == DataResult.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
//        networkState.postValue(NetworkState.FAILED)
    }

}