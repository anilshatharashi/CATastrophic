package com.anilsandroidtraining.catastrophic.data.model

data class CatsDataModel(
    var page: Int = 1,
    var cats: List<DataCat> = emptyList(),
    var totalPages: Int = 1,
    var order: String? = "Asc",
)

data class DataCat(
    val breeds: List<Breed>,
    val id: String,
    val url: String,
    val width: Long,
    val height: Long,
    val categories: List<Category>? = null,
)

data class Breed(
    val weight: Weight,
    val id: String,
    val name: String,
    val cfaURL: String,
    val vetstreetURL: String,
    val vcahospitalsURL: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val description: String,
    val lifeSpan: String,
    val indoor: Long,
    val lap: Long,
    val altNames: String,
    val adaptability: Long,
    val affectionLevel: Long,
    val childFriendly: Long,
    val dogFriendly: Long,
    val energyLevel: Long,
    val grooming: Long,
    val healthIssues: Long,
    val intelligence: Long,
    val sheddingLevel: Long,
    val socialNeeds: Long,
    val strangerFriendly: Long,
    val vocalisation: Long,
    val experimental: Long,
    val hairless: Long,
    val natural: Long,
    val rare: Long,
    val rex: Long,
    val suppressedTail: Long,
    val shortLegs: Long,
    val wikipediaURL: String,
    val hypoallergenic: Long,
    val referenceImageID: String,
)

data class Weight(
    val imperial: String,
    val metric: String,
)

data class Category(
    val id: Long,
    val name: String,
)
