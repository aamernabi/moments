package com.aamernabi.moments.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aamernabi.moments.R
import com.aamernabi.moments.di.Injectable
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.utils.State
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.fragment_photos.*
import javax.inject.Inject

class PhotosFragment : Fragment(), OnItemClickListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel

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

        activity?.let { activity ->
            viewModel = ViewModelProvider(activity, viewModelFactory).get(PhotosViewModel::class.java)
        }

        attachPhotosObservers(adapter)
        attachStateObserver()
    }

    private fun attachPhotosObservers(adapter: PhotoAdapter) {
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            adapter.submitList(it)
        })
    }

    private fun attachStateObserver() {
        val activity = activity ?: return
        viewModel.photosState.observe(activity, Observer { state ->
            when (state) {
                is State.Loading -> showProgress()
                is State.Success -> onSuccess()
                is State.Error -> onError(state.message, state.errorCode)
            }
        })
    }

    override fun onItemClick(index: Int) {
        viewModel.currentIndex = index
        val navController = Navigation.findNavController(activity ?: return, R.id.nav_host_fragment)
        navController.navigate(R.id.fullScreenFragment)
    }


    private fun onSuccess() {
        progress_bar.visibility = View.GONE
        tv_no_internet.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    private fun showProgress() {
        recycler_view.visibility = View.GONE
        tv_no_internet.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun onError(message: String?, errorCode: Int?) {
        progress_bar.visibility = View.GONE


        if (errorCode != Errors.NO_DATA) {
            recycler_view.visibility = View.GONE
            tv_no_internet.visibility = View.VISIBLE
            tv_no_internet.text = if (message.isNullOrEmpty()) tv_no_internet.text else message
        }
    }

}
