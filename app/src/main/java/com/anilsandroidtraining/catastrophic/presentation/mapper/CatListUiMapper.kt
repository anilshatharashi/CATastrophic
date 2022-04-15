package com.anilsandroidtraining.catastrophic.presentation.mapper

import com.anilsandroidtraining.catastrophic.domain.mapper.Mapper
import com.anilsandroidtraining.catastrophic.domain.model.Cat
import com.anilsandroidtraining.catastrophic.domain.model.CatsModel
import com.anilsandroidtraining.catastrophic.presentation.model.UiCat
import com.anilsandroidtraining.catastrophic.presentation.model.UiCatModel

class CatListUiMapper : Mapper<CatsModel?, UiCatModel> {

    override fun mapFrom(from: CatsModel?): UiCatModel =
        UiCatModel(
            from?.page ?: 1,
            from?.cats?.let { mapToUiCatList(it) } ?: emptyList(),
            from?.totalPages ?: 1,
            from?.order
        )

    private fun mapToUiCatList(cats: List<Cat>?) = cats?.map {
        UiCat(
            it.id ?: "",
            it.url ?: "",
            it.width ?: 0,
            it.height ?: 0,
        )
    }

}
