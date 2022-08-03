package com.decagon.decafit.workout.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.decafit.R
import com.decagon.decafit.common.utils.OnclickListener
import com.decagon.decafit.databinding.WorkoutBreakdownItemBinding
import com.decagon.decafit.workout.data.WorkoutItems

class WorkoutAdapter(private  val listener:OnclickListener, private val context: Context) :RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){

    private val callBack = object :DiffUtil.ItemCallback<WorkoutItems>(){
        override fun areItemsTheSame(oldItem: WorkoutItems, newItem: WorkoutItems): Boolean {
            return ((oldItem.title == newItem.title)&&(oldItem.workoutProgress ==newItem.workoutProgress))
        }

        override fun areContentsTheSame(oldItem: WorkoutItems, newItem: WorkoutItems): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    class ViewHolder(binding:WorkoutBreakdownItemBinding):RecyclerView.ViewHolder(binding.root){

         val title = binding.workoutTitleTv
         val timer = binding.exerciseTimerTv
         val workoutStatus = binding.workoutStatusTv
         val workoutProgressCard = binding.workoutStatusCV
         val progressBar = binding.workoutProgressBar

         @RequiresApi(Build.VERSION_CODES.M)
         fun bindView(items: WorkoutItems, context: Context){
             title.text = items.title
             timer.text =items.workoutTime.toString()
             if (items.workoutProgress ==100){
                 workoutProgressCard.visibility = View.VISIBLE
                 workoutStatus.setText(R.string.complete_workout)
                 workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_green))
                 progressBar.visibility = View.INVISIBLE
             }else if(items.workoutProgress !=0){
                 workoutProgressCard.visibility = View.VISIBLE
                 workoutStatus.setText(R.string.incomplete_workout)
                 workoutProgressCard.setCardBackgroundColor(context.getColor(R.color.light_orange))
                 progressBar.progress = items.workoutProgress
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
