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
import androidx.paging.DataSource
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.utils.State
import kotlinx.coroutines.Job

class PagedKeyPhotosDSFactory(
    private var job: Job?,
    private val service: PhotosService,
    private var state: MutableLiveData<State<Unit>>
) : DataSource.Factory<Int, Photo>() {

    override fun create(): DataSource<Int, Photo> {
        return PagedKeyPhotosDS(job, service, state)
    }
}
