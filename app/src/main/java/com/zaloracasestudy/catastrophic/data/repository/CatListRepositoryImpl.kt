package com.zaloracasestudy.catastrophic.data.repository

import android.util.Log
import com.zaloracasestudy.catastrophic.data.dao.CatDao
import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class CatListRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatListRemoteDataSource,
    private val catDao: CatDao,
    private val mapper: Mapper<List<CatEntity>, List<Cat>>,
) : CatListRepository {

    override suspend fun getCatList(pageIndex: Int): Flow<List<Cat>> {
        val listFromDb = loadCatListFromDb(pageIndex)
        if (listFromDb.first().isEmpty()) {
            remoteDataSource.fetchCatListData(pageIndex).map { catDto ->
                with(catDto) {
                    Log.i("***", "Fetched From Remote CatsDTO = $this")
                    catDao.insertOrUpdate(
                        CatEntity(
                            id = id ?: "",
                            url = url ?: "",
                            width = width ?: 0,
                            height = height ?: 0,
                            page_index = pageIndex).also {
                            Log.i("***", " insertOrUpdate CatEntity = $it")
                        }
                    )
                }
            }
        }
        return loadCatListFromDb(pageIndex)
    }

    fun loadCatListFromDb(pageIndex: Int): Flow<List<Cat>> =
        catDao.getCatList(pageIndex).map { mapper.mapFrom(it).also {
            Log.i("***", "loaded and mapped CatList = ${it.toList()}")
        } }

}