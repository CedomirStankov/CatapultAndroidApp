package com.example.vezbanje.gallery.photoviewer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.gallery.repository.GalleryRepository
import com.example.vezbanje.navigation.pictureId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewerViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(){

    private val pictureId : String = savedStateHandle.pictureId
    private val _state = MutableStateFlow(PhotoViewerState())
    val state = _state.asStateFlow()
    private fun setState(reducer: PhotoViewerState.() -> PhotoViewerState) = _state.update(reducer)

    init {
        fetchGallery()
    }

    private fun fetchGallery(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val gallery = withContext(Dispatchers.IO) {
                    galleryRepository.getBreedPhotosbyPictureId(pictureId = pictureId.dropLast(1))
                }
                Log.d("Gale",""+pictureId.dropLast(1))
                setState { copy(gallery = gallery) }
                val index = gallery.indexOfFirst { it.id == pictureId.dropLast(1) }
                Log.d("Gale", ""+index)
                setState { copy(currentIndex = index) }
            } catch (error: Exception) {

            }
            setState { copy(loading = false) }
        }
    }

}