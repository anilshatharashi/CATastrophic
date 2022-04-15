package com.anilsandroidtraining.catastrophic.domain

import com.anilsandroidtraining.catastrophic.domain.model.CatsModel
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun getCatList(pageIndex: Int): Flow<CatsModel?>

}
