package com.decagon.decafit.common.common.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import com.decagon.decafit.LoginMutation
import com.google.gson.Gson

object Preference {
    val HEADER_KEY = "Header_KEY"
    val MY_PREF = "my_pref"
     const val STEP_KEY = "step_Key"
    val USER_NAME = "name"
    val WORKOUT_KEY = "workoutId_key"
    val SET_KEY = "set_key"
    val REP_KEY = "rep_key"
    val TIME_KEY = "time_key"
    val COUNT_KEY = "count_key"
    val USERID_KEY = "userid_key"
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
    fun saveWorkoutId(id: String){
        preferences.edit().putString(WORKOUT_KEY, id).commit()
    }

    fun savePreviousStepCount(step: Int){
        preferences.edit().putInt(STEP_KEY, step).commit()
    }

    fun saveWorkoutSet(set: String?){
        preferences.edit().putString(SET_KEY, set).commit()
    }
    fun saveWorkoutRep(reps: String?){
        preferences.edit().putString(REP_KEY, reps).commit()
    }
    fun saveEstimatedTime(time: String?){
        preferences.edit().putString(TIME_KEY, time).commit()
    }
    fun saveNumberOfCount(count: String?){
        preferences.edit().putString(COUNT_KEY, count).commit()
    }
    fun saveUserId(userId: String?){
        preferences.edit().putString(USERID_KEY, userId).commit()
    }
    fun getUserId(key: String): String?{
        return preferences.getString(key, null)
    }
    fun getWorkoutSet(key: String): String?{
        return preferences.getString(key, null)
    }
    fun getWorkoutRep(key: String): String?{
        return preferences.getString(key, null)
    }
    fun getEstimatedTime(key: String): String?{
        return preferences.getString(key, null)
    }

    fun getNumberOfCount(key: String): String?{
        return preferences.getString(key, null)
    }
    fun getPreviousStepCount(key: String): Int{
        return preferences.getInt(key, 0)
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

    fun getWorkoutId(key: String): String?{
        return preferences.getString(key, null)
    }
    fun clearInfo(key: String){
        preferences.edit().remove(key).apply()
    }
}