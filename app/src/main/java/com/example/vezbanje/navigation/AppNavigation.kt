package com.example.vezbanje.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vezbanje.accountDetails.changeAccountDetails
import com.example.vezbanje.auth.AuthStore
import com.example.vezbanje.breeds.details.breedsDetails
import com.example.vezbanje.gallery.gallery
import com.example.vezbanje.breeds.list.breedsList
import com.example.vezbanje.gallery.photoviewer.photoviewer
import com.example.vezbanje.leaderboard.leaderboard
import com.example.vezbanje.login.login
import com.example.vezbanje.quiz.end.quizEnd
import com.example.vezbanje.quiz.quizQuestion
import com.example.vezbanje.quiz.start.quizStart

@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition")
@Composable
fun AppNavigation(autData: AuthStore) {

    val navController = rememberNavController()
    val authData = autData.authData.value

    NavHost(
        navController = navController,
        startDestination = if (authData.isEmpty()) "login" else "breeds"
    ) {
        login(
            route="login",
            navController = navController,
            authStore = autData,
        )
        breedsList(
            route = "breeds",
            navController = navController,
        )
        breedsDetails(
            route = "breeds/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    this.nullable = false
                    this.type = NavType.StringType
                }
            ),
            navController = navController,
        )
        gallery(
            route="pictures",
            onPictureClick =  {
                navController.navigate(route = "picture/$it.")
            },
        )
        photoviewer(
            route="picture/{pictureId}",
            arguments = listOf(
                navArgument(name = "pictureId") {
                    nullable = false
                    type = NavType.StringType
                }
            ),
        )
        quizStart(
            route = "quizStart",
            navController = navController,
        )
        quizQuestion(
            route = "quizQuestion",
            navController = navController,
        )
        quizEnd(
            route = "quizEnd/{score}",
            navController = navController,
            arguments = listOf(
                navArgument(name = "score"){
                    this.nullable = false
                    this.type = NavType.StringType
                }
            ),
            authStore = autData
        )
        changeAccountDetails(
            route = "changeAccountDetails",
            navController = navController,
            authData = autData,
        )
        leaderboard(
            route = "leaderboard",
            navController = navController,
        )
    }
}

inline val SavedStateHandle.id: String
    get() = checkNotNull(get("id")) { "id required" }

inline val SavedStateHandle.breedId: String
    get() = checkNotNull(get("breedId")) { "breedId required" }

inline val SavedStateHandle.pictureId: String
    get() = checkNotNull(get("pictureId")) { "pictureId required" }