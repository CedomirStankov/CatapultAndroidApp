package com.example.vezbanje.breeds.list

sealed class BreedsListEvent {
    data class SearchQueryChanged(val query: String) : BreedsListEvent()
}