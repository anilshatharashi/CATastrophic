/*
package com.zaloracasestudy.catastrophic.data.repository.remote
import android.content.Context
import com.google.gson.Gson
import com.zaloracasestudy.catastrophic.data.repository.readJsonFromAssets
import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// This implementation would be replaced by Retrofit Implementation when we use the Actual Network call
class CatListApiImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : CatListApi {

    companion object {
        private const val MOCK_RESPONSE_FILE_NAME = "catlist_response.json"
    }

    override suspend fun fetchCatListData(
        pageIndex: Int,
        limit: Int,
        order: String,
        mimeTypes: String,
    ): Flow<List<CatDataModel>> =
        flow { emit(readJsonFromAssets(context, gson, MOCK_RESPONSE_FILE_NAME)) }

}*/
