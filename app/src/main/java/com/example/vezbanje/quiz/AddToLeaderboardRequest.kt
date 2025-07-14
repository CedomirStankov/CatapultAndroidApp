package com.example.vezbanje.quiz

import kotlinx.serialization.Serializable

@Serializable
data class AddToLeaderboardRequest(
    val nickname: String,
    val result: Float,
    val category: Int
)