package com.decagon.decafit.common.utils

import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.workout.data.WorkoutItems

interface OnclickListener {
    fun onclickWorkoutItem(workoutItems: Exercises)
}