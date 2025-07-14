package com.example.vezbanje.networking.api

import com.example.vezbanje.networking.api.apiModel.BreedsApiModel
import com.example.vezbanje.networking.api.apiModel.BreedsDetailsApiModel
import com.example.vezbanje.gallery.api.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsApi {

    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedsApiModel>

    @GET("breeds/{id}")
    suspend fun getBreed(
        @Path("id") breedId: String,
    ): BreedsDetailsApiModel

    @GET("images/{image_id}")
    suspend fun getImages(
        @Path("image_id") breedId: String,
    ): CatImage


}