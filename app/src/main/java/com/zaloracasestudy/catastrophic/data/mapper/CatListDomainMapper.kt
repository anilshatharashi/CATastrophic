package com.zaloracasestudy.catastrophic.data.mapper

import android.util.Log
import com.zaloracasestudy.catastrophic.data.model.CatDataModel
import com.zaloracasestudy.catastrophic.domain.mapper.Mapper
import com.zaloracasestudy.catastrophic.domain.model.Cat

class CatListDomainMapper : Mapper<List<CatDataModel>?, List<Cat>?> {

    override fun mapFrom(from: List<CatDataModel>?): List<Cat>? =  from?.map {
        Cat(
            it.id,
            it.url,
            it.width,
            it.height,
            emptyList(), // Avoiding mapping Breeds as we are not using it
            emptyList(), // Avoiding mapping Categories as we are not using it
        ).also {
            Log.i("***", "CatlistDomainMapper = $it")
        }
    }

}
