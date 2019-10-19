package com.aamernabi.moments.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aamernabi.moments.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        toolbar.setBackgroundResource(R.drawable.bg_app_bar)
    }

    fun hideToolbar() {
        app_bar.setExpanded(false, false)
    }

}
