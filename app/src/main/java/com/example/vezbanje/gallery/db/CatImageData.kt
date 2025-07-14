package com.example.vezbanje.gallery.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatImageData(
    @PrimaryKey val id : String,
    val breedId : String,
    val url : String,
    val width : Int,
    val height : Int,

)