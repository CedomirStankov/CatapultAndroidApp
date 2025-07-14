package com.example.vezbanje.breeds.list.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedsData (
    @PrimaryKey val id: String,
    val name: String,
    val alt_names: String? = null,
    val description: String,
    val dog_friendly: String,
    val energy_level: String,
    val intelligence: String,
    val reference_image_id : String,
    val origin : String,
    val temperament : String,
    val life_span : String,
    val weight_imperial : String,
    val weight_metric : String,
    val child_friendly: Int,
    val health_issues: Int,
    val rare : Int,
    val wikipedia_url : String
)