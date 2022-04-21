package com.zaloracasestudy.catastrophic.presentation

import androidx.lifecycle.*
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
    private val mapper: Mapper<List<Cat>, List<UiCat>>,
) : ViewModel() {

    val pageIndex = MutableLiveData(1)
    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNextPageLoading = MutableLiveData(false)
    val isNextPageLoading: LiveData<Boolean> = _isNextPageLoading

    private val _catListState = MutableStateFlow<CatListState>(CatListState.Loading)
    val catListState: LiveData<CatListState> = _catListState.asLiveData()

    init {
        pageIndex.observeForever { fetchCatList() }
    }

    fun fetchCatList() {
        val currentPage = pageIndex.value ?: 1
        if (currentPage > 1) _isNextPageLoading.postValue(true)

        viewModelScope.launch {
            runCatching {
                getCatsUseCase.execute(currentPage).collectLatest {
                    _catListState.value = handleSuccess(it)
                }
            }.onFailure {
                _catListState.value = Failure(UnknownException)
            }
        }
    }

    private fun handleSuccess(list: List<Cat>): Success {
        _isLastPage.postValue(false)
        _isNextPageLoading.postValue(false)
        return Success(mapper.mapFrom(list))
    }

}
