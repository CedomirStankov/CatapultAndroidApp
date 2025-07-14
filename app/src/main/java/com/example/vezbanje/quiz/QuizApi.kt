package com.example.vezbanje.quiz

import retrofit2.http.Body
import retrofit2.http.POST

interface QuizApi {

    @POST("leaderboard")
    suspend fun postScore(@Body request: AddToLeaderboardRequest)
}