package com.aamernabi.moments.datasource.remote.photos

import com.aamernabi.moments.datasource.remote.RetrofitFactory

class PhotosService {

    companion object {
        val instance: PhotosService by lazy { PhotosService() }
    }

    private val service = RetrofitFactory.getInstance().create(PhotosApiInterface::class.java)

    suspend fun getPhotos(page: Int, perPage: Int): List<Photo> {
        val request = service.getPhotosAsync(page, perPage)
        return request.await()
    }
}
