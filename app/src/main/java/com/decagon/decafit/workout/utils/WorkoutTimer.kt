package com.decagon.decafit.workout.utils

import android.os.Handler
import android.os.Looper

class WorkoutTimer(listener: OnTimerTickListener) {
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable{
            duration += delay
            handler.postDelayed(runnable, delay)
            listener.timerTickListener(format(), timeRemaining())
        }
    }

    private fun format():String{
        val secs:Long = (duration/1000) %60
        val min : Long = (duration/1000/60)%60
        return  "%02d:%02d".format( min,secs)
    }

    fun timeRemaining():String{
        val secs:Long = 60 - ((duration/1000) %60)
        val min : Long = 2-((duration/1000/60)%60)
        return "%02d:%02d".format( min,secs)
    }
    fun progressTracker() = (duration/1000)%60
    fun workoutTracker() =(duration/1000/60)%60

    fun startTimer(){
        handler.postDelayed(runnable, delay)
    }
    fun pauseTimer(){
        handler.removeCallbacks(runnable)
    }
    fun stopTimer(){
        handler.removeCallbacks(runnable)
        duration = 0L
    }
}