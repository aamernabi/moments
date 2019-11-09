package com.aamernabi.moments.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aamernabi.moments.App
import com.aamernabi.moments.R
import com.aamernabi.moments.di.PhotosComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var photosComponent: PhotosComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        photosComponent = (application as App).appComponent.photosComponent().create()
        photosComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        toolbar.setBackgroundResource(R.drawable.bg_app_bar)
    }

    fun hideToolbar() {
        app_bar.setExpanded(false, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            /*R.id.new_game -> {
                newGame()
                true
            }
            R.id.help -> {
                showHelp()
                true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
    }


}
