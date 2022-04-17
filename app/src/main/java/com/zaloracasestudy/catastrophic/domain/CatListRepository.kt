package com.zaloracasestudy.catastrophic.domain

import com.zaloracasestudy.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatListRepository {

    suspend fun getCatList(pageIndex: Int): Flow<List<Cat>?>

}
