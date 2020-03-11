package com.fossil.duy.stackoverflow.di

import com.fossil.duy.stackoverflow.users.views.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeThemeFragment(): UserListFragment

}
