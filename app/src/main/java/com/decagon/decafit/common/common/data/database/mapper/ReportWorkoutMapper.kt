package com.decagon.decafit.common.common.data.database.mapper

import com.decagon.decafit.GetReportWorkoutQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import javax.inject.Inject

class ReportWorkoutMapper @Inject constructor(): Mapper<ReportWorkoutData, GetReportWorkoutQuery.Workouts> {
    override fun mapTo(type: GetReportWorkoutQuery.Workouts): ReportWorkoutData {
       return ReportWorkoutData(
           type.workoutId,
           type.workoutReps,
           type.workoutSet,
           type.workoutTime,
           type.workoutCount,
           type.exercises
       )
    }
}
