package com.decagon.decafit.common.common.data.models

import com.decagon.decafit.WorkoutWitIdQuery

data class Workout(
     val id: String,
     val title: String,
     val backgroundImage: String,
     val sets: Int?,
     val reps: Int?,
     val exercises: List<WorkoutWitIdQuery.Exercise?>?,
     val createdAt: String?,
)
