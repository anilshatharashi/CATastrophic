package com.zaloracasestudy.catastrophic.domain.mapper

interface Mapper<INPUT, OUTPUT> {
    fun mapFrom(from: INPUT): OUTPUT
}
