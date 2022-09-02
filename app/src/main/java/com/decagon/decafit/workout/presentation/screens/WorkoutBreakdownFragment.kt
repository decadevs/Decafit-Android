package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.decagon.decafit.GetReportWorkoutQuery
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportWorkoutData
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.TIME_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.USERID_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.WORKOUT_KEY
import com.decagon.decafit.common.utils.*
import com.decagon.decafit.databinding.ContinueExerciseDialogBinding
import com.decagon.decafit.databinding.FragmentWorkoutBreakdownBinding
import com.decagon.decafit.databinding.WorkoutDetailsDialogBinding
import com.decagon.decafit.workout.presentation.adapters.ReportAdapter
import com.decagon.decafit.workout.presentation.adapters.WorkoutAdapter
import com.decagon.decafit.workout.presentation.viewmodels.WorkoutViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutBreakdownFragment : Fragment(),OnclickListener {
    private var _binding :FragmentWorkoutBreakdownBinding? =null
    private val binding get() =_binding!!
    private lateinit var  workoutAdapter: WorkoutAdapter
    private lateinit var  reportExerciseAdapter: ReportAdapter
    private val viewModel:WorkoutViewModels by viewModels()
    lateinit var reportWorkout:List<WorkoutsQuery.Exercise?>


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
        //setUpRecyclerView()
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

    fun setUpReportRecyclerView(){
        reportExerciseAdapter = ReportAdapter(this, requireContext())
        val recyclerView = binding.workoutRV
        recyclerView.apply {
            adapter = reportExerciseAdapter
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
        val workoutId = Preference.getWorkoutId(WORKOUT_KEY)
        val userId = Preference.getUserId(USERID_KEY)
        viewModel.getReportWorkout(userId!!,workoutId!!, requireContext())
        viewModel.getWorkoutFromDb(workoutId).observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it.backgroundImage)
                .centerCrop()
                .into(binding.exerciseImage)
            binding.workoutBreakdownTv.text = getString(R.string.numberOfExercises,it.exercise?.size)
            getReportWorkoutFromDb()

            reportWorkout =it.exercise!!

            if(it.exercise!!.isEmpty()){
                binding.startWorkoutBtn.text = getString(R.string.no_exercise)
                binding.startWorkoutBtn.isEnabled = false
            }
        }
    }
    private fun getReportWorkoutFromDb(){
        val id = Preference.getWorkoutId(WORKOUT_KEY)
        viewModel.getReportExercise(id!!).observe(viewLifecycleOwner) {
            val dialogBinding = ContinueExerciseDialogBinding.inflate(layoutInflater)
            val chooseToContinueExercise =showChooseToContinueDialog(dialogBinding)
            if (it.isNotEmpty()) {
                chooseToContinueExercise.show()
                setUpReportRecyclerView()
                reportExerciseAdapter.differ.submitList(it)
                binding.startWorkoutBtn.visibility = View.INVISIBLE
                binding.continueWorkoutBtn.visibility =View.VISIBLE
            }else{
                setUpRecyclerView()
                workoutAdapter.differ.submitList(reportWorkout)
            }
        }
    }

    override fun onclickWorkoutItem(workoutItems: WorkoutsQuery.Exercise) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showWorkoutDetails(dialogBinding,workoutItems)
        workoutDetails.show()
    }

    override fun onclickReportExerciseItem(workoutItems: ReportExercise) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showReportDetails(dialogBinding,workoutItems)
        workoutDetails.show()    }
}