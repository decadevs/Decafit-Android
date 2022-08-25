package com.decagon.decafit.common.common.data.local_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagon.decafit.common.common.data.models.Exercises

@Database(
    entities = [Exercises::class], version = 1, exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class LocalDataBase:RoomDatabase() {
    abstract fun decafitDAO():DecafitDAO

    companion object {
        @Volatile
        var INSTANCE: LocalDataBase? = null
        fun getDatabase(context: Context): LocalDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    LocalDataBase::class.java,
                    "quote_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}