package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apolloClient: ApolloClient,
) : RepositoryInterface{
    override suspend fun register(register: RegisterInput): ApolloResponse<RegisterMutation.Data> {
        return apolloClient.mutation(RegisterMutation(user = register)).execute()
    }

//    override suspend fun login(loginUser:LoginInput): ApolloResponse<LoginMutation.Data> {
//
//        return apolloClient.mutation(LoginMutation(user = loginUser)).execute()
//    }
}