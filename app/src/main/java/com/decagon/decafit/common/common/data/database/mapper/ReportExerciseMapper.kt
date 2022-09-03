package com.decagon.decafit.common.common.data.database.mapper

import com.apollographql.apollo3.api.Optional
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.type.ReportExcerciseProgressInput
import javax.inject.Inject

class ReportExerciseMapper @Inject constructor():Mapper<WorkoutsQuery.Exercise, ReportExercise>{
    override fun mapTo(type: ReportExercise): WorkoutsQuery.Exercise {
        return WorkoutsQuery.Exercise(
            type.excerciseId,
            type.title!!,
            type.description!!,
            type.image!!,
            type.type!!
        )
    }
}