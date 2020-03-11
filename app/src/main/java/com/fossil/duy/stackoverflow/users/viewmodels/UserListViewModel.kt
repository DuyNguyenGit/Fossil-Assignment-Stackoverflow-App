package com.fossil.duy.stackoverflow.users.viewmodels

import androidx.lifecycle.ViewModel
import com.fossil.duy.stackoverflow.users.data.UsersRepository
import javax.inject.Inject

class UserListViewModel @Inject constructor(usersRepository: UsersRepository): ViewModel() {

    val users = usersRepository.users
}