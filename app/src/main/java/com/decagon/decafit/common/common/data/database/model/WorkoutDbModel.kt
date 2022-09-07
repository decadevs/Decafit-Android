package com.decagon.decafit.common.common.data.database.model

import com.decagon.decafit.type.Excercise

data class WorkoutDbModel(
    val id: String,
    val title: String,
    val backgroundImage: String,
    val sets: Int?,
    val reps: Int?,
    val exercises: List<Excercise>?,
    val createdAt: String?
)
