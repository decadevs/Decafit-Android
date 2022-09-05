package com.decagon.decafit.common.common.data.database.repository

import androidx.lifecycle.LiveData
import com.decagon.decafit.common.common.data.database.WorkoutDao
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import com.decagon.decafit.common.common.data.database.model.WorkOutData
import javax.inject.Inject


class RoomRepositoryImpl @Inject constructor(private val workoutDao: WorkoutDao)  {
    fun getWorkouts(): LiveData<List<WorkOutData>> {
       return workoutDao.getAllWorkouts()
    }

    fun insertWorkouts(workout: WorkOutData) {
        workoutDao.insertWorkouts(workout)
    }

    fun deleteWorkouts() {
        workoutDao.deleteWorkouts()
    }

    fun getWorkoutById(workoutId:String):LiveData<WorkOutData>{
       return workoutDao.getWorkoutById(workoutId)
    }
    fun insertReportWorkout(report:ReportWorkoutData){
        workoutDao.insertReportWorkout(report)
    }
    fun insertReportExercise(report:ReportExercise){
        workoutDao.insertReportExercise(report)
    }

    fun getReportExercise(workoutId: String): LiveData<List<ReportExercise>> {
        return workoutDao.getReportReportExercise(workoutId)
    }

    fun getReportWorkoutById(id:String):LiveData<ReportWorkoutData>{
       return workoutDao.getReportWorkoutById(id)
    }
}