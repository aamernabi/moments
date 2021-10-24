package com.aamernabi.moments.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.aamernabi.moments.R
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.extensions.itemsIndexed
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.google.android.material.composethemeadapter.MdcTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel
) {
    val items = viewModel.photos().collectAsLazyPagingItems()
    MdcTheme {
        val isEmpty = items.loadState.refresh is LoadState.NotLoading &&
            items.loadState.append.endOfPaginationReached && items.itemCount == 0
        val error = items.loadState.source.refresh as? LoadState.Error?

        when {
            items.loadState.source.refresh is LoadState.NotLoading && !isEmpty ->
                Photos(
                    items = items,
                    onClick = viewModel::onPhotoClick
                )
            items.loadState.source.refresh is LoadState.Loading -> {
                Progress(
                    resource = R.raw.progress,
                    //modifier = Modifier.size(dimensionResource(id = R.dimen.progress_bar))
                )
            }
            items.itemCount == 0 && error != null -> {
                val message = error.error.message?.takeIf { it.trim().isNotEmpty() }
                    ?: stringResource(R.string.no_internet)
                NoInternet(
                    text = message,
                    icon = R.drawable.ic_no_signal,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Photos(
    items: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier,
    onClick: (Int, List<Photo>) -> Unit = { _, _ -> }
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = modifier
    ) {
        itemsIndexed(items) { index, photo ->
            photo ?: return@itemsIndexed
            Photo(
                photo,
                modifier = Modifier
                    .clickable(onClick = { onClick(index, items.itemSnapshotList.items) })
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
