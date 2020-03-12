package com.fossil.duy.stackoverflow.userdetail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fossil.duy.stackoverflow.di.annotations.CoroutineScropeIO
import com.fossil.duy.stackoverflow.userdetail.data.UserDetailsRepository
import com.fossil.duy.stackoverflow.users.data.Page
import com.fossil.duy.stackoverflow.users.models.UserDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository,
    @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var userId: Long? = null

    val user by lazy {
        userDetailsRepository.getCachedUser(userId!!)
    }
    val userDetails by lazy {
        userDetailsRepository.observePagedUserDetails(
            connectivityAvailable, userId, ioCoroutineScope
        )
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}