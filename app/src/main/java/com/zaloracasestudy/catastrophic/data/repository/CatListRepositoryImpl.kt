package com.zaloracasestudy.catastrophic.data.repository

import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatListRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatListRemoteDataSource,
    private val mapper: Mapper<List<CatDataModel>?, List<Cat>?>
) : CatListRepository {

    override suspend fun getCatList(pageIndex: Int): Flow<List<Cat>?> =
        remoteDataSource.fetchCatListData(pageIndex).map {
            mapper.mapFrom(it)
        }
}