package com.zaloracasestudy.catastrophic.data.model

import com.google.gson.annotations.SerializedName

data class CatDTO(
    @SerializedName("breeds")
    var breedData: List<BreedData>?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("width")
    var width: Long?,
    @SerializedName("height")
    var height: Long?,
    @SerializedName("categories")
    var categories: List<CategoryData>?,
)

data class BreedData(
    @SerializedName("weight")
    var weight: WeightData,
    var id: String,
    var name: String,
    @SerializedName("cfa_url")
    var cfaURL: String,
    @SerializedName("vetstreet_url")
    var vetstreetURL: String,
    @SerializedName("vcahospitals_url")
    var vcahospitalsURL: String,
    var temperament: String,
    var origin: String,
    @SerializedName("country_codes")
    var countryCodes: String,
    @SerializedName("country_code")
    var countryCode: String,
    var description: String,
    @SerializedName("life_span")
    var lifeSpan: String,
    var indoor: Long,
    var lap: Long,
    @SerializedName("alt_names")
    var altNames: String,
    var adaptability: Long,
    var affectionLevel: Long,
    @SerializedName("child_friendly")
    var childFriendly: Long,
    @SerializedName("dog_friendly")
    var dogFriendly: Long,
    @SerializedName("entry_level")
    var energyLevel: Long,
    var grooming: Long,
    @SerializedName("health_issues")
    var healthIssues: Long,
    var intelligence: Long,
    @SerializedName("shedding_level")
    var sheddingLevel: Long,
    @SerializedName("social_needs")
    var socialNeeds: Long,
    var strangerFriendly: Long,
    var vocalisation: Long,
    var experimental: Long,
    var hairless: Long,
    var natural: Long,
    var rare: Long,
    var rex: Long,
    var suppressedTail: Long,
    @SerializedName("short_legs")
    var shortLegs: Long,
    @SerializedName("wikipedia_url")
    var wikipediaURL: String,
    var hypoallergenic: Long,
    @SerializedName("reference_image_id")
    var referenceImageID: String,
)

data class WeightData(
    var imperial: String,
    var metric: String,
)

data class CategoryData(
    var id: Long,
    var name: String,
)
