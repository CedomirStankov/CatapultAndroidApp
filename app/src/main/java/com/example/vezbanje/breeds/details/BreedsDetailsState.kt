package com.example.vezbanje.breeds.details

import com.example.vezbanje.breeds.domain.BreedsDetailsWithImage

data class BreedsDetailsState(
    val loading: Boolean = false,
    val items: BreedsDetailsWithImage? = null,
    val error: Throwable? = null,
) {
}