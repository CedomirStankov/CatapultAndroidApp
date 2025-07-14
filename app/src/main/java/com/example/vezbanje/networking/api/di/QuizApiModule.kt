package com.example.vezbanje.networking.api.di

import com.example.vezbanje.networking.LeaderboardApiRetrofit
import com.example.vezbanje.quiz.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizApiModule {

    @Provides
    @Singleton
    fun provideQuizApi(@LeaderboardApiRetrofit retrofit: Retrofit): QuizApi = retrofit.create()
}