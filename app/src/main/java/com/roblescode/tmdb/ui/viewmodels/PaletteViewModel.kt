package com.roblescode.tmdb.ui.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaletteViewModel @Inject constructor() : ViewModel() {

    suspend fun calcDominantColor(painter: AsyncImagePainter, onFinish: (Color) -> Unit) {
        val image =
            painter.imageLoader.execute(painter.request).drawable
        val bmp = (image as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888,
            true
        )
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}