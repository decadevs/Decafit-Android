package com.decagon.decafit.common.authentication.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.isNetworkAvailable
import com.decagon.decafit.type.LoginInput
import com.decagon.decafit.type.RegisterInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModels @Inject constructor(
    private val repository: RepositoryInterface
) :ViewModel() {
    private var _registerResponse = MutableLiveData<ApolloResponse<RegisterMutation.Data>>()
    val registerResponse: LiveData<ApolloResponse<RegisterMutation.Data>> get() = _registerResponse

    private var _loginResponse = MutableLiveData<ApolloResponse<LoginMutation.Data>>()
    val loginResponse: LiveData<ApolloResponse<LoginMutation.Data>> get() = _loginResponse

    private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse


    fun registerUser(registerInput: RegisterInput, context :Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                val response = try {
                    repository.register(registerInput)
                } catch (e: ApolloException) {
                    return@launch
                }

                _registerResponse.value = response
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }

    fun loginUser(loginInput: LoginInput, context :Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                val response = try {
                    repository.login(loginInput)
                } catch (e: ApolloException) {
                    return@launch
                }
                _loginResponse.value = response
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }
}