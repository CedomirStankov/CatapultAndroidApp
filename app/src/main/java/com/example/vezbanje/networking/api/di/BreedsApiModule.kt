package com.example.vezbanje.networking.api.di

import com.example.vezbanje.networking.CatApiRetrofit
import com.example.vezbanje.networking.api.BreedsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreedsApiModule {

    @Provides
    @Singleton
    fun provideBreedsApi(@CatApiRetrofit retrofit: Retrofit): BreedsApi = retrofit.create()
}