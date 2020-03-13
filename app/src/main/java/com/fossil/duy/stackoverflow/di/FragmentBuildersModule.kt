package com.fossil.duy.stackoverflow.di

import com.fossil.duy.stackoverflow.setting.SettingFragment
import com.fossil.duy.stackoverflow.userdetail.views.UserDetailFragment
import com.fossil.duy.stackoverflow.users.views.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserDetailFragment(): UserDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingFragment

}
