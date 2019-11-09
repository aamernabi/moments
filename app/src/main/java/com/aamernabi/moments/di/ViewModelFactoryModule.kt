package com.aamernabi.moments.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelfactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}