package com.zaloracasestudy.catastrophic.presentation.mapper

import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat
import com.zaloracasestudy.catastrophic.presentation.model.UiCat

class CatListUiMapper : Mapper<List<Cat>, List<UiCat>> {

    override fun mapFrom(from: List<Cat>): List<UiCat> = from.map {
        UiCat(
            it.id,
            it.url,
            it.width,
            it.height,
        )
    }

}
