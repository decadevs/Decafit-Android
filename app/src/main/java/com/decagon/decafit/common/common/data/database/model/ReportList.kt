package com.decagon.decafit.common.common.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportList (
    val report:List<ReportExercise>
        ):Parcelable