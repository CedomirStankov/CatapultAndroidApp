package com.example.vezbanje.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vezbanje.core.theme.Bronze
import com.example.vezbanje.core.theme.Gold
import com.example.vezbanje.core.theme.Silver
import com.example.vezbanje.leaderboard.model.LeaderboardItem
import kotlinx.coroutines.launch

fun NavGraphBuilder.leaderboard(
    route: String,
    navController : NavController,

    ) = composable(route = route){
    val leaderboardViewModel = hiltViewModel<LeaderboardViewModel>()

    val state by leaderboardViewModel.state.collectAsState() // collectAsState vraca ovo kao State iz Compose biblioteke

    LeaderboardScreen(
        state = state,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    state: LeaderboardState,
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        color = Color.Black,
                        text = "Catapult",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Add your drawer items here
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate("breeds")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(color = Color.Black, text = "Breeds List")
                    }
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate("quizStart")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(color = Color.Black, text = "Play Quiz!")
                    }
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate("leaderboard")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(color = Color.Black, text = "Leaderboard")
                    }
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate("changeAccountDetails")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(color = Color.Black, text = "Change Account Details")
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            //.padding(8.dp),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Leaderboard",
                                color = Color.White,
                                style = TextStyle(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    if (state.loading) {
                        Spacer(modifier = Modifier.height(100.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(128.dp),
                            color = Color.Black
                        )
                    } else if (state.error != null) {
                        Text(text = "Error. ${state.error.message}", color = Color.Black)
                    } else {
                        LazyColumn {
                            for ((index, item) in state.items.withIndex()) {
                                item {
                                    LeaderboardItemCard(index, item = item)
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {

            },
        )
    }
}

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    padding: Int = 8
) {
    Text(
        text = text,
        modifier = modifier
            .border(width = 1.dp, color = borderColor)
            .background(color = backgroundColor)
            .padding(padding.dp),
        color = Color.Black
    )
}

@Composable
fun LeaderboardItemCard(index: Int, item: LeaderboardItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if(index==0) Gold else if(index == 1) Silver else if(index == 2) Bronze else MaterialTheme.colorScheme.surface
        ),
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier.padding(16.dp)

        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${index+1}. place")
                Text(text = "Nickname: ${item.nickname}")
                Text(text = "Result: ${item.result}")
                Text(text = "Games played: ${item.gamesPlayed}")
            }
        }
    }
}