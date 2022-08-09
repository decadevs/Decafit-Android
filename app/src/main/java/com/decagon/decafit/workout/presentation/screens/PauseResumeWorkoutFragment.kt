package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.databinding.FragmentPauseResumeWorkoutBinding
import com.decagon.decafit.workout.utils.OnTimerTickListener
import com.decagon.decafit.workout.utils.WorkoutTimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PauseResumeWorkoutFragment : Fragment(),OnTimerTickListener {
    private var _binding:FragmentPauseResumeWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var timer :WorkoutTimer
    private var duration = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPauseResumeWorkoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer = WorkoutTimer(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        timer.startTimer()
    }

    private fun initClickListener(){
        binding.pauseBtn.setOnClickListener {
            timer.pauseTimer()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
        }
        binding.resumeBtn.setOnClickListener {
            timer.startTimer()
            binding.pauseBtn.visibility =View.VISIBLE
            binding.resumeBtn.visibility = View.INVISIBLE
        }
        binding.nextWorkoutBtn.setOnClickListener {
            timer.stopTimer()
        }
        binding.pauseWorkoutBackArrowIV.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun timerTickListener(duration: String) {
        binding.workoutCounterTv.text = duration
        this.duration = duration.dropLast(3)
        binding.workoutProgressIndicator.progress = timer.progressTracker().toInt()
    }


}