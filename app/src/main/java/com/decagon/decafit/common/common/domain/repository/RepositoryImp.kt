package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import com.decagon.decafit.type.UserLogin
import com.decagon.decafit.type.WorkOut
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apolloClient: ApolloClient,
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
}