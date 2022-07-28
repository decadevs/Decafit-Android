package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagon.decafit.R
import com.decagon.decafit.databinding.FragmentWorkoutBreakdownBinding


class WorkoutBreakdownFragment : Fragment() {
    private var _binding :FragmentWorkoutBreakdownBinding? =null
    private val binding get() =_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentWorkoutBreakdownBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



}