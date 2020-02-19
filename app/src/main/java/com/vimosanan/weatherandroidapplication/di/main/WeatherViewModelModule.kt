package com.vimosanan.weatherandroidapplication.di.main

import androidx.lifecycle.ViewModel
import com.vimosanan.weatherandroidapplication.di.ViewModelKey
import com.vimosanan.weatherandroidapplication.ui.main.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WeatherViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel
}