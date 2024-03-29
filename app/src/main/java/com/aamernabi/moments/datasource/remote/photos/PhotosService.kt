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

package com.aamernabi.moments.datasource.remote.photos

import com.aamernabi.core.dagger.scopes.FeatureScope
import com.aamernabi.moments.datasource.remote.UnsplashApi
import javax.inject.Inject

@FeatureScope
class PhotosService @Inject constructor(
    private val service: UnsplashApi
) {

    suspend fun getPhotos(
        page: Int,
        perPage: Int = PER_PAGE
    ): List<Photo> {
        return service.getRecentPhotos(page, perPage)
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val PER_PAGE = 20
    }
}
