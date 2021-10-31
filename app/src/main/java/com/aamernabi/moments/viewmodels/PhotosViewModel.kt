package com.aamernabi.moments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aamernabi.moments.datasource.PhotosRepository
import com.aamernabi.moments.datasource.remote.photos.Photo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Todo: Check -> StateFlow and SharedFlow
 * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
 */

class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _downloadImage = MutableLiveData<String>()
    val downloadImage: LiveData<String> get() = _downloadImage

    private val photoClickEventChannel = Channel<Unit>()
    val photoClickEventFlow = photoClickEventChannel.receiveAsFlow()

    private var _photosCacheFlow: Flow<PagingData<Photo>>? = null
    val photosCacheFlow: Flow<PagingData<Photo>> get() = _photosCacheFlow!!
    var photosCache: List<Photo>? = null
    var currentIndex = 0

    fun photos(): Flow<PagingData<Photo>> {
        val photosCacheFlow = _photosCacheFlow
        if (photosCacheFlow != null) return photosCacheFlow

        return repository.photos()
            .cachedIn(viewModelScope)
            .also { this._photosCacheFlow = it }
    }

    fun downloadImage(full: String) {
        _downloadImage.value = full
    }

    fun onPhotoClick(index: Int, photos: List<Photo>) {
        currentIndex = index
        photosCache = photos
        viewModelScope.launch {
            photoClickEventChannel.send(Unit)
        }
    }

}
