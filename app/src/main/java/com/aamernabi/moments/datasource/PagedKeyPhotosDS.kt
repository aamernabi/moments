package com.aamernabi.moments.datasource

import android.arch.paging.PageKeyedDataSource
import com.aamernabi.moments.datasource.remote.NetworkState
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagedKeyPhotosDS(
    private var job: Job?,
    private val service: PhotosService,
    private var state: NetworkState<Nothing>?
) : PageKeyedDataSource<Int, Photo>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        state = NetworkState.Loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val photos = service.getPhotos(1)
            state = NetworkState.Success(null)
            callback.onResult(photos, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        state = NetworkState.Loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val photos = service.getPhotos(page ?: 1)
            state = NetworkState.Success(null)
            callback.onResult(photos, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        // Yeah i know, we don't need this.
    }
}