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
    private var state: MutableLiveData<State<Nothing>>
) : DataSource.Factory<Int, Photo>() {

    override fun create(): DataSource<Int, Photo> {
        return PagedKeyPhotosDS(job, service, state)
    }

}