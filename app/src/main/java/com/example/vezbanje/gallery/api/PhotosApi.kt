package com.example.vezbanje.gallery.api

import com.example.vezbanje.gallery.api.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("images/search")
    suspend fun getAllPhotos(
        @Query("limit") limit: Int = 20,
        @Query("breed_ids") breedId: String
    ): List<CatImage>
}