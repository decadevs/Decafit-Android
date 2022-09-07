package com.decagon.decafit.workout.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.decagon.decafit.GetReportWorkoutQuery
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutWitIdQuery
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.models.Exercises
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.COUNT_KEY
import com.decagon.decafit.common.common.data.preferences.Preference.TIME_KEY
import com.decagon.decafit.common.utils.OnclickListener
import com.decagon.decafit.databinding.WorkoutBreakdownItemBinding
import com.decagon.decafit.workout.data.WorkoutItems

class WorkoutAdapter(private  val listener:OnclickListener, private val context: Context) :RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){

    private val callBack = object :DiffUtil.ItemCallback<WorkoutsQuery.Exercise>(){
        override fun areItemsTheSame(oldItem: WorkoutsQuery.Exercise, newItem: WorkoutsQuery.Exercise): Boolean {
            return ((oldItem.title == newItem.title)&&(oldItem.id ==newItem.id))
        }

        override fun areContentsTheSame(oldItem: WorkoutsQuery.Exercise, newItem: WorkoutsQuery.Exercise): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    class ViewHolder(binding:WorkoutBreakdownItemBinding):RecyclerView.ViewHolder(binding.root){

         val title = binding.workoutTitleTv
         private val timer = binding.exerciseTimerTv
        private val exercisesImage = binding.exerciseImageIv
         private val workoutStatus = binding.workoutStatusTv
         private val workoutProgressCard = binding.workoutStatusCV
         private val progressBar = binding.workoutProgressBar

         fun bindView(items: WorkoutsQuery.Exercise, context: Context){
             title.text = items.title
             //timer.text =items.workoutTime.toString()
             Glide.with(context).load(items.image)
                 .centerCrop()
                 .override(65,56)
                 .into(exercisesImage)
             exercisesImage.setBackgroundResource(R.drawable.full_body_img)
             val isComplete = false
             val pausedTime =0
             if (items.type.rawValue == "count"){
                 "X${Preference.getNumberOfCount(COUNT_KEY)}".also { timer.text = it }
             }else{
                 timer.text = Preference.getEstimatedTime(TIME_KEY)
             }
             if (isComplete){
                 workoutProgressCard.visibility = View.VISIBLE
                 workoutStatus.setText(R.string.complete_workout)
                // workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_green))
                 progressBar.visibility = View.INVISIBLE
             }else if(pausedTime !=0){
                 workoutProgressCard.visibility = View.VISIBLE
                 workoutStatus.setText(R.string.incomplete_workout)
                // workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_orange))
                 progressBar.progress = pausedTime
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorkoutBreakdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = differ.currentList[position]
        holder.bindView(itemView, context)
        holder.itemView.setOnClickListener {
            listener.onclickWorkoutItem(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
