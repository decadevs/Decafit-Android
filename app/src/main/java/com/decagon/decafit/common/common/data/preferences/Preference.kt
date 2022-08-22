package com.decagon.decafit.common.common.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object Preference {
    val HEADER_KEY = "Header_KEY"
    val MY_PREF = "my_pref"
     const val STEP_KEY = "step_Key"
    val USER_NAME = "name"
    val WORKOUT_KEY = "workoutId_key"


    lateinit var preferences: SharedPreferences


    fun initSharedPreference(activity: Activity){
        preferences = activity.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
    }


    fun saveHeader(token: String){
        preferences.edit().putString(HEADER_KEY, token).commit()
    }
    fun saveWorkoutId(id: String){
        preferences.edit().putString(WORKOUT_KEY, id).commit()
    }

    fun savePreviousStepCount(step: Int){
        preferences.edit().putInt(STEP_KEY, step).commit()
    }
    fun getPreviousStepCount(key: String): Int{
        return preferences.getInt(key, 0)
    }


    fun saveName(name: String){
        preferences.edit().putString(USER_NAME, name).commit()
    }

    fun getName(key: String):String? {
        return preferences.getString(key, null)
    }

    fun getHeader(key: String): String?{
        return preferences.getString(key, null)
    }

    fun getWorkoutId(key: String): String?{
        return preferences.getString(key, null)
    }
    fun clearInfo(key: String){
        preferences.edit().remove(key).apply()
    }
}