package com.decagon.decafit.common.common.data.database.mapper

import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.WorkOutData
import javax.inject.Inject


class WorkoutNetworkMapper @Inject constructor(): Mapper<WorkOutData, WorkoutsQuery.Workout> {
    override fun mapTo(type: WorkoutsQuery.Workout): WorkOutData {
        return WorkOutData(
            type.id,
            type.title,
            type.backgroundImage,
            type.sets,
            type.reps,
            type.createdAt,
            type.exercises
        )
    }
}