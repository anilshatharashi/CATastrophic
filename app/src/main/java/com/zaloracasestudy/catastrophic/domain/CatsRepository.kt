package com.zaloracasestudy.catastrophic.domain

import com.zaloracasestudy.catastrophic.domain.model.CatsModel
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun getCatList(pageIndex: Int): Flow<CatsModel?>

}
