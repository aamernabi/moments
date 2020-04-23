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
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.aamernabi.core.data.State
import com.aamernabi.moments.datasource.PhotosRemoteDataSource
import com.aamernabi.moments.datasource.remote.coroutineErrorHandler
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val photosService: PhotosService
) : ViewModel() {

    private val _photosState = MutableLiveData<State<Unit>>()
    val photosState: LiveData<State<Unit>> get() = _photosState

    var currentIndex = 0

    val photos: LiveData<PagedList<Photo>> =
        PhotosRemoteDataSource.Factory(viewModelScope, this::fetchPhotos).toLiveData(20)

    private suspend fun fetchPhotos(page: Int): List<Photo> {
        if (page == 1) {
            _photosState.value = State.Loading
        }

        return try {
            photosService.getPhotos(page).also {
                if (page == 1) _photosState.value = State.Success(null)
            }
        } catch (e: Exception) {
            _photosState.value = coroutineErrorHandler(e)
            emptyList()
        }
    }
}
