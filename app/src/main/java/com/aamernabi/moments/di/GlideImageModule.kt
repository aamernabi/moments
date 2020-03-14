package com.aamernabi.moments.di

import android.content.Context
import com.aamernabi.moments.App
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named

/**
 * Moments
 * Created by Aamer on 8/11/2018.
 */
@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class GlideImageModule : AppGlideModule() {
    @Inject @Named("GlideModule")
    lateinit var okHttpClient: OkHttpClient

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        (context.applicationContext as App).androidInjector().inject(this)

        val factory = OkHttpUrlLoader.Factory(okHttpClient)
        registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}