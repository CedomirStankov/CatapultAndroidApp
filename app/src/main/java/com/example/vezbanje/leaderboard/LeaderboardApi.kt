package com.example.vezbanje.leaderboard

import com.example.vezbanje.leaderboard.model.LeaderboardItemApiModel
import retrofit2.http.GET

interface LeaderboardApi {

    @GET("leaderboard?category=3")
    suspend fun getAllLeaderboardItems(): List<LeaderboardItemApiModel>

}