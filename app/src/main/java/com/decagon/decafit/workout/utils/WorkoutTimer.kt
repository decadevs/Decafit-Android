package com.decagon.decafit.workout.utils

import android.os.Handler
import android.os.Looper
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.COUNT_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.TIME_KEY

class WorkoutTimer(listener: OnTimerTickListener) {
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable{
            duration += delay
            handler.postDelayed(runnable, delay)
            listener.timerTickListener(format())
        }
    }

    private fun format():String{
        val secs:Long = (duration/1000) %60
        val min : Long = (duration/1000/60)%60
        val estimatedTime = Preference.getEstimatedTime(TIME_KEY)
         if (estimatedTime!!.toInt() >60 &&  estimatedTime.toLong()/60 == min && estimatedTime.toLong()%60 == secs ){
            stopTimer()
        }else if (estimatedTime.toLong() == secs){
           stopTimer()
        }
        return  "%02d:%02d".format( min,secs)

    }

    private fun countRemaining():String{
        var secs = 0L
        val count = Preference.getNumberOfCount(COUNT_KEY)!!.toLong()
         secs =count - ((duration/1000) %60)
        return "%02d".format( secs)

    }
    fun progressTracker(): Long {
        val min: Long = (duration / 1000 / 60) % 60
        return min  + ((duration / 1000) % 60)
    }

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