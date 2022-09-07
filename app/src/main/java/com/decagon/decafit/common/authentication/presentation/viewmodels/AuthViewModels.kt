package com.decagon.decafit.common.authentication.presentation.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.decagon.decafit.LoginMutation
import com.decagon.decafit.RegisterMutation
import com.decagon.decafit.common.common.data.preferences.Preference
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
    var _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar
    private var _registerResponse = MutableLiveData<ApolloResponse<RegisterMutation.Data>>()
    val registerResponse: LiveData<ApolloResponse<RegisterMutation.Data>> get() = _registerResponse

    private var _loginResponse = MutableLiveData<ApolloResponse<LoginMutation.Data>>()
    val loginResponse: LiveData<ApolloResponse<LoginMutation.Data>> get() = _loginResponse

    private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse


    fun registerUser(registerInput: RegisterInput, context :Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                _progressBar.value=true
                val response = try {
                    repository.register(registerInput)
                } catch (e: ApolloException) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    _progressBar.value = false
                    Log.d("SIGNUP","network error${e.message}")
                    return@launch
                }
                _progressBar.value = false
                _registerResponse.value = response
                //if (response.data != null)  _progressBar.value = false
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }

    fun loginUser(loginInput: LoginInput, context :Context) {
        if (isNetworkAvailable(context)) {
            _progressBar.value = true
            viewModelScope.launch {
                val response = try {
                    repository.login(loginInput)
                } catch (e: ApolloException) {
                    _progressBar.value = false
                    return@launch
                }
                _progressBar.value = false
                _loginResponse.value = response
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }

}