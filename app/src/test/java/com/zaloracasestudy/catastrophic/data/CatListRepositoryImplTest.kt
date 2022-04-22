package com.zaloracasestudy.catastrophic.data

import com.zaloracasestudy.catastrophic.data.dao.CatDao
import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import com.zaloracasestudy.catastrophic.data.mapper.CatListDomainMapper
import com.zaloracasestudy.catastrophic.data.model.CatDTO
import com.zaloracasestudy.catastrophic.data.repository.CatListRepositoryImpl
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.platform.NetworkHandler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
@ExperimentalCoroutinesApi
class CatListRepositoryImplTest {

    private var mapper: CatListDomainMapper = mockk()
    private var remoteDataSource: CatListRemoteDataSource = mockk()
    private var catDao: CatDao = mockk()
    private var networkHandler: NetworkHandler = mockk()
    private var repository = CatListRepositoryImpl(remoteDataSource, networkHandler, catDao, mapper)
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `emptyList should be returned when it's not connected to internet`() {
        val catList = mutableListOf<CatDTO>()

        every { networkHandler.isConnected() } returns false
        every { mapper.mapFrom(any()) } returns emptyList()
        coEvery { catDao.getCatList(any(), any()) } returns flowOf(emptyList())
        coEvery { remoteDataSource.fetchCatListData(any()) } returns catList

        runTest {
            val result = repository.getCatList(1)

            assertEquals(result.first(), flowOf(emptyList<Cat>()).first())
        }
    }

    @Test
    fun `getCatListList calls a correct function`() = runBlocking {
        val catList = mutableListOf<CatDTO>()

        every { networkHandler.isConnected() } returns true
        every { mapper.mapFrom(any()) } returns emptyList()
        coEvery { catDao.getCatList(any(), any()) } returns flowOf(emptyList())
        coEvery { remoteDataSource.fetchCatListData(any()) } returns catList

        repository.getCatList(1)

        coVerify(exactly = 1) { remoteDataSource.fetchCatListData(1) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}