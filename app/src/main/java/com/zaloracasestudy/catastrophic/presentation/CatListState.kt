package com.zaloracasestudy.catastrophic.presentation

import com.zaloracasestudy.catastrophic.presentation.model.UiCatModel

sealed class CatListState {
    object Loading : CatListState()
    data class Success(val uiModel: UiCatModel) : CatListState()
    data class Failure(val error: Exception) : CatListState()
}

object ErrorFetchingCatsData : Exception()
object UnknownException : Exception()
