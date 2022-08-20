package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.local_data.DecafitDAO
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apolloClient: ApolloClient,
    private val decafitDAO: DecafitDAO
) : RepositoryInterface{
    override suspend fun register(register: RegisterInput): ApolloResponse<RegisterMutation.Data> {
        return apolloClient.mutation(RegisterMutation(user = register)).execute()
    }

    override suspend fun login(login: LoginInput): ApolloResponse<LoginMutation.Data> {
        return apolloClient.mutation(LoginMutation(user = login)).execute()
    }

    override suspend fun getWorkoutWithId(id: String): ApolloResponse<WorkoutWitIdQuery.Data> {
        return apolloClient.query(WorkoutWitIdQuery(workoutId = id)).execute()
    }

    override suspend fun getWorkouts(): ApolloResponse<WorkoutsQuery.Data> {
        return apolloClient.query(WorkoutsQuery()).execute()
    }

    override suspend fun saveExerciseToLocalDB(exercises: List<Exercises>) {
        decafitDAO.saveExercises(exercises)
    }

    override suspend fun getExerciseFromLocalDB(): List<Exercises> {
        return decafitDAO.getAllExercise()
    }

    override suspend fun updateExercise(
        id: String?,
        completed: Boolean,
        paused: Boolean,
        pausedTime: String
    ) {
       // decafitDAO.updateExercise(id,completed,paused,pausedTime)
    }
}