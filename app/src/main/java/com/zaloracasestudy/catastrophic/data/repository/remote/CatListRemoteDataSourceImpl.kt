package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDTO
import javax.inject.Inject

class CatListRemoteDataSourceImpl @Inject constructor(
    private val api: CatListApi,
) : CatListRemoteDataSource {

    override suspend fun fetchCatListData(pageIndex: Int): List<CatDTO> =
        api.fetchCatListData(pageIndex)
}