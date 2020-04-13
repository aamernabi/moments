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

package com.aamernabi.moments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aamernabi.moments.datasource.PagedKeyPhotosDSFactory
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.utils.State
import javax.inject.Inject
import kotlinx.coroutines.Job

class PhotosViewModel @Inject constructor(
    photosService: PhotosService
) : ViewModel() {

    private val _photosState = MutableLiveData<State<Nothing>>()
    val photosState: LiveData<State<Nothing>> get() = _photosState

    var currentIndex = 0
    private var job: Job? = null

    init {
        _photosState.value = State.Loading()
    }

    val photos: LiveData<PagedList<Photo>> = LivePagedListBuilder<Int, Photo>(
        PagedKeyPhotosDSFactory(job, photosService, _photosState),
        20
    ).build()

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}
