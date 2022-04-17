package com.zaloracasestudy.catastrophic.presentation

import com.zaloracasestudy.catastrophic.presentation.model.UiCat

sealed class CatListState {
    object Loading : CatListState()
    data class Success(val catList: List<UiCat>?) : CatListState()
    data class Failure(val error: Exception) : CatListState()
}

object ErrorFetchingCatsData : Exception()
object UnknownException : Exception()
