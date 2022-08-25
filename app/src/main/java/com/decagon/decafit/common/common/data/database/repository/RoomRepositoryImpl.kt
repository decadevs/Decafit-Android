package com.decagon.decafit.common.common.data.database.repository

import androidx.lifecycle.LiveData
import com.decagon.decafit.common.common.data.database.WorkoutDao
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
}