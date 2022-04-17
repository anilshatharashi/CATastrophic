package com.zaloracasestudy.catastrophic.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.usecase.GetCatsUseCase
import com.zaloracasestudy.catastrophic.presentation.CatListState.Failure
import com.zaloracasestudy.catastrophic.presentation.CatListState.Success
import com.zaloracasestudy.catastrophic.presentation.model.UiCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val mapper: Mapper<List<Cat>?, List<UiCat>?>,
) : ViewModel() {

    val pageIndex = MutableLiveData(1)

    private val _catListState = MutableStateFlow<CatListState>(CatListState.Loading)
    val catListState: LiveData<CatListState> = _catListState.asLiveData()

    init {
        pageIndex.observeForever { fetchCatList() }
    }

    fun fetchCatList() {
        val currentPage = pageIndex.value ?: 1

        viewModelScope.launch {
            try {
                getCatsUseCase.execute(currentPage).collectLatest { result ->
                    _catListState.value = result?.let { handleSuccess(it) }
                        ?: Failure(ErrorFetchingCatsData)
                }
            } catch (exception: Exception) {
                _catListState.value = Failure(UnknownException)
            }
        }
    }

    private fun handleSuccess(list: List<Cat>?): Success {
        val uiModel = mapper.mapFrom(list)
        Log.i("***", "uiModel = $uiModel")
        return Success(uiModel)
    }

}