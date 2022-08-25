package com.decagon.decafit.common.common.data.database.converter

import androidx.room.TypeConverter
import com.decagon.decafit.WorkoutsQuery
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseTypeConverter {

    @TypeConverter
    fun fromExerciseList(countryLang: List<WorkoutsQuery.Exercise?>?): String? {
        val type = object : TypeToken<List<WorkoutsQuery.Exercise>>() {}.type
        return Gson().toJson(countryLang, type)
    }
    @TypeConverter
    fun toExerciseList(countryLangString: String?): List<WorkoutsQuery.Exercise?>? {
        val type = object : TypeToken<List<WorkoutsQuery.Exercise>>() {}.type
        return Gson().fromJson<List<WorkoutsQuery.Exercise>>(countryLangString, type)
    }
}