package com.vimosanan.weatherandroidapplication.di

import androidx.lifecycle.ViewModelProvider
import com.vimosanan.weatherandroidapplication.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProvidersFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}