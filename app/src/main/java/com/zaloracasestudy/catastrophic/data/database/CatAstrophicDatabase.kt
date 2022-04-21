package com.zaloracasestudy.catastrophic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaloracasestudy.catastrophic.data.dao.CatDao
import com.zaloracasestudy.catastrophic.data.entities.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class CatAstrophicDatabase : RoomDatabase() {

    abstract fun getCatDao(): CatDao

}