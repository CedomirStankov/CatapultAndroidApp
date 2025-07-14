package com.example.vezbanje.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.leaderboard.model.LeaderboardItem
import com.example.vezbanje.leaderboard.model.LeaderboardItemApiModel
import com.example.vezbanje.leaderboard.repository.LeaderboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: LeaderboardRepository
): ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LeaderboardState.() -> LeaderboardState) = _state.getAndUpdate(reducer)

    init{
        fetchLeaderboard()
    }

    private fun fetchLeaderboard(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try{
                withContext(Dispatchers.IO){
                    val leaderboard = convertApiModelToItem(repository.fetchLeaderboard())
                    setState { copy(items = leaderboard) }
                }
            }catch (error: IOException){
                setState { copy(error = error) }
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    fun convertApiModelToItem(apiModels: List<LeaderboardItemApiModel>): List<LeaderboardItem> {
        val gamesPlayedMap = mutableMapOf<String, Int>()

        for (apiModel in apiModels) {
            val currentGamesPlayed = gamesPlayedMap.getOrDefault(apiModel.nickname, 0)
            gamesPlayedMap[apiModel.nickname] = currentGamesPlayed + 1
        }


        val leaderboardItems = mutableListOf<LeaderboardItem>()

        for (apiModel in apiModels) {
            val gamesPlayed = gamesPlayedMap[apiModel.nickname] ?: 0
            val item = LeaderboardItem(
                nickname = apiModel.nickname,
                result = apiModel.result,
                gamesPlayed = gamesPlayed
            )
            leaderboardItems.add(item)
        }

        return leaderboardItems
    }


}