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
import com.aamernabi.moments.databinding.FragmentPhotosBinding
import com.aamernabi.moments.di.Injectable
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.utils.State
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import com.aamernabi.moments.views.bindings.viewBinding
import javax.inject.Inject

class PhotosFragment : Fragment(), OnItemClickListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel

    private val binding by viewBinding(FragmentPhotosBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(this)
        binding.recyclerView.adapter = adapter


        activity?.let { activity ->
            viewModel =
                ViewModelProvider(activity, viewModelFactory).get(PhotosViewModel::class.java)
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
        binding.progressBar.visibility = View.GONE
        binding.tvNoInternet.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showProgress() {
        binding.recyclerView.visibility = View.GONE
        binding.tvNoInternet.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onError(message: String?, errorCode: Int?) {
        binding.progressBar.visibility = View.GONE


        if (errorCode != Errors.NO_DATA) {
            binding.recyclerView.visibility = View.GONE
            binding.tvNoInternet.visibility = View.VISIBLE
            binding.tvNoInternet.text =
                if (message.isNullOrEmpty()) binding.tvNoInternet.text else message
        }
    }

}
