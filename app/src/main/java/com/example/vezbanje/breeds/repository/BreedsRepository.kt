package com.example.vezbanje.breeds.repository

import android.util.Log
import com.example.vezbanje.db.AppDatabase
import com.example.vezbanje.gallery.api.PhotosApi
import com.example.vezbanje.gallery.db.CatImageData
import com.example.vezbanje.mappers.asBreedsDbModel
import com.example.vezbanje.networking.api.BreedsApi
import javax.inject.Inject

class BreedsRepository @Inject constructor(
    private val breedsApi: BreedsApi,
    private val database: AppDatabase,
    private val photosApi: PhotosApi
){

    //private val breedsApi: BreedsApi = retrofit.create(BreedsApi:: class.java)

    suspend fun fetchAllBreeds() {
        val breeds = breedsApi.getAllBreeds()
        database.breedDao().insertAll(list = breeds.map { it.asBreedsDbModel() })
    }

    fun observeAllBreeds()=database.breedDao().observeAllBreeds()

    suspend fun fetchMainPictureHelper(){
        val breeds = database.breedDao().getAllHelper()
        for(breed in breeds){
            if(breed.id.equals("ebur") || breed.id.equals("mala")) continue
            val catImage = breedsApi.getImages(breed.reference_image_id)
            val catImagedata = CatImageData(catImage.id,breed.id,catImage.url,catImage.width,catImage.height)
            database.catImageDao().insert(catImagedata)
        }

    }

    suspend fun fetchAllPictures(){
        val breeds = database.breedDao().getAllHelper()
        for(breed in breeds){
            Log.d("TESTTT", "${breed.id}")
            if(breed.id.equals("ebur") || breed.id.equals("mala")) continue
            val catImages = photosApi.getAllPhotos(20, breed.id)
            for(catImage in catImages){
                val catImagedata = CatImageData(catImage.id,breed.id,catImage.url,catImage.width,catImage.height)
                database.catImageDao().insert(catImagedata)
            }

        }
    }

}