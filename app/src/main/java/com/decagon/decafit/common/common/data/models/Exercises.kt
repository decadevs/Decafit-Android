package com.decagon.decafit.common.common.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.type.EnumType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "exercise_table")
data class Exercises(

    @SerializedName("id")public val id: String,
    @SerializedName("title") public val title: String,
    @SerializedName("description")public val description: String,
    @SerializedName("image") public val image: String,
    @SerializedName("type") public val type: String?=null

    ):Serializable{
    @PrimaryKey(autoGenerate = false)
    var idd =id
    }

data class EnumType(
    val time:String,
    val count :String
)

