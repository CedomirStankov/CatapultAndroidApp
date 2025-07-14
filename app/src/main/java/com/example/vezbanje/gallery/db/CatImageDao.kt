package com.example.vezbanje.gallery.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catImageData: CatImageData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CatImageData>)

    @Query("SELECT * FROM CatImageData WHERE id = :imageId")
    fun getCatImage(imageId: String): CatImageData

    @Query("SELECT * FROM CatImageData WHERE breedId = :breedId")
    fun getCatImagesByBreedId(breedId: String): List<CatImageData>

    @Query("SELECT * FROM CatImageData ORDER BY RANDOM() LIMIT 1")
    fun getRandomImage(): CatImageData

    @Query("SELECT * FROM CatImageData where breedId = (SELECT breedId FROM CatImageData WHERE id = :id)")
    fun getCatImagesByBreedImage(id: String): List<CatImageData>

}