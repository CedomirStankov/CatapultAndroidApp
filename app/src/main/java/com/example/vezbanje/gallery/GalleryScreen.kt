package com.example.vezbanje.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vezbanje.gallery.api.model.CatImage

fun NavGraphBuilder.gallery(
    route: String,
    onPictureClick: (String) -> Unit
) = composable(
    route = "$route/{breedId}",
    arguments = listOf(navArgument("breedId") { type = NavType.StringType })
){
    navBackStackEntry->

    val galleryViewModel = hiltViewModel<GalleryViewModel>(navBackStackEntry)

    val state by galleryViewModel.state.collectAsState()

    MyGallery(
        state = state,
        onPictureClick = onPictureClick
    )
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGallery(
    state: GalleryState,
    onPictureClick: (pictureId: String) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        text = "Gallery",
                        color = Color.Black
                    ) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.background)
            )
        },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.BottomCenter,
            ) {
                val screenWidth = this.maxWidth
                val cellSize = (screenWidth / 2) - 4.dp

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    itemsIndexed(
                        items = state.gallery,
                        key = { index: Int, gallery: CatImage ->
                            gallery.id
                        },
                    ) { index: Int, picture: CatImage ->
                        Card(
                            modifier = Modifier
                                .size(cellSize)
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable {
                                    onPictureClick(picture.id)
                                },
                        ) {
                            GalleryPreview(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surface),
                                picture = picture,
                            )
                        }
                    }

                    item(
                        span = {
                            GridItemSpan(2)
                        }
                    ) {
                    }
                }
            }
        },
    )
}