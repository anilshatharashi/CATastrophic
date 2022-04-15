package com.anilsandroidtraining.catastrophic.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anilsandroidtraining.catastrophic.domain.model.Cat
import com.anilsandroidtraining.catastrophic.domain.model.CatsModel
import com.anilsandroidtraining.catastrophic.domain.usecase.GetCatsUseCase
import com.anilsandroidtraining.catastrophic.presentation.CatListState.Success
import com.anilsandroidtraining.catastrophic.presentation.mapper.CatListUiMapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatsViewModelTest {

    private lateinit var catsModelFlow: MutableSharedFlow<CatsModel?>
    private lateinit var viewModel: CatsViewModel
    private lateinit var getCatsListUseCase: GetCatsUseCase
    private lateinit var uiMapper: CatListUiMapper

    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getCatsListUseCase = mockk()
        catsModelFlow = MutableSharedFlow()

        uiMapper = CatListUiMapper()
        coEvery { getCatsListUseCase.execute(1) } returns catsModelFlow

        viewModel = CatsViewModel(getCatsListUseCase, uiMapper)
    }

    @Test
    fun `page should be error page if null is returned`() {

        observeState {
            viewModel.fetchCatsList()
            runBlocking { catsModelFlow.emit(null) }
            verify { it.onChanged(CatListState.Failure(ErrorFetchingCatsData)) }
        }
    }

    @Test
    fun `getCatsListData should push catLists to the ui`() {
        // GIVEN I select a catList id

        observeState {
            viewModel.fetchCatsList()

            // WHEN I catList is emitted
            val catList = CatsModel(1, listOf(Cat(),
                Cat(),
                Cat()))
            runBlocking { catsModelFlow.emit(catList) }

            // THEN it should be observed
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(catList)))
            }
        }
    }

    @Test
    fun `getCatsListData should only emit catLists for the last requested id`() {
        // GIVEN there are two catList ids
        val firstPageFlow = MutableSharedFlow<CatsModel>()
        val secondPageFlow = MutableSharedFlow<CatsModel>()

        coEvery { getCatsListUseCase.execute(1) } returns firstPageFlow
        coEvery { getCatsListUseCase.execute(1) } returns secondPageFlow

        observeState {
            // WHEN I subscribe first to one, then to the second
            viewModel.fetchCatsList()
            viewModel.fetchCatsList()

            // AND there is a catList for both ids
            val list1 = CatsModel(
                cats = listOf(Cat(), Cat(id = "42"), Cat(id = "52"))
            )
            val list2 = CatsModel(
                cats = listOf(Cat(id = "382"), Cat(id = "242"), Cat(id = "2542"))
            )
            runBlocking {
                firstPageFlow.emit(list1)
                secondPageFlow.emit(list2)
            }

            // THEN the ui should only get the catList from the last subscribed id
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(list2)))
            }
            verify(exactly = 0) {
                it.onChanged(Success(uiMapper.mapFrom(list1)))
            }
        }
    }

    private fun observeState(block: (Observer<CatListState>) -> Unit) {
        val observer = mockk<Observer<CatListState>>(relaxUnitFun = true)
        viewModel.catListState.observeForever(observer)
        try {
            block(observer)
        } finally {
            viewModel.catListState.removeObserver(observer)
        }
    }
}