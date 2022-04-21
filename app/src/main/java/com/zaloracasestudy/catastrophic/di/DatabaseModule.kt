package com.zaloracasestudy.catastrophic.di

import android.content.Context
import androidx.room.Room
import com.zaloracasestudy.catastrophic.data.database.CatAstrophicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "cat_astrophic_db.sqlite"

    @Singleton
    @Provides
    fun provideCatDatabase(
        @ApplicationContext context: Context,
    ): CatAstrophicDatabase = Room.databaseBuilder(
        context,
        CatAstrophicDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCatDao(database: CatAstrophicDatabase) = database.getCatDao()

}