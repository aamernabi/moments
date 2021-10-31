package com.aamernabi.moments.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.aamernabi.moments.R
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

        Box {
            when {
                items.loadState.source.refresh is LoadState.NotLoading && !isEmpty ->
                    Photos(
                        items = items,
                        onClick = viewModel::onPhotoClick
                    )
                items.loadState.source.refresh is LoadState.Loading -> {
                    Progress(
                        resource = R.raw.progress,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(dimensionResource(id = R.dimen.progress_bar))
                    )
                }
                items.itemCount == 0 && error != null -> {
                    val message = error.error.message?.takeIf { it.trim().isNotEmpty() }
                        ?: stringResource(R.string.no_internet)
                    NoInternet(
                        text = message,
                        icon = R.drawable.ic_no_signal,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
