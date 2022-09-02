package com.decagon.decafit.common.common.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.type.Excercise


@Entity(tableName = "workouts")
data class WorkOutData(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val backgroundImage: String,
    @ColumnInfo val sets: Int?,
    @ColumnInfo val reps: Int?,
    @ColumnInfo val createdAt: String?,
    @ColumnInfo val exercise: List<WorkoutsQuery.Exercise?>?
)