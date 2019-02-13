package com.aamernabi.moments.datasource.remote.photos

import com.aamernabi.moments.datasource.remote.UnsplashApi

class PhotosService {

    companion object {
        val instance: PhotosService by lazy { PhotosService() }
        private const val PER_PAGE = 20
    }

    private val service = UnsplashApi.getInstance()

    suspend fun getPhotos(page: Int, perPage: Int = PER_PAGE): List<Photo> {
        val request = service.getPhotosAsync(page, perPage)
        return request.await()
    }
}
