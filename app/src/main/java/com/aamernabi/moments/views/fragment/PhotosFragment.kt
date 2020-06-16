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

package com.aamernabi.moments.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aamernabi.core.data.State
import com.aamernabi.core.utils.delegates.viewBinding
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.FragmentPhotosBinding
import com.aamernabi.core.dagger.Injectable
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import javax.inject.Inject

class PhotosFragment : Fragment(), OnItemClickListener,
    Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel

    private val binding by viewBinding(FragmentPhotosBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                is State.Error -> onError(state.t, state.code)
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

    private fun onError(t: Throwable, errorCode: Int?) {
        binding.progressBar.visibility = View.GONE

        if (errorCode != Errors.NO_DATA) {
            binding.recyclerView.visibility = View.GONE
            binding.tvNoInternet.visibility = View.VISIBLE
            binding.tvNoInternet.text =
                if (t.message.isNullOrEmpty()) binding.tvNoInternet.text else t.message
        }
    }
}
