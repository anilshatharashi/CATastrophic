package com.zaloracasestudy.catastrophic.presentation.model

data class UiCatModel(
    var page: Int = 1,
    var cats: List<UiCat> = emptyList(),
    var totalPages: Int = 1,
    var order: String? = "Asc",
)

data class UiCat(
    val id: String,
    val url: String,
    val width: Long,
    val height: Long,
)
