package com.aamernabi.moments.di

import com.aamernabi.moments.views.MainActivity
import com.aamernabi.moments.views.fragment.FullScreenFragment
import com.aamernabi.moments.views.fragment.PhotosFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface PhotosComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PhotosComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: FullScreenFragment)
    fun inject(fragment: PhotosFragment)
}