package com.aamernabi.moments.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aamernabi.moments.R
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var viewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)
        viewModel.getPhotos()

        attachObservers()
    }

    private fun attachObservers() {
        viewModel.photos.observe(this, Observer {
            it ?: return@Observer
            recycler_view.adapter = PhotoAdapter(this, it)
            recycler_view.adapter?.notifyDataSetChanged()
        })
    }

    override fun onItemClick(index: Int) {

    }
}
