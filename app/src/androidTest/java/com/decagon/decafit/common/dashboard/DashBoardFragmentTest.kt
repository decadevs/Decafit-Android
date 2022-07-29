package com.decagon.decafit.common.dashboard

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.decafit.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DashBoardFragmentTest{

    private lateinit var senerio : FragmentScenario<DashBoardFragment>
    @Before
    fun setUp() {
        senerio = launchFragmentInContainer(themeResId = R.style.Theme_DecafitAndroid)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_view_visibility(){
        Espresso.onView(ViewMatchers.withId(R.id.back_arrow_CV))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.exersice_image))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.start_workout_btn))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.workout_cal_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.workout_RV))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}