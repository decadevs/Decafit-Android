package com.decagon.decafit.common.common.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.decagon.decafit.LoginMutation
import com.google.gson.Gson

object Preference {
    val HEADER_KEY = "Header_KEY"
    val MY_PREF = "my_pref"
    val USER_NAME = "name"
    val prefLoginData = "prefLoginData"
    val prefLoggedIn = "prefLoggedIn"
    val defaultStringValue = "{}"

    private val gson = Gson()


    lateinit var preferences: SharedPreferences

    fun initSharedPreference(activity: Activity){
        preferences = activity.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
    }


    fun saveHeader(token: String){
        preferences.edit().putString(HEADER_KEY, token).commit()
    }


    fun setBooleanPreference(key: String, value: Boolean) {
        preferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getBooleanPreference(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun loggedIn(): Boolean {
        return getBooleanPreference(prefLoggedIn)
    }

    fun setLoggedIn(value: Boolean) {
        return setBooleanPreference(prefLoggedIn, value)
    }

    fun getLoggedIn(): Boolean {
        return loggedIn()
    }

    fun logOut(): Boolean {
        preferences.edit()
            .remove(prefLoginData)
            .remove(prefLoggedIn)
            .apply()
        return true
    }

    fun setLoginData(entity: LoginMutation.Data) {
        return setStringPreference(prefLoginData, gson.toJson(entity))
    }

    fun getLoginData(): LoginMutation.Data {
        return gson.fromJson(getStringPreference(prefLoginData), LoginMutation.Data::class.java).apply {
            val data = this
            data.userLogin
        }
    }

    fun getStringPreference(
        key: String, defaultValue: String = defaultStringValue
    ): String {
        return preferences.getString(key, defaultValue) ?: defaultStringValue
    }

    fun setStringPreference(key: String, value: String) {
        preferences.edit()
            .putString(key, value)
            .apply()
    }

    fun getLoginDetails(key: String): String?{
        return preferences.getString(key, null)
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