package com.zaloracasestudy.catastrophic.domain.usecase

import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: CatListRepository) :
    UseCase<Int, List<Cat>?>() {

    override suspend fun buildUseCase(params: Int): Flow<List<Cat>?> = repository.getCatList(params)

}
