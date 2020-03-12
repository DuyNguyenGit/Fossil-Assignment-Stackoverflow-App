package com.fossil.duy.stackoverflow.userdetail.data

import com.fossil.duy.stackoverflow.api.UserService
import com.fossil.duy.stackoverflow.base.BaseDataSource
import javax.inject.Inject

/**
 * Works with the User Details API to get data.
 */
class UserDetailsRemoteDataSource @Inject constructor(private val service: UserService) :
    BaseDataSource() {

    suspend fun fetchUserDetails(userId: String, page: Int, pageSize: Int) =
        getResult { service.getUserDetails(userId, page, pageSize) }

}
