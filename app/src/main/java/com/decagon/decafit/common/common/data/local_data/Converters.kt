package com.decagon.decafit.common.common.data.local_data

import androidx.room.TypeConverter
import com.decagon.decafit.common.common.data.models.Exercises
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class Converters {
//    @TypeConverter
//    fun fromSource(source: Exercises): String {
//        return JSONObject().apply {
//            put("id", source.id)
//            put("title", source.title)
//           // put("completed", source.completed)
//            put("description", source.description)
//            put("paused", source.paused)
//            put("pausedTime", source.pausedTime)
//        }.toString()
//    }
//
//
//    @TypeConverter
//    fun toSource(source: String): Exercises {
//        val json = JSONObject(source)
//
//        val exercises = Exercises(
//            json.get("id").toString(),
//            json.getString("title"),
//            json.getBoolean("completed"),
//            json.getString("description"),
//            json.getBoolean("paused"),
//            json.getInt("pausedTime")
//        )

//        if(json.has("img")) user.img = json.getString("img")
//        if(json.has("bio")) user.img = json.getString("bio")

     //   return exercises
  //  }
}