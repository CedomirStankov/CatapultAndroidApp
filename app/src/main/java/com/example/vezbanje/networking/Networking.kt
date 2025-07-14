package com.example.vezbanje.networking

import com.example.vezbanje.networking.serialization.AppJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

//MOVED TO NetworkingModule

//val okHttpClient = OkHttpClient.Builder()
//    .addInterceptor{
//        val updatedRequest = it.request().newBuilder()
//            .addHeader("x-api-key","live_QJu0Ht6HPWgt0vkCyqg0TlDCRewu70A4nPMB3U0NeJTCLtnxcl0m7N9MqUxV3Ewg")
//            .build()
//        it.proceed(updatedRequest)
//    }
//    .addInterceptor(
//        HttpLoggingInterceptor().apply {
//            setLevel(HttpLoggingInterceptor.Level.BODY)
//        }
//    ).build()
//
//val retrofit: Retrofit = Retrofit.Builder()
//    .baseUrl("https://api.thecatapi.com/v1/")
//    .client(okHttpClient)
//    .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
//    .build()