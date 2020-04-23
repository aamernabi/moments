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

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.aamernabi.moments.datasource.remote.photos.Photo
import kotlin.reflect.KSuspendFunction1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PhotosRemoteDataSource(
    private val scope: CoroutineScope,
    private val fetchPhotos: KSuspendFunction1<Int, List<Photo>>
) : PageKeyedDataSource<Int, Photo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        scope.launch {
            val photos = fetchPhotos(1)
            callback.onResult(photos, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        scope.launch {
            val page = params.key
            val photos = fetchPhotos(page)
            callback.onResult(photos, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    class Factory(
        private val scope: CoroutineScope,
        private val fetchPhotos: KSuspendFunction1<Int, List<Photo>>
    ) : DataSource.Factory<Int, Photo>() {
        override fun create(): DataSource<Int, Photo> {
            return PhotosRemoteDataSource(scope, fetchPhotos)
        }
    }
}
