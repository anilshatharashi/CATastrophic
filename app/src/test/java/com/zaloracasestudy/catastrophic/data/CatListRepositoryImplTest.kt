package com.zaloracasestudy.catastrophic.data

import com.zaloracasestudy.catastrophic.data.mapper.CatListDomainMapper
import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import com.zaloracasestudy.catastrophic.data.repository.CatListRepositoryImpl
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class CatListRepositoryImplTest {

    private lateinit var mapper: Mapper<List<CatDataModel>?, List<Cat>?>
    private lateinit var remoteDataSource: CatListRemoteDataSource
    private lateinit var repository: CatListRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        mapper = CatListDomainMapper()
        repository = CatListRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun `getCatListList calls a correct function`() = runBlocking {
        val catListFlow = MutableSharedFlow<List<CatDataModel>>()
        coEvery { remoteDataSource.fetchCatListData(1) } returns catListFlow

        repository.getCatList(1)

        coVerify(exactly = 1) { remoteDataSource.fetchCatListData(1) }
    }

    @Test
    fun `test exception thrown from the network call should not be caught inside repository`() {
        coEvery { remoteDataSource.fetchCatListData(1) } throws UnknownHostException()
        runBlocking {
            try {
                repository.getCatList(1)
            } catch (e: Exception) {
                assertTrue(e is UnknownHostException)
            }
        }
    }
}