package com.example.vezbanje.quiz.end

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vezbanje.auth.AuthStore
import com.example.vezbanje.core.compose.CustomBackHandler
import com.example.vezbanje.quiz.QuizEvent
import com.example.vezbanje.quiz.QuizQuestionState
import com.example.vezbanje.quiz.QuizViewModel

fun NavGraphBuilder.quizEnd(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
    authStore: AuthStore
) = composable(
    route = route,
    arguments = arguments,
){
        navBackStackEntry ->
    val quizViewModel = hiltViewModel<QuizViewModel>(navBackStackEntry)
    val state by quizViewModel.state.collectAsState()
    val score = navBackStackEntry.arguments?.getString("score") ?: 0
    QuizEndScreen(
        navController = navController,
        state = state,
        score = score.toString(),
        authStore = authStore,
        eventPublisher = {
            quizViewModel.setEvent(it)
        },
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizEndScreen(
    state: QuizQuestionState,
    score: String,
    navController: NavController,
    authStore: AuthStore,
    eventPublisher: (uiEvent: QuizEvent) -> Unit,
){
    val data = authStore.authData.value.split(" ")
    val nickname = data[1]
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Quiz Result")
                Text(text = "Score: ${score}")
                OutlinedButton(onClick = { eventPublisher(
                    QuizEvent.ShareScore(
                        nickname,
                        score.toFloat()
                    )
                ) }, colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Text(text = "Share score!", color = Color.Black)
                }
                OutlinedButton(onClick = { navController.navigate("breeds")}, colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface) ){
                    Text(text = "Home", color = Color.Black)
                }
            }

        }
    }
    CustomBackHandler(navController, "quizStart")
}




