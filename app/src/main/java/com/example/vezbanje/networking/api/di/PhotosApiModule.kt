package com.example.vezbanje.networking.api.di

import com.example.vezbanje.gallery.api.PhotosApi
import com.example.vezbanje.networking.CatApiRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotosApiModule {

    @Provides
    @Singleton
    fun providePhotosApi(@CatApiRetrofit retrofit: Retrofit): PhotosApi = retrofit.create()
}