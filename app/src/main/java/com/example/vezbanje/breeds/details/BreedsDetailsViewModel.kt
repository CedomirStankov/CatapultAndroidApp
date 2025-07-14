package com.example.vezbanje.breeds.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.breeds.details.BreedsDetailsState
import com.example.vezbanje.breeds.domain.BreedsDetails
import com.example.vezbanje.breeds.domain.BreedsDetailsWithImage
import com.example.vezbanje.breeds.list.db.BreedsData
import com.example.vezbanje.breeds.repository.BreedsDetailsRepository
import com.example.vezbanje.gallery.api.model.CatImage
import com.example.vezbanje.navigation.id
import com.example.vezbanje.networking.api.apiModel.Weight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreedsDetailsViewModel @Inject constructor(
    private val repository: BreedsDetailsRepository,
    savedStateHandle: SavedStateHandle,

): ViewModel() {

    private val id : String = savedStateHandle.id
    private val _state = MutableStateFlow(BreedsDetailsState())
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedsDetailsState.() -> BreedsDetailsState) = _state.getAndUpdate(reducer)

    init{
        viewModelScope.launch {
            setState { copy(loading = true) }
            try{
                val data = withContext(Dispatchers.IO){
                    repository.getBreedDetailsById(id).mapiranje()
                }
                val image = withContext(Dispatchers.IO){
                    repository.getImage(data.reference_image_id)
                }
                val dataImage = BreedsDetailsWithImage(data, CatImage(image.id,image.url,image.width,image.height))
                setState { copy(items = dataImage) }
            }catch (error: IOException){
                setState { copy(error = error) }
            } finally {
                setState { copy(loading = false) }
            }
        }
    }
}

private fun BreedsData.mapiranje() = BreedsDetails(
    id=this.id,
    reference_image_id=this.reference_image_id,
    name = this.name,
    description = this.description,
    origin = this.origin,
    temperament = this.temperament,
    life_span = this.life_span,
    weight = Weight(this.weight_imperial,this.weight_metric),
    dog_friendly = this.dog_friendly.toInt(),
    energy_level = this.energy_level.toInt(),
    intelligence = this.intelligence.toInt(),
    child_friendly = this.child_friendly,
    health_issues  = this.health_issues,
    rare = this.rare,
    wikipedia_url = this.wikipedia_url
)