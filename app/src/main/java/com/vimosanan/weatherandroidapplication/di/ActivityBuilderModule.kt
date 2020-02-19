package com.vimosanan.weatherandroidapplication.di

import com.vimosanan.weatherandroidapplication.di.main.WeatherViewModelModule
import com.vimosanan.weatherandroidapplication.ui.main.WeatherActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [
                                    WeatherViewModelModule::class
                                                        ])
    abstract fun contributeWeatherActivity():WeatherActivity

}