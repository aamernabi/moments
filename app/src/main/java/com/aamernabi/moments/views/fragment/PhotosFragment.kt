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
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.aamernabi.moments.compose.NoInternet
import com.aamernabi.moments.compose.Photos
import com.aamernabi.moments.compose.PhotosScreen
import com.aamernabi.moments.databinding.FragmentPhotosBinding
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.OnItemClickListener
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.adapter.PhotoAdapter
import com.google.android.material.composethemeadapter.MdcTheme
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

    @OptIn(ExperimentalFoundationApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val adapter = PhotoAdapter(this).also { this.adapter = it }
        binding.recyclerView.adapter = adapter
        adapter.addLoadStateListener(::onPhotosState)
        lifecycleScope.launch { viewModel.photos().collectLatest(::onPhotos) }*/
        binding.recyclerView.setContent {
            PhotosScreen(viewModel = viewModel, onPhotoClick = ::onPhotoClick)
        }
    }

    private suspend fun onPhotos(photos: PagingData<Photo>) {
        /*val adapter = binding.recyclerView.adapter as? PhotoAdapter?
        adapter?.submitData(photos)*/
    }

    private fun onPhotosState(state: CombinedLoadStates) {
        val adapter = adapter ?: return
        val isEmpty = state.refresh is LoadState.NotLoading &&
            state.append.endOfPaginationReached && adapter.itemCount == 0

        binding.recyclerView.isVisible = state.source.refresh is LoadState.NotLoading && !isEmpty
        binding.progressBar.isVisible = state.source.refresh is LoadState.Loading

        val error = state.source.refresh as? LoadState.Error?
        when {
            adapter.itemCount == 0 && error != null -> onError(error.error)
        }
    }

    private fun onPhotoClick(index: Int, photos: List<Photo>) {
        viewModel.cachedPhotos = photos
        viewModel.currentIndex = index
        val navController = Navigation.findNavController(activity ?: return, R.id.nav_host_fragment)
        navController.navigate(R.id.fullScreenFragment)
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
        binding.noInternet.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showProgress() {
        binding.recyclerView.visibility = View.GONE
        binding.noInternet.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onError(t: Throwable) {
        binding.noInternet.visibility = View.VISIBLE
        val message =
            t.message?.takeIf { it.trim().isNotEmpty() } ?: getString(R.string.no_internet)
        binding.noInternet.setContent {
            MdcTheme {
                NoInternet(text = message, icon = R.drawable.ic_no_signal)
            }
        }
    }
}
