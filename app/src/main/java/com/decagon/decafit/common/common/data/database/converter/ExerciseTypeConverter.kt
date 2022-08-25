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


//    @TypeConverter
//    fun fromExercise(value: WorkoutsQuery.Exercise?): String {
//        return JSONObject().apply {
//            put("id", value!!.id)
//            put("name", value.title)
//            put("name", value.image)
//            put("name", value.description)
//            put("name", value.type)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toExercise(value: String): WorkoutsQuery.Exercise? {
//        val gson : Gson = Gson()
//        val listType: Type = object : TypeToken<WorkoutsQuery.Exercise?>() {}.type
//        return gson.fromJson<WorkoutsQuery.Exercise??>(value, listType)
//    }

//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<WorkoutsQuery.Exercise?>::class.java).toList()

//    @TypeConverter
//    fun fromExercise(value: List<WorkoutsQuery.Exercise?>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<WorkoutsQuery.Exercise?>::class.java).toList()
}