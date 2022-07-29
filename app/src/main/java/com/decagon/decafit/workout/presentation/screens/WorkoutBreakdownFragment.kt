package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.decafit.R
import com.decagon.decafit.common.utils.OnclickListener
import com.decagon.decafit.common.utils.dommyData.workoutData
import com.decagon.decafit.common.utils.showWorkoutDetails
import com.decagon.decafit.databinding.FragmentWorkoutBreakdownBinding
import com.decagon.decafit.databinding.WorkoutDetailsDialogBinding
import com.decagon.decafit.workout.data.WorkoutItems
import com.decagon.decafit.workout.presentation.adapters.WorkoutAdapter


class WorkoutBreakdownFragment : Fragment(),OnclickListener {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initListener()
    }

    fun initListener(){
        binding.continueWorkoutBtn.setOnClickListener {
            binding.continueWorkoutBtn.visibility = View.GONE
            binding.startWorkoutBtn.visibility = View.VISIBLE

        }
        binding.startWorkoutBtn.setOnClickListener {
            binding.startWorkoutBtn.visibility = View.GONE
            binding.continueWorkoutBtn.visibility = View.VISIBLE
        }
        binding.backArrowCV.setOnClickListener {
            findNavController().navigate(R.id.action_workoutBreakdownFragment_to_inputExerciseFragment)
        }
    }
    fun setUpRecyclerView(){
        val workoutAdapter = WorkoutAdapter(this, requireContext())
        val recyclerView = binding.workoutRV
        recyclerView.apply {
            adapter = workoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        workoutAdapter.differ.submitList(workoutData)
    }


    override fun onclickWorkoutItem(workoutItems: WorkoutItems) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showWorkoutDetails(dialogBinding,workoutItems)
        workoutDetails.show()
    }
}