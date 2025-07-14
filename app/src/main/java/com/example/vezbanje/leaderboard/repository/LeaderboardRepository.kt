package com.example.vezbanje.leaderboard.repository

import com.example.vezbanje.leaderboard.LeaderboardApi
import com.example.vezbanje.leaderboard.model.LeaderboardItemApiModel
import javax.inject.Inject

class LeaderboardRepository @Inject constructor(
    private val leaderboardApi: LeaderboardApi,
){

    suspend fun fetchLeaderboard(): List<LeaderboardItemApiModel> {
        leaderboardApi.getAllLeaderboardItems()
        return leaderboardApi.getAllLeaderboardItems()
    }

}