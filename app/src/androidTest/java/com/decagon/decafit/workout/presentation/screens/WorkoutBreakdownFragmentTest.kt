package com.decagon.decafit.workout.presentation.screens

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
<<<<<<< HEAD
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.decagon.decafit.R
=======
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.decagon.decafit.R
import org.junit.Assert.*

>>>>>>> 50d73bd4402999f89005fc6294075e14ada29663
import org.junit.Before
import org.junit.Test

class WorkoutBreakdownFragmentTest {

    private lateinit var senerio : FragmentScenario<WorkoutBreakdownFragment>
    @Before
    fun setUp() {
        senerio = launchFragmentInContainer(themeResId = R.style.Theme_DecafitAndroid)
    }

    @Test
    fun test_view_visibility(){
        onView(withId(R.id.back_arrow_CV)).check(matches(isDisplayed()))
        onView(withId(R.id.exersice_image)).check(matches(isDisplayed()))
        onView(withId(R.id.start_workout_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.workout_cal_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.workout_RV)).check(matches(isDisplayed()))
    }
}