package com.zaloracasestudy.catastrophic.di

import com.zaloracasestudy.catastrophic.domain.CatListRepository
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.usecase.GetCatsUseCase
import com.zaloracasestudy.catastrophic.presentation.mapper.CatListUiMapper
import com.zaloracasestudy.catastrophic.presentation.model.UiCat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object DomainModule {

    @ViewModelScoped
    @Provides
    fun provideCatListUiMapper(): Mapper<List<Cat>?, List<UiCat>?> = CatListUiMapper()

    @Provides
    @ViewModelScoped
    fun provideGetCatsUseCase(repository: CatListRepository): GetCatsUseCase =
        GetCatsUseCase(repository)

}