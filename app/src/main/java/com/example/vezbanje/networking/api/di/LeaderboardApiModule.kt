package com.example.vezbanje.networking.api.di

import com.example.vezbanje.leaderboard.LeaderboardApi
import com.example.vezbanje.networking.LeaderboardApiRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LeaderboardApiModule {

    @Provides
    @Singleton
    fun provideLeaderboardApi(@LeaderboardApiRetrofit retrofit: Retrofit): LeaderboardApi = retrofit.create()
}