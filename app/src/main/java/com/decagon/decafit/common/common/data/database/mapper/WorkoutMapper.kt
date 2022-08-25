package com.decagon.decafit.common.common.data.database.mapper

import com.decagon.decafit.common.common.data.database.model.WorkOutData
import com.decagon.decafit.common.common.data.database.model.WorkoutDbModel

//class WorkoutMapperDbMapper: DbMapper<WorkoutDbModel, WorkOutData> {
//    override fun mapFromCached(type: WorkoutDbModel): WorkOutData {
//        return WorkOutData(
//            id = type.id,
//            backgroundImage = type.backgroundImage,
//            sets = type.sets,
//            reps = type.reps,
//            createdAt = type.createdAt,
//            title = type.title,
//            exercise = type.exercises
//        )
//    }
//
//
//
//    override fun mapToCached(type: WorkOutData): WorkoutDbModel {
//        return WorkoutDbModel(
//            id= type.id,
//            backgroundImage = type.backgroundImage,
//            sets = type.sets,
//            reps = type.reps,
//            createdAt = type.createdAt,
//            title = type.title,
//            exercises = listOf()
//        )
//    }
//}