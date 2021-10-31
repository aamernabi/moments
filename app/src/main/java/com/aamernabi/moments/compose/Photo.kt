package com.aamernabi.moments.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import com.aamernabi.moments.R

@Composable
fun Photo(
    url: String,
    modifier: Modifier = Modifier,
    placeHolderColor: Color = Color.Transparent,
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(data = url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = placeHolderColor,
                ),
        )
    }
}
