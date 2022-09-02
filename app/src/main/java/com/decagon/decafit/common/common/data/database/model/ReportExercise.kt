package com.decagon.decafit.common.common.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagon.decafit.type.EnumType
@Entity(tableName = "reportExercise")
data class ReportExercise(
     val workoutId:String?,
     @PrimaryKey
     val excerciseId: String,
     val title: String?,
     val description: String?,
     val image: String?,
     val type: EnumType?,
     val paused: Boolean?,
     val limit: String?,
     val completed: Boolean?,
     val progress: Int?,
)
