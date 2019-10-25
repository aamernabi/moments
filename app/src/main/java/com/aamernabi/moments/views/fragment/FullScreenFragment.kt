package com.aamernabi.moments.views.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aamernabi.moments.R
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.MainActivity
import com.aamernabi.moments.views.adapter.FullScreenAdapter
import kotlinx.android.synthetic.main.fragment_full_screen.*

class FullScreenFragment : Fragment() {

    private val viewModel: PhotosViewModel? by lazy {
        ViewModelProviders.of(activity ?: return@lazy null).get(PhotosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photos = viewModel?.photos?.value ?: return
        view_pager.adapter = FullScreenAdapter().apply { addAll(photos) }
        view_pager.currentItem = viewModel?.currentIndex ?: 0

        attachPhotosObserver()
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)?.hideToolbar()
    }

    private fun attachPhotosObserver() {
        viewModel?.photos?.observe(this, Observer { photos ->
            (view_pager.adapter as FullScreenAdapter?)?.addAll(photos ?: return@Observer)
        })
    }
}
