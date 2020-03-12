package com.fossil.duy.stackoverflow.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fossil.duy.stackoverflow.userdetail.viewmodels.UserDetailViewModel
import com.fossil.duy.stackoverflow.users.viewmodels.UserListViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(viewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun bindUserDetailViewModel(viewModel: UserDetailViewModel): ViewModel

}
