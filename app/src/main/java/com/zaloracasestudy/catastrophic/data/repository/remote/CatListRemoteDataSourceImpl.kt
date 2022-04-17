package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatListRemoteDataSourceImpl @Inject constructor(
    private val api: CatListApi,
) : CatListRemoteDataSource {

    override suspend fun fetchCatListData(pageIndex: Int): Flow<List<CatDataModel>?> {
        val response = api.fetchCatListData(pageIndex)
        return flow { emit(response) }
    }
}