package com.decagon.decafit.common.authentication.data

data class SignUpRequest(
    val email : String,
    val fullName :String,
    val phone_number : String,
    val password : String,
)
