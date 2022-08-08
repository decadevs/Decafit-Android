package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.RegisterUserMutation
import com.decagon.decafit.common.common.data.networks.ApiCallsHandler
import com.decagon.decafit.common.utils.Resource
import com.decagon.decafit.type.UsersPermissionsRegisterInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apolloClient: ApolloClient,
) : RepositoryInterface{
    override suspend fun register(register: UsersPermissionsRegisterInput): ApolloResponse<RegisterUserMutation.Data> {
        return apolloClient.mutation(RegisterUserMutation(input = register)).execute()
    }
}