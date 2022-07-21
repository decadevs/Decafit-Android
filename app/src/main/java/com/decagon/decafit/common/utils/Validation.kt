package com.decagon.decafit.common.utils

import com.decagon.decafit.common.SignUpRequest

object Validation {
    val DIGITCHARACTER = Regex("[0-9]")
    val SPECAILCHARAACTERS = Regex("[@#\$%^&+=*_-]")

    fun validateEmailInput(email: String): Boolean {
        if (email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            return false
        }
        return true
    }
    var EMAIL_PATTERN = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    //Validate user email pattern
    fun validatePasswordPattern(usersPassword: String): Boolean {
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@%\\[}+'!/#$^?:;,(\")~`.*=&{>\\]<_])(?=\\S+$).{8,20}$".toRegex()
        if (usersPassword.matches(passwordPattern)) {
            return true
        }
        return false
    }

    //Validate full name input
    fun validateFullNameInput(name: String): List<String> {
        val result = mutableListOf<String>()
        if (name.contains(DIGITCHARACTER)) {
            result.add("Can't start with numbers")
        }

        if (name.contains(SPECAILCHARAACTERS)) {
            result.add("must not contain special characters")
        }
        return result
    }

    // phone_number inputField validation
    fun validatePhoneNumber(phone_number : String):Boolean{
        return phone_number.length < 10
    }

    fun validateAccountData(accountData: SignUpRequest):List<String>{
        val result = mutableListOf<String>()
        val splitName = accountData.fullName.split(" ")
        // check if the full name is entered and is a valid name
        if(splitName.size <2 || splitName.size >3){
            result.add("Enter your full name")
        }else if (!validateEmailInput(accountData.email) ) {
            result.add("cant be empty")
        } else if ( validatePhoneNumber(accountData.phone_number)){
            result.add("Incomplete number")
        }
        return result
    }
}