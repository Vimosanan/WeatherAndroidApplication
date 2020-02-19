package com.vimosanan.weatherandroidapplication.di

import android.app.Application
import com.vimosanan.weatherandroidapplication.app.WeatherApplication
import com.vimosanan.weatherandroidapplication.network.ApiInterface
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
                        AndroidSupportInjectionModule::class,
                        AppModule::class,
                        ViewModelFactoryModule::class,
                        ActivityBuilderModule::class
                            ])
interface AppComponent: AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun getApiInterface(): ApiInterface

}