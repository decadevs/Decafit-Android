package com.decagon.decafit.common.common.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.decagon.decafit.R
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.decagon.decafit.HiltTestActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline  fun <reified T :Fragment> launchFragmentInHiltContainer(
    fragmentArgs:Bundle? =null,
    themResId : Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action:T.()->Unit ={} // crossinline keyword is use when ever we executing a lamda function inside inline functions
){
    // create an intent that will our activity

    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra("androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
    themResId)


    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity {activity->

        fragmentFactory?.let {
         activity.supportFragmentManager.fragmentFactory = it
     }

        // this creates our fragment
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        fragment.arguments = fragmentArgs
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()
        (fragment as T).action()
    }
}