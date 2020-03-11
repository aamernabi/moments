package com.aamernabi.moments.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.ActivityMainBinding
import com.aamernabi.moments.showMaterialDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var bindings: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        setSupportActionBar(bindings.toolbar)

        bindings.toolbar.setBackgroundResource(R.drawable.bg_app_bar)
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
            /*R.id.new_game -> {
                newGame()
                true
            }
            R.id.help -> {
                showHelp()
                true
            }*/
            R.id.action_light_theme -> {
                showMaterialDialog(
                    this,
                    "Verify your identity",
                    "Login using fingerprint authentication."
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}
