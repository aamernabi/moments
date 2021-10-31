package com.aamernabi.moments.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.parseColor

@Composable
fun PhotosRow(
    photos: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(photos) { photo ->
            photo ?: return@items
            Photo(
                photo.urls.thumb,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.5.dp),
                placeHolderColor = Color(parseColor(photo.color))
            )
        }
    }
}