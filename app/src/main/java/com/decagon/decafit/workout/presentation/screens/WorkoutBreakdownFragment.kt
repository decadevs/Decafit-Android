package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.utils.OnclickListener
import com.decagon.decafit.common.utils.ProgressBarLoading
import com.decagon.decafit.common.utils.showWorkoutDetails
import com.decagon.decafit.databinding.FragmentWorkoutBreakdownBinding
import com.decagon.decafit.databinding.WorkoutDetailsDialogBinding
import com.decagon.decafit.workout.presentation.adapters.WorkoutAdapter
import com.decagon.decafit.workout.presentation.viewmodels.WorkoutViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutBreakdownFragment : Fragment(),OnclickListener {
    private var _binding :FragmentWorkoutBreakdownBinding? =null
    private val binding get() =_binding!!
    lateinit var  workoutAdapter: WorkoutAdapter
    private val viewModel:WorkoutViewModels by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =FragmentWorkoutBreakdownBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoading()
        setUpRecyclerView()
        initListener()
        getExerciseFromDb()
    }

    fun initListener(){
        binding.continueWorkoutBtn.setOnClickListener {
            binding.continueWorkoutBtn.visibility = View.GONE
            binding.startWorkoutBtn.visibility = View.VISIBLE
        }
        binding.startWorkoutBtn.setOnClickListener {
            binding.startWorkoutBtn.visibility = View.GONE
            binding.continueWorkoutBtn.visibility = View.VISIBLE
            findNavController().navigate(R.id.pauseResumeWorkoutFragment)
        }

        binding.backArrowCV.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    fun setUpRecyclerView(){
        workoutAdapter = WorkoutAdapter(this, requireContext())
        val recyclerView = binding.workoutRV
        recyclerView.apply {
            adapter = workoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun isLoading(){
        val progressBar = ProgressBarLoading(requireContext())
        viewModel.progressBar.observe(viewLifecycleOwner){
            if (it){
                progressBar.show()
            }else{
                progressBar.dismiss()
            }
        }}

    private fun getExerciseFromDb(){
        val id = Preference
        viewModel.getWorkoutWithId("6300106a9d9bd74931c514c9",requireContext())
        viewModel.workoutWithIdResponse.observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it.data?.workout?.backgroundImage)
                .centerCrop()
                .into(binding.exerciseImage)
            binding.workoutBreakdownTv.text = getString(R.string.numberOfExercises,it.data?.workout?.exercises?.size)
            workoutAdapter.differ.submitList(it.data?.workout?.exercises)

        }
    }


    override fun onclickWorkoutItem(workoutItems: WorkoutWitIdQuery.Exercise) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showWorkoutDetails(dialogBinding,workoutItems)
        workoutDetails.show()
    }



}