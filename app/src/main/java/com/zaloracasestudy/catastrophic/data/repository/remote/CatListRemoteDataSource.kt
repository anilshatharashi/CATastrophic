package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import kotlinx.coroutines.flow.Flow

interface CatListRemoteDataSource {

    suspend fun fetchCatListData(pageIndex: Int): Flow<List<CatDataModel>?>
}
