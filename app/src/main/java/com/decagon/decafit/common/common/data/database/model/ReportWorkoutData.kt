package com.decagon.decafit.common.common.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagon.decafit.GetReportWorkoutQuery

@Entity(tableName = "reportWorkouts")
data class ReportWorkoutData(
     @PrimaryKey val workoutId: String,
     val workoutReps: Int,
     val workoutSet: Int,
     val workoutTime: String,
     val workoutCount: Int,
     val exercises: List<GetReportWorkoutQuery.Exercise>,
    )
