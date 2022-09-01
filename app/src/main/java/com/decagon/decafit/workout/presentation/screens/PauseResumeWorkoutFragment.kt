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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.common.utils.dommyData.workoutData
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentPauseResumeWorkoutBinding
import com.decagon.decafit.workout.utils.OnTimerTickListener
import com.decagon.decafit.workout.utils.WorkoutTimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PauseResumeWorkoutFragment : Fragment(),OnTimerTickListener,SensorEventListener {
    private var _binding:FragmentPauseResumeWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var timer :WorkoutTimer
    private var duration = ""
    private var num=0
    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    private var sensorManager : SensorManager ? = null
    private var running = false
    private var totalSteps = 0F
    private var previousSteps = 0F
    private var currentStep=0
    private var stepCounter = 0



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
        if (isPermissionGranted()) {
            requestPermission()
        }
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        startStepCounter()

    }


    private fun initClickListener(){
        binding.pauseBtn.setOnClickListener {
            if(running){
                stopStepCounter()
            }
            timer.pauseTimer()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
        }
        binding.resumeBtn.setOnClickListener {
            if (running){
                startStepCounter()
            }else{
                timer.startTimer()
            }
            binding.pauseBtn.visibility =View.VISIBLE
            binding.resumeBtn.visibility = View.INVISIBLE
        }
        binding.nextWorkoutBtn.setOnClickListener {
//            timer.stopTimer()
//            stopStepCounter()
            resetStepCounter()
            num++
            setUpWorkout()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
        }
    }

    override fun timerTickListener(duration: String, timeRemaining:String) {
        num = timer.workoutTracker().toInt()
        setUpWorkout()
        binding.workoutCounterTv.text = duration
        this.duration = duration.dropLast(3)
        binding.workoutProgressIndicator.progress = timer.progressTracker().toInt()
        binding.remainingTimeTv.text = timeRemaining
    }


    private fun setUpWorkout(){
       val numberOfWorkout= workoutData.size
        if(num < numberOfWorkout){
            binding.workoutHeaderTv.text = workoutData[num].title
            if (workoutData[num].title == "Running"){
                startStepCounter()
                binding.stepsCounterLayout.visibility = View.VISIBLE
                binding.workoutProgressIndicator.visibility = View.INVISIBLE
                binding.workoutCounterTv.visibility = View.INVISIBLE
            }else{
                binding.stepsCounterLayout.visibility = View.INVISIBLE
                binding.workoutProgressIndicator.visibility = View.VISIBLE
                binding.workoutCounterTv.visibility = View.VISIBLE
            }
        }else{
            timer.stopTimer()
        }
    }

    private fun startStepCounter() {
        Log.d("COUNTER", "currentStep START = $stepCounter")
        running =true
        val stepSensor:Sensor? = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null){
            snackBar("No Sensor Detected On this Devices")
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun stopStepCounter(){
        Log.d("COUNTER", "currentStep STOP= $stepCounter")
        running = false
        sensorManager?.unregisterListener(this)
    }

    private fun resetStepCounter(){
        currentStep = 0
        totalSteps =0F
        previousSteps = 0F
        sensorManager?.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent) {

        if (running){
            totalSteps =event.values[0]
             currentStep = totalSteps.toInt() - previousSteps.toInt()
            binding.numbersOfStepsTv.text= getString(R.string.numbersOf_steps,currentStep.toString())

            Log.d("COUNTER", "currentStep = $currentStep")
            Log.d("COUNTER", "totalSteps = $totalSteps")

        }
    }

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

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Workout Progress"
    }
}