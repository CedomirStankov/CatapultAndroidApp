package com.example.vezbanje.breeds.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import com.example.vezbanje.breeds.list.BreedsDetailsViewModel
import com.example.vezbanje.core.compose.Chip2
import com.example.vezbanje.core.compose.OpenLinkButton
import com.example.vezbanje.quiz.QuizEvent

fun NavGraphBuilder.breedsDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) {
    composable(
        route = route,
        arguments = arguments
    ) { navBackStackEntry ->
        val breedsDetailsViewModel = hiltViewModel<BreedsDetailsViewModel>(navBackStackEntry)
        val state by breedsDetailsViewModel.state.collectAsState()
        BreedsDetailsScreen(state = state, navController = navController)
    }
}

@Composable
fun BreedsDetailsScreen(
    state : BreedsDetailsState,
    navController : NavController
){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(state.loading){
                Spacer(modifier = Modifier.height(100.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(128.dp),
                    color = Color.Black
                )
            }else if(state.error!=null){
                Text(text = "Error. ${state.error.message}", color = Color.Black)
            }else{
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(250.dp),
                        model = state.items?.image?.url,
                        contentDescription = null,
                    )
                    Text(text = "Naziv: "+ (state.items?.details?.name ?: ""))
                    OutlinedButton(onClick = { navController.navigate("pictures/${state.items?.details?.id}") }, colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface)){
                        Text(text = "Galerija")
                    }
                    Log.d("TESTIC","pictures/${state.items?.details?.id}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Opis: "+ (state.items?.details?.description ?: ""))
                    Text(text = "Poreklo: "+ (state.items?.details?.origin ?: ""))
                    Text(text = "Temperament: "+ (state.items?.details?.temperament ?: ""))
                    Text(text = "Zivotni vek: "+ (state.items?.details?.life_span ?: ""))
                    Text(text = "Tezina: "+ (state.items?.details?.weight?.metric ?: ""))
                    if((state.items?.details?.rare ?: "") == 1){
                        Text(text = "Retka vrsta: Da")
                    }else{
                        Text(text = "Retka vrsta: Ne")
                    }
                }
                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Chip2(text = "Child friendly: "+ (state.items?.details?.child_friendly ?: ""))
                    Chip2(text = "Dog friendly: "+ (state.items?.details?.dog_friendly ?: ""))
                    Chip2(text = "Energy level: "+ (state.items?.details?.energy_level ?: ""))
                    Chip2(text = "Health issues: "+ (state.items?.details?.health_issues ?: ""))
                    Chip2(text = "Intelligence: "+ (state.items?.details?.intelligence ?: ""))
                    OpenLinkButton(url = state.items?.details?.wikipedia_url ?: "")
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}