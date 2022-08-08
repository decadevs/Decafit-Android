package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.RegisterUserMutation
import com.decagon.decafit.common.utils.Resource
import com.decagon.decafit.type.UsersPermissionsRegisterInput
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun register(register: UsersPermissionsRegisterInput): ApolloResponse<RegisterUserMutation.Data>
}