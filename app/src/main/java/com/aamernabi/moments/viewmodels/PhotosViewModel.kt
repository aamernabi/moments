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
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aamernabi.moments.datasource.PhotosRepository
import com.aamernabi.moments.datasource.remote.photos.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _downloadImage = MutableLiveData<String>()
    val downloadImage: LiveData<String> get() = _downloadImage
    var cachedPhotos: List<Photo>? = null


    var currentIndex = 0

    fun photos(): Flow<PagingData<Photo>> {
        return repository.photos().cachedIn(viewModelScope)
    }

    fun downloadImage(full: String) {
        _downloadImage.value = full
    }
}
