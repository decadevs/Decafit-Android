package com.decagon.decafit.common.utils

import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.workout.data.WorkoutItems

interface OnclickListener {
    fun onclickWorkoutItem(workoutItems: WorkoutsQuery.Exercise)
    fun onclickReportExerciseItem(workoutItems: ReportExercise)
}