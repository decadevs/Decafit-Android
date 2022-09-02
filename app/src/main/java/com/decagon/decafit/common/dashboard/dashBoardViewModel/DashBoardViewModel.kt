package com.decagon.decafit.common.dashboard.dashBoardViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.decafit.common.common.data.database.mapper.WorkoutNetworkMapper
import com.decagon.decafit.common.common.data.database.model.WorkOutData
import com.decagon.decafit.common.common.data.database.repository.RoomRepositoryImpl
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import com.decagon.decafit.common.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val networkMapper: WorkoutNetworkMapper, private val repository: RepositoryInterface, private val roomRepository: RoomRepositoryImpl): ViewModel() {

    private var _dashBoardResponse = MutableLiveData<UiState>()
    val dashBoardResponse: LiveData<UiState> get() = _dashBoardResponse


    private var _networkCheckResponse = MutableLiveData<String>()
    val networkCheckResponse: LiveData<String> get() = _networkCheckResponse

    fun getAllRepositoryList(): LiveData<List<WorkOutData>> {
        return roomRepository.getWorkouts()
    }

    fun getWorkOuts(context: Context) {
        if (isNetworkAvailable(context)) {
            viewModelScope.launch {
                val response = repository.workOuts()
                _dashBoardResponse.value = response.data?.let {
                    for (i in it.workouts) {
                        roomRepository.deleteWorkouts()
                    }

                    UiState.Success(
                        it.workouts.mapNotNull { workout -> networkMapper.mapTo(workout!!) },
                    ).also {
                        for (i in it.data) {
                            roomRepository.insertWorkouts(i)
                        }
                    }
                } ?: UiState.Failure("Error in loading data")

            }
        }
    }
}
//
//fun getWorkOuts(context : Context) {
//    if (isNetworkAvailable(context)) {
//        viewModelScope.launch {
//            val response = try {
//                repository.workOuts()
//            } catch (e: ApolloException) {
//                return@launch
//            }
//
//            _dashBoardResponse.value = response
//            >>>>>>> 649103637a3905981f802b6c5aedab346227acd2
//        }
//    }else{
//        _networkCheckResponse.value = "N0 INTERNET"
//    }
//}