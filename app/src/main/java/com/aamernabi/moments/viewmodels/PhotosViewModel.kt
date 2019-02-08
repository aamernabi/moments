package com.aamernabi.moments.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import kotlinx.coroutines.*

class PhotosViewModel : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() { return _photos }

    private var job: Job? = null

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

    fun getPhotos(page: Int = 1, perPage: Int = 20){
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = PhotosService.instance.getPhotos(page, perPage)
            withContext(Dispatchers.Main) {
                _photos.value = result
            }
        }
    }

}