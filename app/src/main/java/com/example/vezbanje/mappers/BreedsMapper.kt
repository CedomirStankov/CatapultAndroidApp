package com.example.vezbanje.mappers

import com.example.vezbanje.breeds.list.db.BreedsData
import com.example.vezbanje.networking.api.apiModel.BreedsApiModel

fun BreedsApiModel.asBreedsDbModel(): BreedsData{
    return BreedsData(
        id = this.id,
        name = this.name,
        alt_names=this.alt_names,
        description=this.description,
        dog_friendly=this.dog_friendly.toString(),
        energy_level=this.energy_level.toString(),
        intelligence=this.intelligence.toString(),
        reference_image_id=this.reference_image_id ,
        origin=this.origin,
        temperament=this.temperament,
        life_span=this.life_span,
        weight_imperial = this.weight.imperial,
        weight_metric = this.weight.metric,
        child_friendly=this.child_friendly,
        health_issues=this.health_issues,
        rare=this.rare,
        wikipedia_url=this.wikipedia_url
    )
}