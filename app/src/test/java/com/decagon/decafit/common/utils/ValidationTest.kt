package com.decagon.decafit.common.utils

import org.junit.Assert
import org.junit.Test

class ValidationTest {
    val email = "obettabenjamin44@gmil.com"

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
        val actual = Validation.validatePasswordPattern("Benjamin@123")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun validate_empty_password_returns_false() {
        val expected = false
        val actual = Validation.validatePasswordPattern("")
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
}