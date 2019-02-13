package com.aamernabi.moments.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.aamernabi.moments.datasource.PagedKeyPhotosDSFactory
import com.aamernabi.moments.datasource.remote.NetworkState
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import kotlinx.coroutines.Job

class PhotosViewModel : ViewModel() {

    private var state: NetworkState<Nothing>? = null
    private var job: Job? = null

    val photos: LiveData<PagedList<Photo>> = LivePagedListBuilder<Int, Photo>(
        PagedKeyPhotosDSFactory(job, PhotosService.instance, state),
        20
    ).build()

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

    /*fun getPhotos(page: Int = 1, perPage: Int = 20){
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = PhotosService.instance.getPhotos(page, perPage)
            withContext(Dispatchers.Main) {
                _photos.value = result
            }
        }
    }*/

}