package com.decagon.decafit.common.common.data.local_data

import androidx.room.*
import com.decagon.decafit.common.common.data.models.Exercises

@Dao
interface DecafitDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExercises(exercises: List<Exercises>)

    @Query("SELECT * FROM exercise_table")
    suspend fun getAllExercise():List<Exercises>

    @Update
    suspend fun updateWorkout(workout: List<Exercises>)

//    @Query("UPDATE exercise_table SET 'completed'=:completed , 'paused'=:paused ,'pausedTime'=:pausedTime WHERE id = :id")
//    suspend fun updateExercise(id: String?, completed: Boolean, paused: Boolean,pausedTime:String )
}