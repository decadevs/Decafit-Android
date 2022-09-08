package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.*
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import com.decagon.decafit.type.ReportCreateInput
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apolloClient: ApolloClient
) : RepositoryInterface{
    override suspend fun register(register: RegisterInput): ApolloResponse<RegisterMutation.Data> {
        return apolloClient.mutation(RegisterMutation(user = register)).execute()
    }

    override suspend fun login(userLogin: LoginInput): ApolloResponse<LoginMutation.Data> {
        return apolloClient.mutation(LoginMutation(user = userLogin)).execute()
    }

    override suspend fun workOuts(): ApolloResponse<WorkoutsQuery.Data> {
        return apolloClient.query(WorkoutsQuery()).execute()
    }

    override suspend fun createReport(input: ReportCreateInput): ApolloResponse<CreateReportMutation.Data> {
        return apolloClient.mutation(CreateReportMutation(input)).execute()
    }

    override suspend fun getReport(
        userId: String,
        workoutId: String
    ): ApolloResponse<GetReportWorkoutQuery.Data> {
        return apolloClient.query(GetReportWorkoutQuery(userId,workoutId)).execute()
    }

    override suspend fun getWorkoutWithId(id: String): ApolloResponse<WorkoutWitIdQuery.Data> {
        return apolloClient.query(WorkoutWitIdQuery(workoutId = id)).execute()
    }

    override suspend fun getWorkouts(): ApolloResponse<WorkoutsQuery.Data> {
        return apolloClient.query(WorkoutsQuery()).execute()
    }

//    override suspend fun saveExerciseToLocalDB(exercises: List<Exercises>) {
//        decafitDAO.saveExercises(exercises)
//    }
//
//    override suspend fun getExerciseFromLocalDB(): List<Exercises> {
//        return decafitDAO.getAllExercise()
//    }

    override suspend fun updateExercise(
        id: String?,
        completed: Boolean,
        paused: Boolean,
        pausedTime: String
    ) {
       // decafitDAO.updateExercise(id,completed,paused,pausedTime)
    }
}