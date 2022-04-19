package com.zaloracasestudy.catastrophic.domain

import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.usecase.GetCatsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCatListUseCaseTest {
    private lateinit var useCase: GetCatsUseCase
    private lateinit var repository: CatListRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetCatsUseCase(repository)
    }

    @Test
    fun `test execute calls the repository method only once`() {
        val flow = mockk<Flow<List<Cat>>>()
        coEvery { repository.getCatList(any()) } returns flow

        runBlocking { useCase.execute(1) }

        coVerify(exactly = 1) { repository.getCatList(1) }
    }

    @Test
    fun `test buildUseCase returns the correct data`() {
        val flow = mockk<Flow<List<Cat>>>()
        coEvery { repository.getCatList(any()) } returns flow

        val result = runBlocking { useCase.execute(1) }

        assertEquals(flow, result)
    }
}