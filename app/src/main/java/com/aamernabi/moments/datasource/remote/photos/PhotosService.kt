package com.aamernabi.moments.datasource.remote.photos

import com.aamernabi.moments.datasource.remote.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosService  @Inject constructor(
    private val service: UnsplashApi
) {

    suspend fun getPhotos(page: Int, perPage: Int = PER_PAGE): List<Photo> {
        val request = service.getPhotosAsync(page, perPage)
        return request.await()
    }

    companion object {
        private const val PER_PAGE = 20
    }
}
