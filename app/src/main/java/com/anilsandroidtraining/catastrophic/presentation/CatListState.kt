package com.anilsandroidtraining.catastrophic.presentation

import com.anilsandroidtraining.catastrophic.presentation.model.UiCatModel

sealed class CatListState {
    object Loading : CatListState()
    data class Success(val uiModel: UiCatModel) : CatListState()
    data class Failure(val error: Exception) : CatListState()
}

object ErrorFetchingCatsData : Exception()
object UnknownException : Exception()
