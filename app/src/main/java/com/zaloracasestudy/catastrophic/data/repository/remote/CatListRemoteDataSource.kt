package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDTO
import kotlinx.coroutines.flow.Flow

interface CatListRemoteDataSource {

    suspend fun fetchCatListData(pageIndex: Int): List<CatDTO>
}
