package com.decagon.decafit.common.dashboard.dashBoardViewModel

import com.decagon.decafit.common.common.data.database.model.WorkOutData

sealed class UiState {
    data class Success(
        val data: List<WorkOutData>
    ): UiState()
    data class Failure(
        val message: String
    ): UiState()
}