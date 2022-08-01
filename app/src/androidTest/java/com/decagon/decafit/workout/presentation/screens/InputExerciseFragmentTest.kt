package com.decagon.decafit.workout.presentation.screens

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.decafit.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InputExerciseFragmentTest {

    private lateinit var senerio : FragmentScenario<InputExerciseFragment>
    @Before
    fun setUp() {
        senerio = launchFragmentInContainer(themeResId = R.style.Theme_DecafitAndroid)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_view_visibility(){
    onView(withId(R.id.imageView3)).check(matches(isDisplayed()))
    onView(withId(R.id.full_body_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.users_name_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.calory_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.set_limit_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.timer_tv)).check(matches(isDisplayed()))
    onView(withId(R.id.numbersOfSets_ET)).check(matches(isDisplayed()))
    onView(withId(R.id.numbersReps_ET)).check(matches(isDisplayed()))
    }
}