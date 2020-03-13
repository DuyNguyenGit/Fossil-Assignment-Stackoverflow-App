package com.fossil.duy.stackoverflow.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> DataResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<DataResult<T>> = liveData(Dispatchers.IO) {
    emit(DataResult.loading<T>())
    val source = databaseQuery.invoke().map { DataResult.success(it) }
    emitSource(source)

    val responseStatus = networkCall.invoke()
    if (responseStatus.status == DataResult.Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == DataResult.Status.ERROR) {
        emit(DataResult.error<T>(responseStatus.message!!))
        emitSource(source)
    }
}