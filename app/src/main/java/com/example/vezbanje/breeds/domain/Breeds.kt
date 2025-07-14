package com.example.vezbanje.breeds.domain

data class Breeds (
    val id: String,
    val name: String,
    val alt_names: String? = null,
    val description: String,
    val dog_friendly: String,
    val energy_level: String,
    val intelligence: String
){
}