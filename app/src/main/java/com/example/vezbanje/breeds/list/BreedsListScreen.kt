package com.example.vezbanje.breeds.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vezbanje.R
import com.example.vezbanje.breeds.domain.Breeds
import com.example.vezbanje.core.compose.Chip
import kotlinx.coroutines.launch

fun NavGraphBuilder.breedsList(
    route: String,
    navController : NavController,

) = composable(route = route){
    val breedsListViewModel = hiltViewModel<BreedsListViewModel>()

    val state by breedsListViewModel.state.collectAsState() // collectAsState vraca ovo kao State iz Compose biblioteke

    BreedsListScreen(
        state = state,
        onItemClick = {
            navController.navigate(route = "breeds/${it.id}")
        },
        onPlayClick = {
            navController.navigate(route = "quizStart")
        },
        eventPublisher = {
            breedsListViewModel.setEvent(it)
        },
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsListScreen(
    state: BreedsListState,
    onItemClick: (Breeds) -> Unit,
    onPlayClick: (Unit) -> Unit,
    eventPublisher: (uiEvent: BreedsListEvent) -> Unit,
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf(state.query) }

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
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.catalist),
                                contentDescription = "Catalist",
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "Catapult",
                                color = Color.White,
                                style = TextStyle(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                            Column() {
                                Surface(
                                    color = Color.White,
                                    shadowElevation = 4.dp,
                                    shape = MaterialTheme.shapes.large
                                ){
                                    BasicTextField(
                                        value = query,
                                        onValueChange = {
                                            query = it
                                            eventPublisher(BreedsListEvent.SearchQueryChanged(it))
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )

                                }
                                if(query.isNotEmpty()){
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Poništi pretragu",
                                            style = TextStyle(fontSize = 14.sp),
                                            color = Color.White,
                                            modifier = Modifier.clickable {
                                                query="" // ovo menja tekst u textfieldu na prazno
                                                eventPublisher(BreedsListEvent.SearchQueryChanged("")) // a ovo signalizira viewmodelu da je doslo do promene teksta i da treba da updatuje pretragu
                                            }
                                        )
                                    }
                                }
                            }

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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("quizStart") },
                    modifier = Modifier.background(Color.Transparent),
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Add",
                    )
                }
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
                            items(state.items, key = { it.id }) {
                                RasaListItem(
                                    data = it,
                                    onClick = {
                                        onItemClick(it)
                                    },
                                )
                                Spacer(modifier = Modifier.height(16.dp))
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
fun RasaListItem(data: Breeds, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
    {
        Column() {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                color = MaterialTheme.colorScheme.onTertiary,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                text = if (data.alt_names != null) {
                    "${data.name} ${data.alt_names}"
                } else {
                    data.name
                }
            )
            val desc = if(data.description.length>250){
                data.description.take(247)+"..."
            }else{
                data.description
            }
            Row (){
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .weight(weight = 1f),
                    text = desc,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()){
                Chip(text = data.dog_friendly)
                Chip(text = data.energy_level)
                Chip(text = data.intelligence)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}