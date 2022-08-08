package com.decagon.decafit.common.authentication.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.decagon.decafit.RegisterUserMutation
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.Resource
import com.decagon.decafit.common.utils.isNetworkAvailable
import com.decagon.decafit.type.UsersPermissionsRegisterInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModels @Inject constructor(
    private val repository: RepositoryInterface
) :ViewModel() {
    private var _registerResponse = MutableLiveData<ApolloResponse<RegisterUserMutation.Data>>()
    val registerResponse: LiveData<ApolloResponse<RegisterUserMutation.Data>> get() = _registerResponse
     private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse


    fun registerUser(registerInput: UsersPermissionsRegisterInput, context :Context) {
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
}