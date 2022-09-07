package com.decagon.decafit.common.common.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagon.decafit.common.common.data.database.converter.ExerciseTypeConverter
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import com.decagon.decafit.common.common.data.database.model.WorkOutData

@Database(entities = [WorkOutData::class, ReportWorkoutData::class, ReportExercise::class], version = 1)
@TypeConverters(ExerciseTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWorkOutDao(): WorkoutDao

    companion object {
        private const val DB_NAME = "WorkoutDB"
        private val lock = Any()
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
