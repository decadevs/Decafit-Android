package com.decagon.decafit.workout.utils

import android.os.Handler
import android.os.Looper
import com.decagon.decafit.common.common.data.preferences.Preference

class WorkoutCounter(listener: OnTimerTickListener) {
        private var handler = Handler(Looper.getMainLooper())
        private lateinit var runnable: Runnable
        private var duration = 0L
        private var delay = 100L

        init {
            runnable = Runnable{
                duration += delay
                handler.postDelayed(runnable, delay)
                listener.counterListener(countRemaining())
            }
        }

        private fun countRemaining():String{
            val secsCount:Long = (duration/1000) %60
            var secs = 0L
            val min : Long = (duration/1000/60)%60
            val count = Preference.getNumberOfCount(Preference.COUNT_KEY)!!.toLong()
            secs =count - secsCount
            if (count.toInt() >60 &&  count/60 == min && count%60 == secsCount ){
                stopTimer()
            }else if (count == secsCount){
                stopTimer()
            }
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