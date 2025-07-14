package com.example.vezbanje.networking.api.apiModel

import kotlinx.serialization.Serializable

@Serializable
data class BreedsApiModel(
    val id: String,
    val name: String,
    val alt_names: String? = null,
    val description: String,
    val dog_friendly: Int,
    val energy_level: Int,
    val intelligence: Int,
    val reference_image_id : String = "",
    val origin : String,
    val temperament : String,
    val life_span : String,
    val weight : Weight,
    val child_friendly: Int,
    val health_issues: Int,
    val rare : Int,
    val wikipedia_url : String = ""
)