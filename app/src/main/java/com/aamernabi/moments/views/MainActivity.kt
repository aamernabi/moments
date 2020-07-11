/*
 * Copyright 2020 aamernabi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamernabi.moments.views

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.aamernabi.core.utils.createUri
import com.aamernabi.core.utils.createUriPreQ
import com.aamernabi.core.utils.delegates.viewBinding
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.ActivityMainBinding
import com.aamernabi.moments.utils.extensions.showSnackbar
import com.aamernabi.moments.viewmodels.PhotosViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotosViewModel> { factory }

    private val bindings by viewBinding(ActivityMainBinding::inflate)

    private var downloadId: Long? = null

    private val downloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {
                showSnackbar("Download Completed", bindings.root)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)
        setSupportActionBar(bindings.toolbar)

        bindings.toolbar.setBackgroundResource(R.drawable.bg_app_bar)
        viewModel.downloadImage.observe(this, ::onDownloadImage)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                downloadReceiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadReceiver)
    }

    fun hideToolbar() {
        bindings.appBar.setExpanded(false, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_dark_theme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.action_light_theme -> {
                /*showMaterialDialog(
                    this,
                    "Verify your identity",
                    "Login using fingerprint authentication."
                )*/
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun onDownloadImage(url: String) {
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)

        val fileName = uri.path
            ?.replace("\\", "")
            ?.plus(".${uri.getQueryParameter("fm")}") ?: return

        val fileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            createUri(this, fileName)
        } else {
            createUriPreQ(fileName)
        }

        val request = DownloadManager.Request(uri)
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(VISIBILITY_VISIBLE)
            .setDestinationUri(fileUri)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        downloadId = dm.enqueue(request)
        showSnackbar("Download Started", bindings.root)
    }
}
