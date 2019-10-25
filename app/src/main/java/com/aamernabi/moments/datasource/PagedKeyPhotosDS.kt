package com.aamernabi.moments.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.utils.Errors
import com.aamernabi.moments.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.UnknownHostException

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