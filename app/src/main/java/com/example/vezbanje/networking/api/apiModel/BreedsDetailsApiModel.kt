package com.example.vezbanje.networking.api.apiModel

import kotlinx.serialization.Serializable

@Serializable
data class BreedsDetailsApiModel(
    val id : String,
    val reference_image_id : String = "",
    val name : String,
    val description : String,
    val origin : String,
    val temperament : String,
    val life_span : String,
    val weight : Weight,
    val dog_friendly: Int,
    val energy_level: Int,
    val intelligence: Int,
    val child_friendly: Int,
    val health_issues: Int,
    val rare : Int,
    val wikipedia_url : String
)

@Serializable
data class Weight(
    val imperial : String,
    val metric : String
)