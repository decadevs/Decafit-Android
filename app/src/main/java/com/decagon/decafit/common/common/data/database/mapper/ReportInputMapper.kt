package com.decagon.decafit.common.common.data.database.mapper

import com.apollographql.apollo3.api.Optional
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.type.ReportExcerciseProgressInput
import javax.inject.Inject

 class ReportInputMapper @Inject constructor():Mapper<ReportExcerciseProgressInput, ReportExercise>{
    override fun mapTo(type:ReportExercise): ReportExcerciseProgressInput {
        return ReportExcerciseProgressInput(
            Optional.Present(type.excerciseId),
            Optional.Present(type.type),
            Optional.Present(type.paused),
            Optional.Present(type.limit),
            Optional.Present(type.completed),
            Optional.Present(type.progress)
        )
    }
}
