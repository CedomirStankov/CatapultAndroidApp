package com.example.vezbanje.accountDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vezbanje.auth.AuthStore
import com.example.vezbanje.login.isValidEmail
import com.example.vezbanje.login.isValidNickname
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun NavGraphBuilder.changeAccountDetails(
    route: String,
    navController : NavController,
    authData: AuthStore,
) = composable(route = route){

    ChangeAccountDetailsScreen(
        navController = navController,
        authData = authData,
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAccountDetailsScreen(navController: NavController, authData: AuthStore) {
    val data = authData.authData.value.split(" ")
    var fullName by remember { mutableStateOf(data[0]) }
    var nickname by remember { mutableStateOf(data[1]) }
    var email by remember { mutableStateOf(data[2]) }
    var errorMessage by remember { mutableStateOf("") }

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
    ){
        Scaffold(
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
                                text = "Change Account Details",
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
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(color = Color.Black, text = "Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = { Text(color = Color.Black, text = "Nickname") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(color = Color.Black, text = "Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            if (fullName.isNotBlank() && isValidNickname(nickname) && isValidEmail(email)) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    authData.updateAuthData(fullName +" "+ nickname+" "+email)
                                }
                                navController.navigate("breeds")
                            } else {
                                errorMessage = "Invalid input. Please check your details."
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Change")
                    }
                    if (errorMessage.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        )
    }

}
