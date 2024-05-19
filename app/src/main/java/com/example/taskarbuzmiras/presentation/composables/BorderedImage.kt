package com.example.taskarbuzmiras.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun BorderedImage(imageResUrl: String, size: Int) {
    Box(
        modifier = Modifier.size(size.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = 4.dp,
            backgroundColor = Color.White
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageResUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
