package com.decagon.decafit.common.utils

import com.decagon.decafit.common.authentication.data.SignUpRequest
import org.junit.Assert
import org.junit.Test

class ValidationTest {
    val email = "obettabenjamin44@gmil.com"
    val phone_numberLeasThan9 = "070123"
    val passwordLessThan8character = "Pas1#"

    @Test
    fun validatePasswordPattern() {
        val expected = false
        val actual = Validation.validatePasswordPattern("Abc1")
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun validate_wrong_password_returns_false() {
        val expected = false
        val actual = Validation.validatePasswordPattern("benjamin")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_right_password_returns_true() {
        val expected = true
        val actual = Validation.isValidPasswordFormat("Benjamin22")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_empty_password_returns_false() {
        val expected = false
        val actual = Validation.isValidPasswordFormat("")
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun validate_wrong_email_returns_false() {
        val expected = false
        val actual = Validation.validateEmailInput("abc")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_right_email_returns_true() {
        val expected = true
        val actual = Validation.validateEmailInput(email)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_empty_email_returns_false() {
        val expected = false
        val actual = Validation.validateEmailInput("")
        Assert.assertEquals(expected, actual)
    }

    val fullName = "Victor"
    val emailAddress= "victor@gmail.com"
    val phone_number = "+2347056"
    val password = "Manchester1"
    @Test
    fun  test_validateUserInput_wrongInput_returns_true(){
        val expected = true
        val actual = Validation.validateUserProfileInput(SignUpRequest(fullName, password, emailAddress, phone_number))
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_fullName_with_spacial_character() {
        val expected = listOf("must not contain special characters")
        val actual = Validation.validateFullNameInput("vic@@##")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_fullName_starts_with_digiit_returns_cannot_start_with_digit() {
        val expected = listOf("Can't start with numbers")
        val actual = Validation.validateFullNameInput("123gghh")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_phone_numberLessThan_9_digits_returns_true() {
        val actual = Validation.validatePhoneNumber(phone_numberLeasThan9)
        Assert.assertTrue(actual)
    }

    @Test
    fun validate_Password_input_lessThan_8_returns_Minimum_of_8_characters() {

        val expected = listOf("* Minimum of 8 characters")
        val actual = Validation.validatePasswordErrors(passwordLessThan8character)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_sign_up_process() {
        val expected = listOf("* Minimum of 8 characters")
        val actual = Validation.validatePasswordErrors(passwordLessThan8character)
        Assert.assertEquals(expected, actual)
    }
}