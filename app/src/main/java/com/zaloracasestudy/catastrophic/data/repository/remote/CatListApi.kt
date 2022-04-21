package com.zaloracasestudy.catastrophic.data.repository.remote

import com.zaloracasestudy.catastrophic.data.model.CatDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CatListApi {

    //v1/images/search?limit=30&page=1&mime_types=png&&order=Desc
    @GET("/v1/images/search")
    suspend fun fetchCatListData(
        // I am changing only this as I need to retrieve the infinite scrolling list
        @Query("page") pageIndex: Int = 1,
        // taking the default values of following params
        @Query("limit") limit: Int = LIMIT_PER_PAGE,
        @Query("order") order: String = ORDER_BY,
        @Query("mime_types") mimeTypes: String = MIME_TYPES,
    ): List<CatDTO>

    companion object {
        const val LIMIT_PER_PAGE = 20
        const val ORDER_BY = "Asc"
        const val MIME_TYPES = "png"
    }
}
