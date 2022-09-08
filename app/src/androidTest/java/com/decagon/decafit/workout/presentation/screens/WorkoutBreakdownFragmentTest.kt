package com.decagon.decafit.workout.presentation.screens

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.decafit.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutBreakdownFragmentTest {

    private lateinit var senerio : FragmentScenario<WorkoutBreakdownFragment>
    @Before
    fun setUp() {
        senerio = launchFragmentInContainer(themeResId = R.style.Theme_DecafitAndroid)
    }

    @Test
    fun test_view_visibility(){
        onView(withId(R.id.back_arrow_CV)).check(matches(isDisplayed()))
        onView(withId(R.id.start_workout_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.workout_cal_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.workout_RV)).check(matches(isDisplayed()))
    }
}