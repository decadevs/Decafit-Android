package com.decagon.decafit.common.common.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.common.utils.Resource
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun register(register: RegisterInput): ApolloResponse<RegisterMutation.Data>
//    suspend fun login(loginUser:LoginInput): ApolloResponse<LoginMutation.Data>
}