package com.aamernabi.moments.di

import com.aamernabi.moments.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainFragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity

}