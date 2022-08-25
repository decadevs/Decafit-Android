package com.decagon.decafit.common.common.data.database.mapper


interface Mapper<C, D> {
    fun mapTo(type: D): C
}