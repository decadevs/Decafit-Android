package com.decagon.decafit.common.common.data.database.converter

import androidx.room.TypeConverter
import com.decagon.decafit.GetReportWorkoutQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.type.EnumType
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

    @TypeConverter
    fun fromReportExerciseList(reportList:List<GetReportWorkoutQuery.Exercise?>?):String?{
        val type = object :TypeToken<List<GetReportWorkoutQuery.Exercise>>(){}.type
        return Gson().toJson(reportList,type)
    }

    @TypeConverter
    fun toReportExercise(reportList:String?):List<GetReportWorkoutQuery.Exercise?>?{
        val type = object :TypeToken<List<GetReportWorkoutQuery.Exercise>>(){}.type
        return Gson().fromJson<List<GetReportWorkoutQuery.Exercise>>(reportList,type)
    }

    @TypeConverter
    fun fromReportExercise(reportList:EnumType?):String?{
        val type = object :TypeToken<EnumType>(){}.type
        return Gson().toJson(reportList,type)
    }

    @TypeConverter
    fun toExercise(reportList:String?):EnumType?{
        val type = object :TypeToken<EnumType>(){}.type
        return Gson().fromJson<EnumType>(reportList,type)
    }
}