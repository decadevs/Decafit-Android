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
import com.decagon.decafit.databinding.FragmentInputExerciseBinding
import com.decagon.decafit.databinding.FragmentLoginBinding
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
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Input Exercise"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = args.title
        image = args.image
        binding.workoutName.text = title
        Glide.with(requireContext())
            .load(image)
            .into(binding.workoutImage)
        initListeners()

        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Input Exercise"
    }
    private  fun initListeners(){
        binding.nextExerciseBtn.setOnClickListener {
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