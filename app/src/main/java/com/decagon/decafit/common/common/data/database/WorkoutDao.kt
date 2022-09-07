package com.decagon.decafit.common.common.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import com.decagon.decafit.common.common.data.database.model.WorkOutData

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): LiveData<List<WorkOutData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkouts(obj: WorkOutData)


    @Query("DELETE FROM workouts")
    fun deleteWorkouts()

    @Query("DELETE FROM reportExercise")
    fun deleteReportWorkout()


    @Query("SELECT * FROM workouts WHERE id=:id")
    fun getWorkoutById(id:String):LiveData<WorkOutData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReportWorkout(report:ReportWorkoutData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReportExercise(report:ReportExercise)

    @Query("SELECT *FROM reportWorkouts")
    fun getAllReportWorkouts():LiveData<List<ReportWorkoutData>>

    @Query("SELECT * FROM reportWorkouts WHERE workoutId=:id")
    fun getReportWorkoutById(id:String):LiveData<ReportWorkoutData>

    @Query("SELECT * FROM reportExercise WHERE workoutId=:workoutId")
    fun getReportReportExercise(workoutId:String):LiveData<List<ReportExercise>>
}