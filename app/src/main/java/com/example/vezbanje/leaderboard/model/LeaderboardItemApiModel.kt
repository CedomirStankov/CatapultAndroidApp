package com.example.vezbanje.leaderboard.model

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardItemApiModel(
    val category: Int,
    val nickname: String,
    val result: Double,
    val createdAt: Long,
)