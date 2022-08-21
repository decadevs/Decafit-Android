package com.decagon.decafit.common.dashboard.dashBoardViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor (private val repository: RepositoryInterface): ViewModel() {

    private var _dashBoardResponse = MutableLiveData<ApolloResponse<WorkoutsQuery.Data>>()
    val dashBoardResponse: LiveData<ApolloResponse<WorkoutsQuery.Data>> get() = _dashBoardResponse

    private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse

    fun getWorkOuts(context : Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                val response = try {
                    repository.workOuts()
                } catch (e: ApolloException) {
                    return@launch
                }

                _dashBoardResponse.value = response
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }
}