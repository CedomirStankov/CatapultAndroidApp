package com.example.vezbanje.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.breeds.domain.Breeds
import com.example.vezbanje.breeds.list.db.BreedsData
import com.example.vezbanje.breeds.repository.BreedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val repository: BreedsRepository // Prosledjujemo mu repository koji je singleton (object) pa ce biti samo jedna instanca
    // ovo nece morati da se prosledi unutar ListScreen jer je singleton i za svaku instancu viewModela koristice se isti BreedsRepository
): ViewModel() {

    // Koristimo MutableStateFlow jer hocemo da menjamo stanje tj state npr pozivom getAndUpdate ali samo unutar ViewModela
    private val _state = MutableStateFlow(BreedsListState()) // ovo je privatno da ne bismo mogli da pristupimo ovom polju unutar BreedsListScreen i zatim pristupili metodi getAndUpdate sto bi narusilo arhitekturu jer iz BreedsListScreen ne smemo da setujemo stanje
    val state = _state.asStateFlow() // ovde exposujemo state da bude vidljiv u BreedsListScreen ali da je samo view only da bismo mogli da uzimamo podatke iz state-a (read only)
    private fun setState(reducer: BreedsListState.() -> BreedsListState) = _state.getAndUpdate(reducer) // ovo je samo da bi lepse bilo zapisano setovanje state-a da ne mora da se svaki put poziva getAndUpdate

    private val events = MutableSharedFlow<BreedsListEvent>()
    fun setEvent(event: BreedsListEvent) = viewModelScope.launch { events.emit(event) }

    private var originalData: List<Breeds> = emptyList()
    private var flag = true

    init{
        fetchAllBreeds()
        observeBreeds()
        observeEvents()
//        viewModelScope.launch {
//            repository.fetchAllPictures()
//        }
    }

    private fun fetchAllBreeds(){
        viewModelScope.launch {
            setState { copy(loading = true) } // kada se pokrene aplikacija dok se ne ucitaju podaci loading iz state se stavlja na true i tada se iz UI prikazuje ucitavanje (onaj krug)
            try{
                withContext(Dispatchers.IO){ // ovo se izvrsava na IO threadu jer fetch-ovanje cesto moze da traje dugo i onda bi se ostatak izvrsavanja koda blokirao dok se fetch ne zavrsi sto nije dobro
                    repository.fetchAllBreeds()
                    if(flag){
                        repository.fetchMainPictureHelper()
                        //repository.fetchAllPictures()
                        flag=false
                    }
                }
                //originalData=data
                //setState { copy(items = data) } // kada se ucitaju podaci oni se setuju na items iz state-a
            }catch (error: IOException){
                setState { copy(error = error) } // ako dodje do greske error iz state se setuje na vrednost error-a i on vise nije null
            } finally {
                setState { copy(loading = false) } // u oba slucaja i nakon try i nakon catch treba da se loading vrati na false da bi se prikazao items ili error
            }
        }
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            setState { copy(initialLoading = true) }
            repository.observeAllBreeds()
                .distinctUntilChanged()
                .collect {
                    setState {
                        copy(initialLoading = false,
                            items = it.map { it.asBreedsUiModel() })
                    }
                    originalData = it.map { it.asBreedsUiModel() }
                }
        }
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect{
                when(it){
                    is BreedsListEvent.SearchQueryChanged -> {
                        handleSearch(it.query)
                    }
                }
            }
        }
    }

    private fun handleSearch(query: String) {
        viewModelScope.launch {
            if(query.isEmpty()){
                setState { copy(items = originalData, query = "") }
            }else{
                val filteredBreeds = originalData.filter { breed ->
                    breed.name.contains(query, ignoreCase = true)
                }
                setState { copy(items = filteredBreeds, query = query) }
            }
        }

    }

    private fun BreedsData.asBreedsUiModel() = Breeds(
        id = this.id,
        name = this.name,
        alt_names = if(this.alt_names==""){
            this.alt_names
        }else{
            "("+this.alt_names+")"
        },
        description = this.description,
        energy_level = "Energy level: "+this.energy_level,
        dog_friendly = "Dog friendly: "+this.dog_friendly,
        intelligence = "Intelligence: "+this.intelligence,
    )

}