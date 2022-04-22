package com.zaloracasestudy.catastrophic.data.mapper

import com.zaloracasestudy.catastrophic.data.entities.CatEntity
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat

class CatListDomainMapper : Mapper<List<CatEntity>, List<Cat>> {

    override fun mapFrom(from: List<CatEntity>): List<Cat> = from.map {
        Cat(
            it.id,
            it.url,
            it.width,
            it.height,
            emptyList(), // Avoiding mapping Breeds as we are not using it
            emptyList(), // Avoiding mapping Categories as we are not using it
        )
    }

}
