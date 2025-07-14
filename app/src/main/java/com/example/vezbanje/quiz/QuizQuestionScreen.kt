package com.example.vezbanje.quiz

import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import com.example.vezbanje.core.compose.ConfirmExitDialog

fun NavGraphBuilder.quizQuestion(
    route: String = "quizQuestion",
    navController: NavController
) = composable(
    route = route,
){navBackStackEntry ->
    val quizViewModel = hiltViewModel<QuizViewModel>(navBackStackEntry)
    val state by quizViewModel.state.collectAsState()
    QuizQuestionScreen(
        state = state,
        navController = navController,
    )
}

@Composable
fun QuizQuestionScreen(
    state: QuizQuestionState,
    navController: NavController,
){

    val scrollState = rememberScrollState()
    var index by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        showDialog = true
    }

    var timeLeft by remember {
        mutableStateOf(300)
    }
    val timer = remember { object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeft = (millisUntilFinished / 1000).toInt()
        }

        override fun onFinish() {
                val ubp = ((score * 2.5 * (1+(0 + 120)/300)).coerceAtMost(maximumValue = 100.0)).toString()
                navController.navigate("quizEnd/${ubp}")
        }
    }.start() }

    DisposableEffect(Unit) {
        timer.start()
        onDispose {
            timer.cancel()
        }
    }

    if(index!=20){
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
                Text(text = "${index+1}. pitanje")
                Text(text = state.items?.get(index)?.pitanje ?: "")
                Text(
                    text = "Time left: ${timeLeft / 60}:${String.format("%02d", timeLeft % 60)}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Log.d("yest", " $index")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable {
                                var resenje = state.items?.get(index)?.resenje
                                if (resenje == 1) {
                                    score += 1
                                    Log.d("SCORE", "${score}")
                                }
                                index++
                            }
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            model = state.items?.get(index)?.urlPrve,
                            contentDescription = null,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable {
                                var resenje = state.items?.get(index)?.resenje
                                if (resenje == 2) {
                                    score += 1
                                    Log.d("SCORE", "${score}")
                                }
                                index++
                            }
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            model = state.items?.get(index)?.urlDruge,
                            contentDescription = null,
                        )
                    }
                }
                //Text(text = "Tacan odgovor: ${state.items?.get(index)?.resenje}")
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Napomena: Kliknuti na sliku macke", color = Color.Black)

            }

            if (showDialog) {
                ConfirmExitDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        showDialog = false
                        navController.navigate("quizStart")
                    }
                )
            }
        }

    }else{
        val ubp = ((score * 2.5 * (1+(timeLeft + 120)/300)).coerceAtMost(maximumValue = 100.0)).toString()
        navController.navigate("quizEnd/${ubp}")
    }
}

