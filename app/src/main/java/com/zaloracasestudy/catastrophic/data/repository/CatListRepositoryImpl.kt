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
                            height = height ?: 0).also {
                            Log.i("***", " insertOrUpdate CatEntity = $it")
                        }
                    )
                }
            }
        }
        return loadCatListFromDb(pageIndex)
    }

    fun loadCatListFromDb(pageIndex: Int): Flow<List<Cat>> =
        catDao.getCatList(pageIndex - BEGINING_FROM_ID, pageIndex + UPTO_ID)
            .map { mapper.mapFrom(it) }

    companion object {
        // Better way of doing this is, set these values in domain or repository and use it from there in RecyclerViewPaginationListener
        // But for now, I just declared as constants here as these depends on the constant values of
        // RecyclerViewPaginationListener
        const val BEGINING_FROM_ID = 1
        const val UPTO_ID = 19
    }

}