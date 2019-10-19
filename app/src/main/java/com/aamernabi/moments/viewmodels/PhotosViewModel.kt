package com.aamernabi.moments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aamernabi.moments.datasource.PagedKeyPhotosDSFactory
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.utils.State
import kotlinx.coroutines.Job

class PhotosViewModel : ViewModel() {

    private val _photosState = MutableLiveData<State<Nothing>>()
    val photosState: LiveData<State<Nothing>> get() = _photosState

    var currentIndex = 0
    private var job: Job? = null

    init {
        _photosState.value = State.Loading()
    }

    val photos: LiveData<PagedList<Photo>> = LivePagedListBuilder<Int, Photo>(
        PagedKeyPhotosDSFactory(job, PhotosService.instance, _photosState),
        20
    ).build()

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}