package com.zaloracasestudy.catastrophic.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val id: String,
    val url: String,
    val width: Long,
    val height: Long,
)