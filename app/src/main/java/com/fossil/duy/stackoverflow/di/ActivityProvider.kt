package com.fossil.duy.stackoverflow.di

import android.app.Activity
import com.fossil.duy.stackoverflow.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityProvider {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class,
            ViewModelModule::class,
            MainActivityModule::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity
}

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun bindActivity(activity: MainActivity): Activity
}
