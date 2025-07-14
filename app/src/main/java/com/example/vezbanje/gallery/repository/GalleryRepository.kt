package com.example.vezbanje.gallery.repository

import com.example.vezbanje.db.AppDatabase
import com.example.vezbanje.gallery.api.PhotosApi
import com.example.vezbanje.gallery.api.model.CatImage
import com.example.vezbanje.gallery.db.CatImageData
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val database: AppDatabase,
    private val photosApi: PhotosApi
){

    //private val breedsApi: BreedsApi = retrofit.create(BreedsApi:: class.java)

//    suspend fun getBreedPhotos(breedId: String) : List<CatImage> {
//        return photosApi.getAllPhotos(breedId = breedId)
//    }

    suspend fun getBreedPhotos(breedId: String) : List<CatImage> {
        return database.catImageDao().getCatImagesByBreedId(breedId).toCatImageList()
    }

    suspend fun getRandomPhoto() : CatImage {
        return database.catImageDao().getRandomImage().toCatImage()
    }

    suspend fun getBreedPhotosbyPictureId(pictureId: String) : List<CatImage> {
        return database.catImageDao().getCatImagesByBreedImage(pictureId).toCatImageList()
    }

    fun CatImageData.toCatImage(): CatImage {
        return CatImage(
            id = this.id,
            url = this.url,
            width = this.width,
            height = this.height,
            breedId = this.breedId
        )
    }

    fun List<CatImageData>.toCatImageList(): List<CatImage> {
        return this.map { it.toCatImage() }
    }

}