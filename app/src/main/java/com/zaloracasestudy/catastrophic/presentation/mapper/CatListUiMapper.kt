package com.zaloracasestudy.catastrophic.presentation.mapper

import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.domain.model.CatsModel
import com.zaloracasestudy.catastrophic.presentation.model.UiCat
import com.zaloracasestudy.catastrophic.presentation.model.UiCatModel

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
