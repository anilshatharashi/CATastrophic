package com.zaloracasestudy.catastrophic.data.repository

import android.util.Log
import com.zaloracasestudy.catastrophic.platform.NetworkHandler
import com.zaloracasestudy.catastrophic.data.dao.CatDao
import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatListRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatListRemoteDataSource,
    private val networkHandler: NetworkHandler,
    private val catDao: CatDao,
    private val mapper: Mapper<List<CatEntity>, List<Cat>>,
) : CatListRepository {

    private var requestedCatListIndex: Int = 1

    override suspend fun getCatList(pageIndex: Int): Flow<List<Cat>> {
        Log.i("***", " pageIndex = $pageIndex")
        var catListFlow = loadCatListFromDb(pageIndex)
        if (catListFlow.first().isEmpty()) {
            Log.i("***", " List is EmptyList = ")
            if (!networkHandler.isConnected()) return flow {
                Log.i("***", " eMITING EmptyList = ")
                emit(emptyList())
            }
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
            catListFlow = loadCatListFromDb(pageIndex)
        }
        return catListFlow
    }

    fun loadCatListFromDb(pageIndex: Int): Flow<List<Cat>> {
        if (pageIndex == 1) requestedCatListIndex = 1 else requestedCatListIndex += PAGE_SIZE
        Log.i("***", " requestedCatListIndex = $requestedCatListIndex")
        val toId = requestedCatListIndex + PAGE_SIZE - 1
        Log.i("***", " requestedCatListIndex + PAGE_SIZE -1 = $toId")

        return catDao.getCatList(requestedCatListIndex - 1, toId)
            .map { mapper.mapFrom(it) }
    }

    companion object {
        // Better way of doing this is, set these values in domain or repository and use it from there in RecyclerViewPaginationListener
        // But for now, I just declared as constants here as these depends on the constant values of
        // RecyclerViewPaginationListener
        const val PAGE_SIZE = 20
    }

}