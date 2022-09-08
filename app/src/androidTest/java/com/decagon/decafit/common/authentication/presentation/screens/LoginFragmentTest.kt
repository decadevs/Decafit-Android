package com.decagon.decafit.common.authentication.presentation.screens

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
import com.decagon.decafit.R
import com.decagon.decafit.common.common.utils.launchFragmentInHiltContainer
import com.decagon.decafit.workout.presentation.screens.InputExerciseFragment
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
class LoginFragmentTest {

    private val email = "abdul@gmail.com"
    private val validPassword = "Password123#"

    @Test
    fun test_view_visibility() {
        val mockNaveController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), mockNaveController)
        }
         onView(withId(R.id.login_image)).check(matches(isDisplayed()))
         onView(withId(R.id.fragment_login_login_btn)).check(matches(isDisplayed()))
         onView(withId(R.id.fragment_login_email_ET)).check(matches(isDisplayed()))
         onView(withId(R.id.fragment_login_password_ET)).check(matches(isDisplayed()))
    }
    @Test
    fun textInput_test(){
        val mockNaveController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), mockNaveController)
        }
        onView(withId(R.id.fragment_login_email_ET)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.fragment_login_password_ET)).perform(typeText(validPassword), closeSoftKeyboard())
        onView(withId(R.id.fragment_login_login_btn)).perform(click())
   //     verify(mockNaveController).navigate(LoginFragmentDirections.actionLoginFragment2ToNavGraph())
    }
}