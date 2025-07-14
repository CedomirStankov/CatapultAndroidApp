package com.example.vezbanje.breeds.repository

import com.example.vezbanje.breeds.list.db.BreedsData
import com.example.vezbanje.db.AppDatabase
import com.example.vezbanje.networking.api.BreedsApi
import com.example.vezbanje.gallery.db.CatImageData
import javax.inject.Inject

class BreedsDetailsRepository @Inject constructor(
    private val breedsApi: BreedsApi,
    private val database: AppDatabase,
){

    //private val breedsApi: BreedsApi = retrofit.create(BreedsApi:: class.java)

//    suspend fun getBreedDetailsById(id: String) : BreedsDetailsApiModel {
//        return breedsApi.getBreed(id)
//    }

    suspend fun getBreedDetailsById(id: String) : BreedsData {
        return database.breedDao().getBreed(id)
    }

//    suspend fun getImage(id: String) : CatImage {
//        return breedsApi.getImages(id)
//    }

    suspend fun getImage(id: String) : CatImageData {
        return database.catImageDao().getCatImage(id)
    }
}