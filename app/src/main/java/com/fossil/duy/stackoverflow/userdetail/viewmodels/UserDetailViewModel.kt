package com.fossil.duy.stackoverflow.userdetail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.fossil.duy.stackoverflow.di.annotations.CoroutineScropeIO
import com.fossil.duy.stackoverflow.userdetail.data.UserDetailsRepository
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

    private val user = MediatorLiveData<UserDomain>()
    fun getUser(): LiveData<UserDomain> {
        return user
    }

    fun loadUser() {
        user.addSource(userDetailsRepository.getCachedUser(userId!!)) {
            user.postValue(it)
        }
    }

    fun bookmark() {
        user.addSource(userDetailsRepository.updateUserBookmark(userId!!)) {
            user.postValue(it)
        }
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