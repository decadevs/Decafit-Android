package com.decagon.decafit.common.utils

import com.decagon.decafit.common.authentication.data.SignUpRequest
import java.util.regex.Pattern

object Validation {
    val DIGITCHARACTER = Regex("[0-9]")
    val SPECAILCHARAACTERS = Regex("[@#\$%^&+=*_-]")
    val UPPERCASE = Regex("[A-Z]")
    val LOWERCASE = Regex("[a-z]")

    fun validateEmailInput(email: String): Boolean {
        if (email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            return false
        }
        return true
    }
    var EMAIL_PATTERN = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )


    //Validate user password pattern
    fun validatePasswordPattern(usersPassword: String): Boolean {
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@%\\[}+'!/#$^?:;,(\")~`.*=&{>\\]<_])(?=\\S+$).{8,20}$".toRegex()
        val passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+\$).{4,}\$".toRegex()
        if (usersPassword.matches(passwordRegEx)) {
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

    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(password).matches()
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
        }
        if (!validateEmailInput(accountData.email) ) {
            result.add("cant be empty")
        }
        if ( validatePhoneNumber(accountData.phone_number)){
            result.add("Incomplete number")
        }
        if (accountData.password.length <= 7) {
            result.add("Minimum of 8 characters")
        }
        if (!accountData.password.contains(UPPERCASE) || !accountData.password.contains(LOWERCASE)
        ) {
            result.add("Uppercase and lowercase")
        }
        if (!accountData.password.contains(DIGITCHARACTER)) {
            result.add("Numbers")
        }
        return result
    }

    fun validateUserProfileInput(user: SignUpRequest): Boolean {
        return (user.fullName.isEmpty() || user.email.isEmpty() || validatePhoneNumber(user.phone_number) || !validateEmailInput(
            user.password
        ))
    }
    // passwordInputField validation
    fun validatePasswordErrors(
        password: String,
    ): List<String> {

        val result = mutableListOf<String>()

        if (password.length <= 7) {
            result.add("* Minimum of 8 characters")
        }
        if (!password.contains(UPPERCASE) || !password.contains(LOWERCASE)
        ) {
            result.add("* Uppercase and lowercase")
        }
        if (!password.contains(DIGITCHARACTER)) {
            result.add("* Numbers")
        }

        if (!password.contains(SPECAILCHARAACTERS)) {
            result.add("* Special characters")
        }

        return result
    }
}