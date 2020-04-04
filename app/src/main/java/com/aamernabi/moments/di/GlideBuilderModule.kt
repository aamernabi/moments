package com.aamernabi.moments.di

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GlideBuilderModule {

    @ContributesAndroidInjector
    abstract fun bind(): GlideImageModule

}