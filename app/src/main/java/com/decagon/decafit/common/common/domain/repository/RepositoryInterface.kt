package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.*
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput

interface RepositoryInterface {
    suspend fun register(register: RegisterInput): ApolloResponse<RegisterMutation.Data>
    suspend fun login(register: LoginInput): ApolloResponse<LoginMutation.Data>
    suspend fun getWorkoutWithId(id: String):ApolloResponse<WorkoutWitIdQuery.Data>
    suspend fun getWorkouts():ApolloResponse<WorkoutsQuery.Data>
    suspend fun saveExerciseToLocalDB(exercises: List<Exercises>)
    suspend fun getExerciseFromLocalDB():List<Exercises>
    suspend fun updateExercise(id: String?, completed: Boolean, paused: Boolean,pausedTime:String )

}