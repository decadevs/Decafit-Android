package com.decagon.decafit.common.common.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object Preference {
    val HEADER_KEY = "Header_KEY"
    val MY_PREF = "my_pref"
    val USER_NAME = "name"


    lateinit var preferences: SharedPreferences

    fun initSharedPreference(activity: Activity){
        preferences = activity.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
    }


    fun saveHeader(token: String){
        preferences.edit().putString(HEADER_KEY, token).commit()
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

    fun clearInfo(key: String){
        preferences.edit().remove(key).apply()
    }
}