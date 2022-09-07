package com.decagon.decafit.workout.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.database.model.ReportList
import com.decagon.decafit.common.common.data.preferences.Preference
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
    lateinit var reportExercise:ReportList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =FragmentWorkoutBreakdownBinding.inflate(layoutInflater, container, false)
        val activity = activity as AppCompatActivity?
        activity?.actionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLoading()
        initListener()
        getExerciseFromDb()
    }


    private fun initListener(){
        binding.continueWorkoutBtn.setOnClickListener {
            findNavController().navigate(WorkoutBreakdownFragmentDirections.actionWorkoutBreakdownFragmentToPauseResumeWorkoutFragment2(reportExercise = reportExercise))
        }
        binding.startWorkoutBtn.setOnClickListener {
            binding.startWorkoutBtn.visibility = View.GONE
            binding.continueWorkoutBtn.visibility = View.VISIBLE
            findNavController().navigate(WorkoutBreakdownFragmentDirections.actionWorkoutBreakdownFragmentToPauseResumeWorkoutFragment2(reportExercise = reportExercise))
        }
        binding.backArrowCV.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    private fun setUpWorkoutRecyclerView(){
        workoutAdapter = WorkoutAdapter(this, requireContext())
        val recyclerView = binding.workoutRV
        recyclerView.apply {
            adapter = workoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        workoutAdapter.differ.submitList(reportWorkout)
    }

    private fun setUpReportRecyclerView(reportExercise: List<ReportExercise>){
        reportExerciseAdapter = ReportAdapter(this, requireContext())
        val recyclerView = binding.workoutRV
        recyclerView.apply {
            adapter = reportExerciseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        reportExerciseAdapter.differ.submitList(reportExercise)
        binding.startWorkoutBtn.visibility = View.INVISIBLE
        binding.continueWorkoutBtn.visibility =View.VISIBLE
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

        viewModel.getWorkoutFromLocalDb(workoutId).observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it.backgroundImage)
                .centerCrop()
                .into(binding.exerciseImage)
            binding.workoutBreakdownTv.text = getString(R.string.numberOfExercises,it.exercise?.size)
            getReportWorkoutFromLocalDb()

            reportWorkout =it.exercise!!

            if(it.exercise.isEmpty()){
                binding.startWorkoutBtn.text = getString(R.string.no_exercise)
                binding.startWorkoutBtn.isEnabled = false
            }
        }
    }
    private fun getReportWorkoutFromLocalDb(){
        val id = Preference.getWorkoutId(WORKOUT_KEY)
        viewModel.getReportExercise(id!!).observe(viewLifecycleOwner) { report ->
            val dialogBinding = ContinueExerciseDialogBinding.inflate(layoutInflater)
            reportExercise= ReportList( report.filter { it.completed ==false })
            val chooseToContinueExercise =showChooseToContinueDialog(dialogBinding,
                { setUpWorkoutRecyclerView() }, { setUpReportRecyclerView(report.filter { it.completed ==false }) })
            if (report.isNotEmpty() && report.any { it.completed == false }) {
                chooseToContinueExercise.show()
            }else{
                setUpWorkoutRecyclerView()
            }
        }
    }

    override fun onclickWorkoutItem(workoutItems: WorkoutsQuery.Exercise) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showWorkoutDetails(dialogBinding,workoutItems)
        workoutDetails.show()
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Workout Breakdown"
    }

    override fun onclickReportExerciseItem(workoutItems: ReportExercise) {
        val dialogBinding = WorkoutDetailsDialogBinding.inflate(layoutInflater)
        val workoutDetails = showReportDetails(dialogBinding,workoutItems)
        workoutDetails.show()    }
}