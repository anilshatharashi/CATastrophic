package com.zaloracasestudy.catastrophic.di

import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.CatsModel
import com.zaloracasestudy.catastrophic.presentation.mapper.CatListUiMapper
import com.zaloracasestudy.catastrophic.presentation.model.UiCatModel
import com.zaloracasestudy.catastrophic.domain.usecase.GetCatsUseCase
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
    fun provideCatListUiMapper(): Mapper<CatsModel?, UiCatModel> = CatListUiMapper()

    @Provides
    @ViewModelScoped
    fun provideGetCatsUseCase(): GetCatsUseCase = GetCatsUseCase()

}