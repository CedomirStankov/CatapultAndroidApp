package com.example.vezbanje.gallery

import com.example.vezbanje.gallery.api.model.CatImage

data class GalleryState(
    val loading: Boolean = false,
    val gallery: List<CatImage> = emptyList(),
)