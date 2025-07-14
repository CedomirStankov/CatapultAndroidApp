package com.example.vezbanje.breeds.list.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(breedsData: BreedsData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<BreedsData>)

    @Query("SELECT * FROM BreedsData")
    suspend fun getAllHelper(): List<BreedsData>

    @Query("SELECT * FROM BreedsData WHERE id = :breedId")
    fun getBreed(breedId: String): BreedsData

    @Query("SELECT * FROM BreedsData")
    fun observeAllBreeds(): Flow<List<BreedsData>>

}