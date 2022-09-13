package com.decagon.decafit.common.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.decafit.common.common.data.database.model.WorkOutData
import com.decagon.decafit.common.utils.onItemClickListener
import com.decagon.decafit.databinding.DashboardRecyclerItemBinding

class DashBoardAdapter(private val workOuts: List<WorkOutData>): RecyclerView.Adapter<DashBoardAdapter.AppHolder>() {

    private lateinit var context: Context
    private lateinit var mListener: onItemClickListener

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    inner class AppHolder(binding: DashboardRecyclerItemBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root)  {
        private val workOutImage: ImageView = binding.card1ExImage
        private val workOutName: TextView = binding.card1Ex

        private val item = binding.root

        init {

            item.setOnClickListener {
                listener.allAppsItemClicked(adapterPosition)
            }
        }

        fun bind(workOuts: WorkOutData) {
            Glide.with(context)
                .load(workOuts.backgroundImage)
                .into(workOutImage)
            workOutName.text = workOuts.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppHolder {
        val binding =
            DashboardRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return AppHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: AppHolder, position: Int) {
        holder.bind(workOuts[position])
    }

    override fun getItemCount(): Int {
        return workOuts.size
    }
}


