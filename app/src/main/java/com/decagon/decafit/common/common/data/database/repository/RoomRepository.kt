package com.decagon.decafit.common.common.data.database.repository

import androidx.lifecycle.LiveData
import com.decagon.decafit.common.common.data.database.model.WorkOutData


//@Module
//@InstallIn(SingletonComponent::class)
interface RoomRepository {

//    @Binds
    fun getWorkouts(): LiveData<List<WorkOutData>>

//    @Binds
    fun insertWorkouts(workout: WorkOutData)

//    @Binds
    fun deleteWorkouts()
}