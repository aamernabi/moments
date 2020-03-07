package com.aamernabi.moments

import android.app.Application
import com.aamernabi.moments.di.AppComponent
import com.aamernabi.moments.di.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}