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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.aamernabi.core.dagger.Injectable
import com.aamernabi.core.utils.delegates.viewBinding
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.FragmentFullScreenBinding
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.MainActivity
import com.aamernabi.moments.views.adapter.FullScreenAdapter2
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_full_screen.*

class FullScreenFragment : Fragment(), Injectable {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: PhotosViewModel by activityViewModels { factory }

    private val binding by viewBinding(FragmentFullScreenBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_download.show()
        fab_download.setOnClickListener { downloadImage() }

        binding.viewPager.adapter = FullScreenAdapter2()
        viewModel.photos.value?.let { submitList(it) }
        binding.viewPager.currentItem = viewModel.currentIndex

        viewModel.photos.observe(viewLifecycleOwner, ::submitList)
    }

    private fun downloadImage() {
        val image = viewModel.photos.value?.let { it[viewModel.currentIndex] } ?: return
        viewModel.downloadImage(image.urls.full)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.hideToolbar()
    }

    private fun submitList(photos: List<Photo>) {
        (binding.viewPager.adapter as? FullScreenAdapter2?)?.submitList(photos)
    }
}
