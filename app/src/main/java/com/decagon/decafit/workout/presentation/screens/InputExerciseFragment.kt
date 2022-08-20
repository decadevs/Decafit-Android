package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.common.utils.dommyData.workoutData
import com.decagon.decafit.databinding.FragmentInputExerciseBinding
import com.decagon.decafit.databinding.FragmentLoginBinding
import com.decagon.decafit.workout.data.WorkoutItems

class InputExerciseFragment : Fragment() {
    private var _binding: FragmentInputExerciseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentInputExerciseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private  fun initListeners(){
        binding.nextExerciseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_inputExerciseFragment_to_workoutBreakdownFragment)
        }
        binding.inputExerciseBackArrowCV.setOnClickListener {
            updateWorkout()
            findNavController().popBackStack()
        }
    }

    fun updateWorkout(){
        workoutData.set(2,
            WorkoutItems("Planks", "Lorem ipsum dolor sit amt Lorem ipsum dolor sit amtLorem ipsum dolor sit amtLorem ipsum dolor sit amtLorem ip amtLorem ipsum dolor sit amtLorem ipsum dolor sit amtLorem ipsum dolor sit amt sum dolor sit amtLorem ipsum dolor sit amt ","1:00", 10),
        )
    }

}