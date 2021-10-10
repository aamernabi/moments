package com.aamernabi.moments.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.datasource.remote.photos.PhotosService
import com.aamernabi.moments.datasource.remote.photos.PhotosService.Companion.INITIAL_PAGE_INDEX
import com.aamernabi.moments.datasource.remote.photos.PhotosService.Companion.PER_PAGE
import retrofit2.HttpException
import java.io.IOException

class PhotosRemoteDataSource(
    private val service: PhotosService,
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val pageIndex = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val photos = service.getPhotos(pageIndex)
            val prevKey = if (pageIndex == INITIAL_PAGE_INDEX) null else pageIndex - 1
            val nextKey = if (photos.isNotEmpty() && photos.size >= PER_PAGE) {
                pageIndex + (params.loadSize / PER_PAGE)
            } else null

            LoadResult.Page(
                data = photos,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
