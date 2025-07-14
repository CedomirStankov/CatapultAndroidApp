package com.example.vezbanje.breeds.list

import com.example.vezbanje.breeds.domain.Breeds

data class BreedsListState(
    val loading: Boolean = false,
    val items: List<Breeds> = emptyList(),
    val error: Throwable? = null,
    val query: String = "",
    val filteredItems: List<Breeds> = emptyList(),
    val initialLoading: Boolean = true
) {
}