package com.anilsandroidtraining.catastrophic.di

import com.anilsandroidtraining.catastrophic.domain.mapper.Mapper
import com.anilsandroidtraining.catastrophic.domain.model.CatsModel
import com.anilsandroidtraining.catastrophic.presentation.mapper.CatListUiMapper
import com.anilsandroidtraining.catastrophic.presentation.model.UiCatModel
import com.anilsandroidtraining.catastrophic.domain.usecase.GetCatsUseCase
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