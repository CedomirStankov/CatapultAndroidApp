package com.example.vezbanje.gallery.photoviewer

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vezbanje.gallery.GalleryPreview
import com.example.vezbanje.gallery.api.model.CatImage

fun NavGraphBuilder.photoviewer(
    route: String,
    arguments: List<NamedNavArgument>,
) = composable(
    route = route,
    arguments = arguments,
) { navBackStackEntry ->
    val pictureId = navBackStackEntry.arguments?.getString("pictureId")
        ?: throw IllegalStateException("pictureId required")

    val photoViewerViewModel = hiltViewModel<PhotoViewerViewModel>(navBackStackEntry)

    val state by photoViewerViewModel.state.collectAsState()

    PhotoViewerScreen(
        state = state,
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoViewerScreen(
    state: PhotoViewerState,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            state.gallery.size
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            if (state.gallery.isNotEmpty()) {
                LaunchedEffect(state.currentIndex) {
                    pagerState.scrollToPage(state.currentIndex)
                }
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
                        val pic = state.gallery.getOrNull(pageIndex)
                    }
                }

                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                    pageSize = PageSize.Fill,
                    pageSpacing = 16.dp,
                    state = pagerState,
                    key = {
                        val pic = state.gallery[it]
                        pic.id
                    }
                ) { pageIndex ->
                    val pic = state.gallery[pageIndex]
                    Log.d("galeri", "${pic}")
                    GalleryPreview(
                        modifier = Modifier,
                        picture = pic,
                        showTitle = false,
                    )
                }

            } else {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "No pictures.",
                )
            }
        },
    )

}