package com.decagon.decafit.common.authentication.presentation.screens

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.decagon.decafit.common.common.utils.launchFragmentInHiltContainer
import com.decagon.decafit.R
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpFragmentTest {


    private val fullName = "Benjamin obeta"
    private val email = "abdul@gmail.com"
    private val validPassword = "Password123#"
    private val invalidPassword = "Password123"
    private val phone_number = "+07039612093"

    @Test
    fun test_SignUpFragment_views_and_navigation() {
        val mockNaveController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SignUpFragment> {
            Navigation.setViewNavController( requireView(), mockNaveController)
        }
        onView(withId(R.id.fullNameTextInput)).perform(typeText(fullName), closeSoftKeyboard())
        onView(withId(R.id.phoneNumberTextInput)).check(matches(isDisplayed()))
        onView(withId(R.id.phoneNumberTextInput)).perform(typeText(phone_number), closeSoftKeyboard())
        onView(withId(R.id.email_address_textInputLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.emailTextInput)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.password_signup_textInputLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordTextInput)).perform(typeText(validPassword), closeSoftKeyboard())
        onView(withId(R.id.sign_up_button)).check(matches(isDisplayed()))
        onView(withId(R.id.sign_up_button)).perform(click())
        //verify(mockNaveController).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
    }
    @Before
    fun setUp() {
    }
}