package com.zaloracasestudy.catastrophic.di

import com.zaloracasestudy.catastrophic.platform.NetworkHandler
import com.zaloracasestudy.catastrophic.data.dao.CatDao
import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import com.zaloracasestudy.catastrophic.data.mapper.CatListDomainMapper
import com.zaloracasestudy.catastrophic.data.repository.CatListRepositoryImpl
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCatListDomainMapper(): Mapper<List<CatEntity>, List<Cat>> =
        CatListDomainMapper()

    @Singleton
    @Provides
    fun provideCatListRepository(
        remoteDataSource: CatListRemoteDataSource,
        networkHandler: NetworkHandler,
        catDao: CatDao,
        mapper: Mapper<List<CatEntity>, List<Cat>>,
    ): CatListRepository = CatListRepositoryImpl(remoteDataSource, networkHandler, catDao, mapper)

}