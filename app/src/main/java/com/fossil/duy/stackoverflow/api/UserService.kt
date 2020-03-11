package com.fossil.duy.stackoverflow.api

import com.fossil.duy.stackoverflow.users.models.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * REST API access points
 */
interface UserService {

    companion object {
        const val ENDPOINT = "https://api.stackexchange.com/2.2/"
        const val site_parameter = "stackoverflow"
    }

    @GET("users/")
    suspend fun getUsers(
        @Query("page") page: Int? = null,
        @Query("pagesize") pageSize: Int? = null,
        @Query("site") site: String? = site_parameter
    ): Response<UsersResponse>

}
