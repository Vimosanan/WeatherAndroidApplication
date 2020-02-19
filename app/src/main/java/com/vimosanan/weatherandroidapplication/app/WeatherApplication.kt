package com.vimosanan.weatherandroidapplication.app

import com.vimosanan.weatherandroidapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class WeatherApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {

        return DaggerAppComponent.builder().application(this).build()
    }
}