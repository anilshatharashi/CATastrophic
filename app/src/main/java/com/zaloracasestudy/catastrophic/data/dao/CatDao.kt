package com.zaloracasestudy.catastrophic.data.dao

import androidx.room.*
import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cat WHERE page_index= :pageNo ")
    fun getCatList(pageNo: Int): Flow<List<CatEntity>>

    @Transaction
    suspend fun insertOrUpdate(catEntity: CatEntity) {
        val id = insert(catEntity)
        if (id == -1L) update(CatEntity(
            id = catEntity.id,
            url = catEntity.url,
            width = catEntity.width,
            height = catEntity.height))
    }

    @Update(entity = CatEntity::class)
    suspend fun update(entity: CatEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: CatEntity): Long

    @Transaction
    suspend fun insertOrUpdateList(list: List<CatEntity>) {
        val insertResult = insertList(list)
        val updateList = mutableListOf<CatEntity>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) updateList.add(list[i])
        }

        if (updateList.isNotEmpty()) updateList(updateList)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(list: List<CatEntity>): List<Long>

    @Update
    suspend fun updateList(list: List<CatEntity>)

}
