package com.example.vezbanje.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vezbanje.breeds.list.db.BreedsDao
import com.example.vezbanje.breeds.list.db.BreedsData
import com.example.vezbanje.gallery.db.CatImageDao
import com.example.vezbanje.gallery.db.CatImageData

@Database(
    entities = [
        BreedsData::class,
        CatImageData::class,
    ],
    version = 4,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedsDao

    abstract fun catImageDao(): CatImageDao

}