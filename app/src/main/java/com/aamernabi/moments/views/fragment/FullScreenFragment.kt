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
import com.aamernabi.core.utils.delegates.viewBinding
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.FragmentFullScreenBinding
import com.aamernabi.core.dagger.Injectable
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.MainActivity
import com.aamernabi.moments.views.adapter.FullScreenAdapter
import kotlinx.android.synthetic.main.fragment_full_screen.*
import javax.inject.Inject

class FullScreenFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel

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

        viewModel = ViewModelProvider(activity ?: error("activity null"), viewModelFactory)
            .get(PhotosViewModel::class.java)

        fab_download.show()
        fab_download.setOnClickListener { downloadImage() }

        val photos = viewModel.photos.value ?: return
        binding.viewPager.adapter = FullScreenAdapter().apply { addAll(photos) }
        binding.viewPager.currentItem = viewModel.currentIndex

        attachPhotosObserver()
    }

    private fun downloadImage() {
        val image = viewModel.photos.value?.let { it[viewModel.currentIndex] } ?: return
        viewModel.downloadImage(image.urls.full)
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)?.hideToolbar()
    }

    private fun attachPhotosObserver() {
        viewModel.photos.observe(viewLifecycleOwner, Observer { photos ->
            (binding.viewPager.adapter as FullScreenAdapter?)?.addAll(photos ?: return@Observer)
        })
    }
}
