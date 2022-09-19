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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.mapper.ReportExerciseMapper
import com.decagon.decafit.common.common.data.database.mapper.ReportInputMapper
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.COUNT_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.REP_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.SET_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.STEP_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.TIME_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.WORKOUT_KEY
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentPauseResumeWorkoutBinding
import com.decagon.decafit.type.ReportExcerciseProgressInput
import com.decagon.decafit.type.ReportWorkoutInput
import com.decagon.decafit.workout.presentation.viewmodels.WorkoutViewModels
import com.decagon.decafit.workout.utils.OnTimerTickListener
import com.decagon.decafit.workout.utils.WorkoutCounter
import com.decagon.decafit.workout.utils.WorkoutTimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PauseResumeWorkoutFragment : Fragment(),OnTimerTickListener,SensorEventListener {

    private var _binding:FragmentPauseResumeWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel:WorkoutViewModels by viewModels()
    private lateinit var exerciseData: List<WorkoutsQuery.Exercise?>
    private lateinit var timer :WorkoutTimer
    private lateinit var counter:WorkoutCounter
    private var duration = ""
    private var num=0
    private var stepsTaken = 0
    private var repeat =0
    private var isPaused = false
    private var timeBaseProgress =0
    private var countProgress =0
     private var timeRemaining =0
    private val set = Preference.getWorkoutSet(SET_KEY)!!.toInt()
    private val reps = Preference.getWorkoutRep(REP_KEY)!!.toInt()
    private val estimatedTime = Preference.getEstimatedTime(TIME_KEY)!!.toInt()
    private val numberOfCount = Preference.getNumberOfCount(COUNT_KEY)!!.toInt()
    private lateinit var exerciseInput : ReportExercise
    private val args: PauseResumeWorkoutFragmentArgs? by navArgs()
    private var setCount =0

    private var countHolder =0
    private var timeHolder =0
    private var progressHolder = 0

    private val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    private var sensorManager : SensorManager ? = null
    private var running = false
    private var totalSteps = 0
    private var previousSteps = 0
    private var currentStep=0
    var workoutId:String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPauseResumeWorkoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity?.actionBar?.hide()
        timer = WorkoutTimer(this)
        counter = WorkoutCounter(this)
        getExerciseFromDb()
        initClickListener()
        if (isPermissionGranted()) {
            requestPermission()
        }
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }



    private fun initClickListener(){
        binding.pauseWorkoutBackArrowIV.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.pauseBtn.setOnClickListener {
            isPaused = true

            if(running){
                stopStepCounter()
            }
            timer.pauseTimer()
            counter.pauseTimer()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
            setUpWorkout()
        }
        binding.resumeBtn.setOnClickListener {
            isPaused = false
            startStepCounter()
            timer.startTimer()
            counter.startTimer()
            binding.pauseBtn.visibility =View.VISIBLE
            binding.resumeBtn.visibility = View.INVISIBLE
            setUpWorkout()
        }
        binding.nextWorkoutBtn.setOnClickListener {
            timer.stopTimer()
            counter.stopTimer()
            resetStepCounter()
            binding.resumeBtn.visibility = View.VISIBLE
            binding.pauseBtn.visibility =View.GONE
            repeat++
            countHolder+= countProgress
            timeHolder +=timeBaseProgress
            saveReportToLocalDB()
            if(repeat == reps){ //checks for the numbers of reps for each exercise
                num++
                repeat =0
                countHolder=0
                timeHolder=0
                //saveReportToLocalDB()
                binding.nextWorkoutBtn.setText(R.string.repeat_workout)
            }else if (repeat <reps-1){
                binding.nextWorkoutBtn.setText(R.string.repeat_workout)
            }else{
                binding.nextWorkoutBtn.setText(R.string.next_workout)
            }
            if(num > exerciseData.size && setCount<set){
                num =0
                repeat=0
                binding.nextWorkoutBtn.setText(R.string.repeat_workout)
            }
            setUpWorkout()
            timeBaseProgress=0
            countProgress =0


        }
    }




    override fun timerTickListener(duration: String) {
        binding.workoutCounterTv.text = duration
        this.duration = duration.dropLast(3)
        if (timer.progressTracker().toInt()!=0) {
            timeBaseProgress = timer.progressTracker().toInt() +1
            binding.workoutProgressIndicator.progress = timeBaseProgress
        }
        setUpWorkout()
    }

    override fun counterListener(timeRemaining: String) {
        binding.remainingTimeTv.text = timeRemaining
        this.timeRemaining =timeRemaining.toInt()
        if (counter.progressTracker().toInt()!=0) {
            countProgress = counter.progressTracker().toInt()+1
        }
        setUpWorkout()
    }

    private fun getExerciseFromDb(){
        workoutId = Preference.getWorkoutId(WORKOUT_KEY)
        viewModel.getWorkoutFromLocalDb(workoutId!!).observe(viewLifecycleOwner) {
            exerciseData = if (args?.reportExercise?.report!!.isNotEmpty()) {
                val mapper = ReportExerciseMapper()
                val exerciseList = mutableListOf<WorkoutsQuery.Exercise>()
                for (i in args?.reportExercise!!.report) {
                    val  d = mapper.mapTo(i)
                    exerciseList.add(d)
                }
                exerciseList
            } else {
                it.exercise!!
            }
            startStepCounter()
            timer.startTimer()
            setUpWorkout()
        }
    }

    private fun setUpWorkout(){
        val numberOfWorkout= exerciseData.size
        binding.repsCountTv.text = getString(R.string.reps_count,(repeat+1).toString(),reps.toString())
        if(reps==1)binding.nextWorkoutBtn.setText(R.string.next_workout)

        if(num < numberOfWorkout){
            binding.workoutHeaderTv.text = exerciseData[num]?.title
            val exercise = exerciseData[num]
            if (exerciseData[num]?.type?.name == getString(R.string.count)){
                startStepCounter()
                timer.stopTimer()
                binding.stepsCounterLayout.visibility = View.VISIBLE
                binding.workoutProgressIndicator.visibility = View.INVISIBLE
                binding.workoutCounterTv.visibility = View.INVISIBLE
                Glide.with(requireContext()).load(exerciseData[num]?.image)
                    .centerCrop()
                    .into(binding.pauseAndResumeImageIV)

                var isCompleted = false
                progressHolder=countHolder+countProgress
                val progress =((progressHolder.toDouble()/(numberOfCount*reps))*100).toInt()
                if (progress == 100){
                    isCompleted = true
                }
                exerciseInput= ReportExercise(workoutId, exercise?.id!!, exercise.title, exercise.description,exercise.image,exercise.type, isPaused,"2",isCompleted,progress)

            }else{
                counter.stopTimer()
                binding.stepsCounterLayout.visibility = View.INVISIBLE
                binding.workoutProgressIndicator.visibility = View.VISIBLE
                binding.workoutCounterTv.visibility = View.VISIBLE
                Glide.with(requireContext()).load(exerciseData[num]?.image)
                    .centerCrop()
                    .into(binding.pauseAndResumeImageIV)
                    binding.workoutProgressIndicator.max = Preference.getEstimatedTime(TIME_KEY)!!.toInt()
                var isCompleted = false
                progressHolder=timeHolder+timeBaseProgress
                val progress =((progressHolder.toDouble()/(estimatedTime*reps))*100).toInt()

                if (progress == 100){
                    isCompleted = true
                }
                exerciseInput= ReportExercise(workoutId, exercise?.id!!,exercise.title, exercise.description,exercise.image, exercise.type, isPaused,"2",isCompleted,progress)
            }
        }else{
            // display END button when all exercise is finished
            timer.stopTimer()
            counter.stopTimer()
            createReport()


            if (num > numberOfWorkout &&setCount==set){
                findNavController().popBackStack()
            }

            if (num == numberOfWorkout && setCount<set){
                setCount++
                repeat=0
                binding.nextWorkoutBtn.text = getString(R.string.move_to_next_set, (setCount+1).toString())
            }

            if(setCount==set){
                // go back to the workoutBreakdown fragment onclick of END button
                binding.nextWorkoutBtn.text = getString(R.string.end)
            }
            num += 1
        }
    }

    private fun startStepCounter() {
        running =true
        val stepSensor:Sensor? = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null){
            snackBar(getString(R.string.no_sensor_detected))
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun stopStepCounter(){
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
                binding.numbersOfStepsTv.text = getString(R.string.numbersOf_steps, currentStep.toString())
            }
            if (totalSteps >previousSteps){
                currentStep = totalSteps - previousSteps
                stepsTaken += currentStep
                binding.numbersOfStepsTv.text = getString(R.string.numbersOf_steps, currentStep.toString())
            }
            Preference.savePreviousStepCount(totalSteps)
        }
    }

    // save the reports of the workout to the server
    private fun createReport(){
        val workoutId = Preference.getWorkoutId(WORKOUT_KEY)!!

        /** get the report of each exercise from the local db then send it to the server*/
        viewModel.getReportOfExerciseFromLocalDB(workoutId).observe(viewLifecycleOwner){
            var  input : ReportExcerciseProgressInput?
            val reportMapper = ReportInputMapper()
            val exerciseFromDB = mutableListOf<ReportExcerciseProgressInput>()
                if (it.isNotEmpty()) {
                    for (i in it.listIterator()) {
                        input = reportMapper.mapTo(i)
                        exerciseFromDB.add(input)
                    }
                }

            val reportWorkoutInput = ReportWorkoutInput(workoutId,reps,set,estimatedTime.toString(),numberOfCount,exerciseFromDB)
            viewModel.createReport(reportWorkoutInput, requireContext())
        }
    }

    //saves the report of each exercise to the local db
    private fun saveReportToLocalDB(){
        viewModel.saveExerciseToLocalDB(exerciseInput)
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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }
}