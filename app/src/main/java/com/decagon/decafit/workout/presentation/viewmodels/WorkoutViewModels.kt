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
import com.decagon.decafit.*
import com.decagon.decafit.common.common.data.database.mapper.ReportInputMapper
import com.decagon.decafit.common.common.data.database.mapper.ReportWorkoutMapper
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import com.decagon.decafit.common.common.data.database.model.WorkOutData
import com.decagon.decafit.common.common.data.database.repository.RoomRepositoryImpl
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.USERID_KEY
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.isNetworkAvailable
import com.decagon.decafit.type.ReportCreateInput
import com.decagon.decafit.type.ReportExcerciseProgressInput
import com.decagon.decafit.type.ReportWorkoutInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModels @Inject constructor(
    private val repository:RepositoryInterface,
    private val localDBRepository:RoomRepositoryImpl,
    private val networkMapper: ReportWorkoutMapper,
    private val reportMapper:ReportInputMapper
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

    private var _reportResponse = MutableLiveData<ApolloResponse<GetReportWorkoutQuery.Data>>()
    val reportResponse: LiveData<ApolloResponse<GetReportWorkoutQuery.Data>> get() = _reportResponse

    fun getWorkoutFromLocalDb(workoutId:String):LiveData<WorkOutData>{
        return localDBRepository.getWorkoutById(workoutId)
    }
    fun getReportWorkoutFromDb(workoutId:String):LiveData<ReportWorkoutData>{
        return localDBRepository.getReportWorkoutById(workoutId)
    }

    fun saveReportToLocalDB(report:ReportWorkoutData){
        localDBRepository.insertReportWorkout(report)
    }

    fun saveExerciseToLocalDB(report:ReportExercise){
        localDBRepository.insertReportExercise(report)
    }

    fun getReportExercise(workoutId: String):LiveData<List<ReportExercise>>{
        return localDBRepository.getReportExercise(workoutId)
    }


    fun getReportWorkout(userId:String, workoutId:String,context : Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                _progressBar.value = true
                val response = try {
                    repository.getReport(userId,workoutId)
                } catch (e: ApolloException) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    _progressBar.value = false
                    Log.d("SIGNUP", "network error${e.message}")
                    return@launch
                }
                _progressBar.value = false
                if (response.data?.reportWorkout != null) {
                  val report = networkMapper.mapTo(response.data?.reportWorkout?.workouts!!)
                    localDBRepository.insertReportWorkout(report)
                    Log.d("CREATEREPORT", " report from backend ====${response.data?.reportWorkout}")

                }
                if (response.hasErrors()){
                    Toast.makeText(context, response.errors?.get(0)!!.message, Toast.LENGTH_SHORT).show()
                    Log.d("CREATEREPORT", " error getting report from backend ====${response.errors?.get(0)!!.message}")

                }
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }
    }

    fun reportProgressExerciseInput():List<ReportExcerciseProgressInput>{
        val workoutId = Preference.getWorkoutId(Preference.WORKOUT_KEY)
        val exercise = localDBRepository.getReportExercise(workoutId!!).value!!
        var  input : ReportExcerciseProgressInput? =null
        if (exercise.isNotEmpty()) {
            for (i in exercise) {
                input = reportMapper.mapTo(i)
            }
        }
        return listOf(input!!)
    }

    fun createReport(input :ReportWorkoutInput, context : Context) {
        val userId = Preference.getUserId(USERID_KEY)

        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                _progressBar.value = true
                val response = try {
                    repository.createReport(ReportCreateInput(userId!!,input))
                } catch (e: ApolloException) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    _progressBar.value = false
                    Log.d("SIGNUP", "network error${e.message}")
                    return@launch
                }

                Log.d("CREATEREPORT", " report created ====${response.data}")

                _progressBar.value = false
                if (response.hasErrors()){
                    Toast.makeText(context, response.errors?.get(0)!!.message, Toast.LENGTH_SHORT).show()
                    Log.d("CREATEREPORT", " report ERROR ====${response.errors?.get(0)!!.message}")

                }
            }
        }else{
            _networkCheckResponse.value = "N0 INTERNET"
        }

    }
}