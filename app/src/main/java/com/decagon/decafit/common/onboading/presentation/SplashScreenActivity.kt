package com.decagon.decafit.common.onboading.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.decagon.decafit.MainActivity
import com.decagon.decafit.R
import com.decagon.decafit.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private var _binding:ActivitySplashScreenBinding? =null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashScreen()

        supportActionBar?.hide()
    }

    private fun splashScreen(){
        val text1 = binding.splashDecafitTV

        val topText = AnimationUtils.loadAnimation(this, R.anim.slide_from_top_animation)

        text1.startAnimation(topText)

        val splashScreenTimeOut = 4000
        var intent = Intent(this, MainActivity::class.java)

// use the handler to delay the activity of the splash screen
        Handler().postDelayed({
            startActivity(intent)
            finish()
        },splashScreenTimeOut.toLong())
    }
}