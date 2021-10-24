package com.aamernabi.moments.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.aamernabi.moments.R
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.extensions.items
import kotlinx.coroutines.flow.Flow

@ExperimentalFoundationApi
@Composable
fun Photos(
    photosFlow: Flow<PagingData<Photo>>,
    modifier: Modifier = Modifier,
    onClick: (Photo) -> Unit = {}
) {
    val photos = photosFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = modifier
    ) {
        items(photos) { photo ->
            photo ?: return@items
            Photo(
                photo,
                modifier = Modifier
                    .clickable(onClick = { onClick(photo) })
                    .padding(0.5.dp)
            )
        }
    }
}

@Composable
fun Photo(
    photo: Photo,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        val imageSize = dimensionResource(id = R.dimen.photo_width)
        Image(
            painter = rememberImagePainter(
                data = photo.urls.thumb
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
                .background(
                    color = colorResource(id = R.color.seaShellSolid),
                ),
        )
    }
}
