package com.example.vezbanje.leaderboard

import com.example.vezbanje.leaderboard.model.LeaderboardItem

data class LeaderboardState(
    val loading: Boolean = false,
    val items: List<LeaderboardItem> = emptyList(),
    val error: Throwable? = null,
) {
}