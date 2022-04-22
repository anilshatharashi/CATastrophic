package com.zaloracasestudy.catastrophic.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.usecase.GetCatsUseCase
import com.zaloracasestudy.catastrophic.presentation.CatListState.Success
import com.zaloracasestudy.catastrophic.presentation.mapper.CatListUiMapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatsViewModelTest {

    private lateinit var catList: MutableSharedFlow<List<Cat>>
    private lateinit var viewModel: CatsViewModel
    private lateinit var getCatsListUseCase: GetCatsUseCase
    private lateinit var uiMapper: CatListUiMapper
    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getCatsListUseCase = mockk()
        catList = MutableSharedFlow()

        uiMapper = CatListUiMapper()
        coEvery { getCatsListUseCase.execute(1) } returns catList

        viewModel = CatsViewModel(getCatsListUseCase, uiMapper)
    }

    @Test
    fun `page should be empty page if emptylist is returned`() {

        observeState {
            viewModel.fetchCatList()
            runTest { catList.emit(emptyList()) }
            verify { it.onChanged(Success(emptyList())) }
        }
    }

    @Test
    fun `getCatsListData should push catLists to the ui`() {
        // GIVEN I select a catList id

        observeState {
            viewModel.fetchCatList()

            // WHEN I catList is emitted
            val catList = listOf(Cat(),
                Cat(),
                Cat())
            runBlocking { this@CatsViewModelTest.catList.emit(catList) }

            // THEN it should be observed
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(catList)))
            }
        }
    }

    @Test
    fun `getCatsListData should only emit catLists for the last requested id`() {
        // GIVEN there are two catList ids
        val firstPageFlow = MutableSharedFlow<List<Cat>>()
        val secondPageFlow = MutableSharedFlow<List<Cat>>()

        coEvery { getCatsListUseCase.execute(1) } returns firstPageFlow
        coEvery { getCatsListUseCase.execute(1) } returns secondPageFlow

        observeState {
            // WHEN I subscribe first to one, then to the second
            viewModel.fetchCatList()
            viewModel.fetchCatList()

            // AND there is a catList for both ids
            val list1 = listOf(Cat(), Cat(id = "42"), Cat(id = "52"))
            val list2 = listOf(Cat(id = "382"), Cat(id = "242"), Cat(id = "2542"))
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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