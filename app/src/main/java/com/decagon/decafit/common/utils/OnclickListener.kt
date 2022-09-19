package com.decagon.decafit.common.utils

import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise

interface OnclickListener {
    fun onclickWorkoutItem(workoutItems: WorkoutsQuery.Exercise)
    fun onclickReportExerciseItem(workoutItems: ReportExercise)
}