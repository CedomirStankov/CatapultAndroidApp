package com.example.vezbanje.gallery.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CatImage(
    val id : String = "",
    val url : String = "",
    val width : Int = 0,
    val height : Int = 0,
    val breedId: String ="",
)