package com.aamernabi.moments.views.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aamernabi.moments.R
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment(), OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(this)
        recycler_view.adapter = adapter

        attachObservers(adapter)
    }

    private fun attachObservers(adapter: PhotoAdapter) {
        val viewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)
        viewModel.photos.observe(this, Observer {
            it ?: return@Observer
            adapter.submitList(it)
        })
    }

    override fun onItemClick(index: Int) {

    }
}
