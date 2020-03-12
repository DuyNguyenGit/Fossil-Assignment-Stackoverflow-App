package com.fossil.duy.stackoverflow.di

import android.content.Context
import com.ashleyfigueira.domain.di.ApplicationContext
import com.fossil.duy.stackoverflow.App
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun provideContext(application: App): Context
}