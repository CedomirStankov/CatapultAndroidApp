package com.example.vezbanje.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.gallery.repository.GalleryRepository
import com.example.vezbanje.navigation.breedId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(){

    private val breedId : String = savedStateHandle.breedId
    private val _state = MutableStateFlow(GalleryState())
    val state = _state.asStateFlow()
    private fun setState(reducer: GalleryState.() -> GalleryState) = _state.update(reducer)

    init {
        fetchGallery()
    }

    private fun fetchGallery(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val gallery = withContext(Dispatchers.IO) {
                    galleryRepository.getBreedPhotos(breedId = breedId)
                }
                setState { copy(gallery = gallery) }
                //fetchGalleryPictures()
            } catch (error: Exception) {
                // TODO Handle error
            }
            setState { copy(loading = false) }
        }
    }
}