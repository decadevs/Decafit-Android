package com.decagon.decafit.common.common.data.database.mapper

import com.decagon.decafit.common.common.data.database.model.ReportExercise


interface Mapper<C, D> {
    fun mapTo(type: D): C
}