package com.decagon.decafit.workout.utils

interface OnTimerTickListener {
    fun timerTickListener(duration :String)
    fun counterListener(timeRemaining :String)
}