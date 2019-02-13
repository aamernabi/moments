package com.aamernabi.moments.datasource

import android.arch.paging.DataSource
import com.aamernabi.moments.datasource.remote.NetworkState
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import kotlinx.coroutines.Job

class PagedKeyPhotosDSFactory(
    private var job: Job?,
    private val service: PhotosService,
    private var state: NetworkState<Nothing>?
) : DataSource.Factory<Int, Photo>() {

    override fun create(): DataSource<Int, Photo> {
        return PagedKeyPhotosDS(job, service, state)
    }

}