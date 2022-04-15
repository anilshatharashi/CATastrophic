package com.anilsandroidtraining.catastrophic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anilsandroidtraining.catastrophic.domain.mapper.Mapper
import com.anilsandroidtraining.catastrophic.domain.model.CatsModel
import com.anilsandroidtraining.catastrophic.presentation.CatListState.Failure
import com.anilsandroidtraining.catastrophic.presentation.CatListState.Success
import com.anilsandroidtraining.catastrophic.presentation.model.UiCatModel
import com.anilsandroidtraining.catastrophic.domain.usecase.GetCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val mapper: Mapper<CatsModel?, UiCatModel>,
) : ViewModel() {

    val pageIndex = MutableLiveData(1)

    private val _catListState = MutableStateFlow<CatListState>(CatListState.Loading)
    val catListState: LiveData<CatListState> = _catListState.asLiveData()

    init {
        pageIndex.observeForever { fetchCatsList() }
    }

    fun fetchCatsList() {
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

    private fun handleSuccess(catsModel: CatsModel): Success {
        val uiModel = mapper.mapFrom(catsModel)
        return Success(uiModel)
    }

}
