package com.zaloracasestudy.catastrophic.domain.usecase

import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.model.CatsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCatsUseCase ://@Inject constructor(private val repository: CatsRepository) :
    UseCase<Int, CatsModel?>() {

    //TODO remove this once the real data is is in place fake data
    private val model: CatsModel = CatsModel()

    init {
        //TODO remove this once the real data is is in place fake data
        model.cats = listOf(
            Cat("1", "blabla", 480, 480),
            Cat("2", "blabla2", 480, 480),
            Cat("4234", "blabla2", 480, 480),
            Cat("22342", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("242", "blabla2", 480, 480),
            Cat("3", "blabla3", 480, 480)
        )

    }

    override suspend fun buildUseCase(params: Int): Flow<CatsModel?> = flowOf(model)
    //repository.getCatList(params)

}
