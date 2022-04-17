package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatListRemoteDataSourceImpl @Inject constructor(
    private val api: CatListApi,
) : CatListRemoteDataSource {

    override suspend fun fetchCatListData(pageIndex: Int): Flow<List<CatDataModel>?> =
       api.fetchCatListData(pageIndex)
}