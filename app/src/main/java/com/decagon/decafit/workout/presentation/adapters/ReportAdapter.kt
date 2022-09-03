package com.decagon.decafit.workout.presentation.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.utils.OnclickListener
import com.decagon.decafit.databinding.WorkoutBreakdownItemBinding

class ReportAdapter (private  val listener: OnclickListener, private val context: Context) : RecyclerView.Adapter<ReportAdapter.ViewHolder>(){


    private val callBack = object : DiffUtil.ItemCallback<ReportExercise>(){
        override fun areItemsTheSame(oldItem: ReportExercise, newItem: ReportExercise): Boolean {
            return ((oldItem.title == newItem.title)&&(oldItem.excerciseId ==newItem.excerciseId))
        }

        override fun areContentsTheSame(oldItem: ReportExercise, newItem: ReportExercise): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)
    class ViewHolder (binding: WorkoutBreakdownItemBinding):RecyclerView.ViewHolder(binding.root){

        val title = binding.workoutTitleTv
        private val timer = binding.exerciseTimerTv
        private val exercisesImage = binding.exerciseImageIv
        private val workoutStatus = binding.workoutStatusTv
        private val workoutProgressCard = binding.workoutStatusCV
        private val progressBar = binding.workoutProgressBar

        @RequiresApi(Build.VERSION_CODES.M)
        fun bindView(items: ReportExercise, context: Context){
            title.text = items.title
            //timer.text =items.workoutTime.toString()
            Glide.with(context).load(items.image)
                .centerCrop()
                .override(65,56)
                .into(exercisesImage)
            exercisesImage.setBackgroundResource(R.drawable.full_body_img)
            val isComplete = false
            val pausedTime =0
            if (items.type!!.rawValue == "count"){
                "X${Preference.getNumberOfCount(Preference.COUNT_KEY)}".also { timer.text = it }
                progressBar.max = Preference.getNumberOfCount(Preference.COUNT_KEY)!!.toInt()
            }else{
                timer.text = Preference.getEstimatedTime(Preference.TIME_KEY)
                progressBar.max = Preference.getEstimatedTime(Preference.TIME_KEY)!!.toInt()
            }
            if (items.completed!!){
                workoutProgressCard.visibility = View.VISIBLE
                workoutStatus.setText(R.string.complete_workout)
                workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_green))
                progressBar.visibility = View.INVISIBLE
            }else{
                workoutProgressCard.visibility = View.VISIBLE
                workoutStatus.setText(R.string.incomplete_workout)
                workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_orange))
                progressBar.progress = items.progress!!
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = WorkoutBreakdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ReportAdapter.ViewHolder, position: Int) {
        val itemView = differ.currentList[position]
        holder.bindView(itemView, context)
        holder.itemView.setOnClickListener {
            listener.onclickReportExerciseItem(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}