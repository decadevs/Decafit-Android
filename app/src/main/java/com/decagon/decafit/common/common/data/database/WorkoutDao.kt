package com.decagon.decafit.common.common.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.decafit.common.common.data.database.model.WorkOutData

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): LiveData<List<WorkOutData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkouts(obj: WorkOutData)


    @Query("DELETE FROM workouts")
    fun deleteWorkouts()

}