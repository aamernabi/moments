package com.aamernabi.moments.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.datasource.remote.photos.PhotosService.Companion.PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PhotosRepository {
    fun photos(): Flow<PagingData<Photo>>
}

class PhotosRepositoryImpl @Inject constructor(
    private val service: PhotosService
) : PhotosRepository {

    override fun photos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { PhotosRemoteDataSource(service) }
        ).flow
    }
}