package com.example.vezbanje.gallery.photoviewer

import com.example.vezbanje.gallery.api.model.CatImage

data class PhotoViewerState(
    val loading: Boolean = false,
    val gallery: List<CatImage> = emptyList(),
    val currentIndex: Int = -1
)