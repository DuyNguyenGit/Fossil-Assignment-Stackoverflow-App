package com.fossil.duy.stackoverflow.di

import com.ashleyfigueira.domain.di.PerApplication
import com.fossil.duy.stackoverflow.App
import com.fossil.duy.stackoverflow.database.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        ActivityProvider::class]
)
@PerApplication
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
