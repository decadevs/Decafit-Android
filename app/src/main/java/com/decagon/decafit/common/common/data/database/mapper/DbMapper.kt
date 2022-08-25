package com.decagon.decafit.common.common.data.database.mapper

interface DbMapper <C, E> {

    fun mapFromCached(type: C): E

    fun mapToCached(type: E): C

}