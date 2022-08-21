package com.decagon.decafit.workout.presentation.viewmodels

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
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.local_data.LocalDataBase
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.dommyData.workoutData
import com.decagon.decafit.common.utils.isNetworkAvailable
import com.decagon.decafit.type.RegisterInput
import com.decagon.decafit.workout.data.WorkoutItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModels @Inject constructor(
    private val repository:RepositoryInterface
    ):ViewModel(){

    var _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar
    private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse

    private var _workoutResponse = MutableLiveData<ApolloResponse<WorkoutsQuery.Data>>()
    val workoutResponse: LiveData<ApolloResponse<WorkoutsQuery.Data>> get() = _workoutResponse

    private var _workoutWithIdResponse = MutableLiveData<ApolloResponse<WorkoutWitIdQuery.Data>>()
    val workoutWithIdResponse: LiveData<ApolloResponse<WorkoutWitIdQuery.Data>> get() = _workoutWithIdResponse

    private var _exerciseResponse = MutableLiveData<List<Exercises>>()
    val exerciseResponse: LiveData<List<Exercises>> get() = _exerciseResponse

    fun getWorkoutWithId(id:String, context : Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                _progressBar.value = true
                val response = try {
                    repository.getWorkoutWithId(id)
                } catch (e: ApolloException) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    _progressBar.value = false
                    Log.d("SIGNUP", "network error${e.message}")
                    return@launch
                }
                _progressBar.value = false
                if (response.data != null) {
                    _workoutWithIdResponse.value = response
                    _progressBar.value = false
//                    val exercises = response.data?.workout?.exercises
//                    repository.saveExerciseToLocalDB(exercises)
                    //_exerciseResponse.value = response
                }
                if (response.hasErrors()){
                    Toast.makeText(context, response.errors?.get(0)!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }
}