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

package com.aamernabi.moments.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.State
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagedKeyPhotosDS(
    private var job: Job?,
    private val service: PhotosService,
    private var state: MutableLiveData<State<Nothing>>
) : PageKeyedDataSource<Int, Photo>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val photos = service.getPhotos(1)
                state.value = State.Success(null)
                callback.onResult(photos, null, 2)
            } catch (e: UnknownHostException) {
                state.value = State.Error(null)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key

        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val photos = service.getPhotos(page)
                callback.onResult(photos, page + 1)
            } catch (e: UnknownHostException) {
                state.value = State.Error(null, Errors.NO_DATA)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        // Yeah i know, we don't need this.
    }
}
