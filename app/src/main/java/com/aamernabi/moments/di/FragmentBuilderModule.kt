package com.aamernabi.moments.di

import com.aamernabi.moments.views.fragment.FullScreenFragment
import com.aamernabi.moments.views.fragment.PhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributePhotosFragment(): PhotosFragment

    @ContributesAndroidInjector
    abstract fun contributeFullScreenFragment(): FullScreenFragment

}