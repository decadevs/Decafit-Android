package com.decagon.decafit.common.dashboard

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.decagon.decafit.R


@RunWith(AndroidJUnit4::class)
class DashBoardFragmentTest {

    private lateinit var senerio : FragmentScenario<DashBoardFragment>
    @Before
    fun setUp() {
        senerio = launchFragmentInContainer(themeResId = R.style.Theme_DecafitAndroid)
    }

    @Test
    fun test_view_visibility(){
        Espresso.onView(ViewMatchers.withId(R.id.apple_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}