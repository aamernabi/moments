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
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.aamernabi.core.dagger.Injectable
import com.aamernabi.core.utils.delegates.viewBinding
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.FragmentPhotosBinding
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosFragment : Fragment(R.layout.fragment_photos), OnItemClickListener,
    Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<PhotosViewModel> { viewModelFactory }

    private val binding by viewBinding(FragmentPhotosBinding::bind)
    private var adapter: PhotoAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(this).also { this.adapter = it }
        binding.recyclerView.adapter = adapter
        adapter.addLoadStateListener(::onPhotosState)
        lifecycleScope.launch { viewModel.photos().collectLatest(::onPhotos) }
    }

    private suspend fun onPhotos(photos: PagingData<Photo>) {
        val adapter = binding.recyclerView.adapter as? PhotoAdapter?
        adapter?.submitData(photos)
    }

    private fun onPhotosState(state: CombinedLoadStates) {
        val adapter = adapter ?: return
        val isEmpty = state.refresh is LoadState.NotLoading &&
            state.append.endOfPaginationReached && adapter.itemCount == 0

        binding.recyclerView.isVisible = state.source.refresh is LoadState.NotLoading && !isEmpty
        //binding.emptyView.root.isVisible = isEmpty
        binding.progressBar.isVisible = state.source.refresh is LoadState.Loading

        val error = state.source.refresh as? LoadState.Error?
        when {
            adapter.itemCount == 0 && error != null -> onError(error.error, Errors.NO_DATA)
        }
    }

    override fun onItemClick(index: Int) {
        val adapter = adapter ?: return
        viewModel.cachedPhotos = adapter.snapshot().items
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
