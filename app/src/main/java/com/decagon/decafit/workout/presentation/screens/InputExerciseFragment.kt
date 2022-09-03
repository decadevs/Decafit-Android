package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.utils.hideKeyboard
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentInputExerciseBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputExerciseFragment : Fragment() {
    private var _binding: FragmentInputExerciseBinding? = null
    private val binding get() = _binding!!
    private val args: InputExerciseFragmentArgs by navArgs()
    private lateinit var title: String
    private lateinit var image: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentInputExerciseBinding.inflate(layoutInflater, container, false)

        val activity = activity as AppCompatActivity?
        activity?.actionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = args.title
        image = args.image
        binding.workoutName.text = title
        Glide.with(requireContext())
            .load(image)
            .centerCrop()
            .into(binding.inputExerciseImage)
        initListeners()
    }
    private  fun initListeners(){
        binding.layout.setOnClickListener {
            it.hideKeyboard()
        }
        binding.nextExerciseBtn.setOnClickListener {
            setUpWorkout()
        }
        binding.inputExerciseBackArrowCV.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpWorkout(){
        val workoutSet = binding.numbersOfSetsET.text.toString()
        val workoutReps = binding.numbersRepsET.text.toString()
        val workoutTime = binding.estimatedTimeET.text.toString()
        val workoutCount = binding.numbersCountsET.text.toString()

        if (workoutReps.isEmpty() || workoutReps.isEmpty()||workoutCount.isEmpty()||workoutTime.isEmpty()){
            snackBar("Enter all fields")
        }else{
        with(Preference){
            saveWorkoutSet(workoutSet)
            saveWorkoutRep(workoutReps)
            saveEstimatedTime(workoutTime)
            saveNumberOfCount(workoutCount)
        }
            findNavController().navigate(R.id.action_inputExerciseFragment_to_workoutBreakdownFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Input Exercise"
    }
}