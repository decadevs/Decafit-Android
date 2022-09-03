package com.decagon.decafit.common.common.data.database.model

import com.apollographql.apollo3.api.EnumType
import kotlinx.android.parcel.Parcelize


data class Exercise(
    val id: Int?,
    val title: String?,
    val description: String?,
    val image: String?,
    val type: EnumType?
)
