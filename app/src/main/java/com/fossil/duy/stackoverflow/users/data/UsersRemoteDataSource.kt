package com.fossil.duy.stackoverflow.users.data

import com.fossil.duy.stackoverflow.api.UserService
import com.fossil.duy.stackoverflow.base.BaseDataSource
import javax.inject.Inject

/**
 * Works with the stackoverflow API to get data.
 */
class UsersRemoteDataSource @Inject constructor(private val service: UserService) :
    BaseDataSource() {

    suspend fun fetchData(page: Page) = getResult { service.getUsers(page.page, page.pageSize) }

}
