package com.decagon.decafit.workout.presentation.screens

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.decagon.decafit.R
import com.decagon.decafit.common.authentication.presentation.screens.SignUpFragment
import com.decagon.decafit.common.common.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class InputExerciseFragmentTest {

    @Before
    fun setUp() {
    }



    @Test
    fun test_view_visibility(){
        val mockNaveController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<InputExerciseFragment> {
            Navigation.setViewNavController( requireView(), mockNaveController)
        }
    onView(withId(R.id.next_exercise_btn)).check(matches(isDisplayed()))
    onView(withId(R.id.users_name_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.calory_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.set_limit_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.timer_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.numbersOfSets_ET)).check(matches(isDisplayed()))
    onView(withId(R.id.numbersReps_ET)).check(matches(isDisplayed()))
    }
}