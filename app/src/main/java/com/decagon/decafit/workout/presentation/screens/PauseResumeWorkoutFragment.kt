package com.decagon.decafit.workout.presentation.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.Optional
import com.bumptech.glide.Glide
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.COUNT_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.REP_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.SET_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.STEP_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.TIME_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.WORKOUT_KEY
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentPauseResumeWorkoutBinding
import com.decagon.decafit.type.ReportCreateInput
import com.decagon.decafit.type.ReportExcerciseProgressInput
import com.decagon.decafit.type.ReportWorkoutInput

import com.decagon.decafit.workout.presentation.viewmodels.WorkoutViewModels
import com.decagon.decafit.workout.utils.OnTimerTickListener
import com.decagon.decafit.workout.utils.WorkoutTimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PauseResumeWorkoutFragment : Fragment(),OnTimerTickListener,SensorEventListener {
    private var _binding:FragmentPauseResumeWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel:WorkoutViewModels by viewModels()
    lateinit var exerciseDatas: List<WorkoutWitIdQuery.Exercise?>
    private lateinit var timer :WorkoutTimer
    private var duration = ""
    private var num=0
    private var stepsTaken = 0
    private var repeat =0
    private var isPaused = false
    private val set = Preference.getWorkoutSet(SET_KEY)!!.toInt()
    private val reps = Preference.getWorkoutRep(REP_KEY)!!.toInt()
    private val estimatedTime = Preference.getEstimatedTime(TIME_KEY)
    private val numberOfCount = Preference.getNumberOfCount(COUNT_KEY)!!.toInt()
    lateinit var exerciseInput : List<ReportExcerciseProgressInput>


    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    private var sensorManager : SensorManager ? = null
    private var running = false
    private var totalSteps = 0
    private var previousSteps = 0
    private var currentStep=0
    private var stepCounter = 0
    var workoutId:String? =null



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
        getExerciseFromDb()
        initClickListener()
        if (isPermissionGranted()) {
            requestPermission()
        }
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager


    }

    private fun initClickListener(){
        binding.pauseBtn.setOnClickListener {
            isPaused = true

            if(running){
                stopStepCounter()
            }
            timer.pauseTimer()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
        }
        binding.resumeBtn.setOnClickListener {
            isPaused = false
                startStepCounter()
                timer.startTimer()
            binding.pauseBtn.visibility =View.VISIBLE
            binding.resumeBtn.visibility = View.INVISIBLE
        }
        binding.nextWorkoutBtn.setOnClickListener {
            timer.stopTimer()
            resetStepCounter()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
            repeat++
            if(repeat == reps){ //checks for the numbers of reps for each exercise
                num++
                repeat =0
                binding.nextWorkoutBtn.text ="Repeat"
                createReport()
            }else{
                binding.nextWorkoutBtn.text ="Next Exercise"
            }
            setUpWorkout()
        }
        binding.pauseWorkoutBackArrowIV.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun timerTickListener(duration: String, timeRemaining:String) {
        binding.workoutCounterTv.text = duration
        this.duration = duration.dropLast(3)
        binding.workoutProgressIndicator.progress = timer.progressTracker().toInt()
        binding.remainingTimeTv.text = timeRemaining
    }

    private fun getExerciseFromDb(){
        workoutId = Preference.getWorkoutId(WORKOUT_KEY)
        viewModel.getWorkoutWithId(workoutId!!,requireContext())
        viewModel.workoutWithIdResponse.observe(viewLifecycleOwner){
            exerciseDatas= it.data?.workout?.exercises!!
            startStepCounter()
            timer.startTimer()
            setUpWorkout()
        }
    }


    private fun setUpWorkout(){
        val numberOfWorkout= exerciseDatas.size
        if(num < numberOfWorkout){
            binding.workoutHeaderTv.text = exerciseDatas[num]?.title
            val exercise = exerciseDatas[num]
            exerciseInput = listOf(ReportExcerciseProgressInput(Optional.presentIfNotNull(exercise?.id),Optional.presentIfNotNull( exercise?.type), Optional.presentIfNotNull(isPaused), Optional.presentIfNotNull("2"), Optional.presentIfNotNull(true), Optional.presentIfNotNull(20)))
            if (exerciseDatas[num]?.type?.name == "count"){

                startStepCounter()
                binding.stepsCounterLayout.visibility = View.VISIBLE
                binding.workoutProgressIndicator.visibility = View.INVISIBLE
                binding.workoutCounterTv.visibility = View.INVISIBLE
                Glide.with(requireContext()).load(exerciseDatas[num]?.image)
                    .centerCrop()
                    .into(binding.pauseAndResumeImageIV)
                binding.workoutProgressIndicator.max = Preference.getNumberOfCount(COUNT_KEY)!!.toInt()
            }else{
                binding.stepsCounterLayout.visibility = View.INVISIBLE
                binding.workoutProgressIndicator.visibility = View.VISIBLE
                binding.workoutCounterTv.visibility = View.VISIBLE
                Glide.with(requireContext()).load(exerciseDatas[num]?.image)
                    .centerCrop()
                    .into(binding.pauseAndResumeImageIV)
                binding.workoutProgressIndicator.max = Preference.getEstimatedTime(TIME_KEY)!!.toInt()
            }
        }else{
            timer.stopTimer()
            binding.nextWorkoutBtn.text = "END"
            if(num > numberOfWorkout){
                findNavController().popBackStack()
                num =0
            }
        }
    }

    private fun startStepCounter() {
        running =true
        val stepSensor:Sensor? = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null){
            snackBar("No Sensor Detected On this Devices")
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun stopStepCounter(){
        Log.d("COUNTER", "currentStep STOP= $currentStep")
        running = false
        sensorManager?.unregisterListener(this)
    }

    private fun resetStepCounter(){
        currentStep = 0
        totalSteps =0
        stepsTaken = 0
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running){
            totalSteps =event.values[0].toInt()
            previousSteps = Preference.getPreviousStepCount(STEP_KEY)
            if (previousSteps == 0){
                binding.numbersOfStepsTv.text = "$currentStep Steps"
            }
            if (totalSteps >previousSteps){
                currentStep = totalSteps - previousSteps
                stepsTaken += currentStep
                binding.numbersOfStepsTv.text = "$stepsTaken Steps"
            }

            Log.d("COUNTER", "currentStep = $currentStep")
            Log.d("COUNTER", "previousStep = $previousSteps")
            Log.d("COUNTER", "totalSteps = $totalSteps")
            Preference.savePreviousStepCount(totalSteps)
        }
    }

    private fun createReport(){
        val reportWorkoutInput =
            ReportWorkoutInput(workoutId!!,reps,repeat,estimatedTime!!,numberOfCount,exerciseInput)
        val reportInput = ReportCreateInput(workoutId!!,reportWorkoutInput)
        viewModel.createReport(reportInput,requireContext())
    }

//    private fun getWorkoutReport(){
//        val userId = Preference.
//        viewModel.getReportWorkout(use)
//    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }
}