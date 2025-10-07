package com.barcelona.qurio.presentation.adapter.streakAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.databinding.DayItemBinding
import com.barcelona.qurio.presentation.model.streak.DayStreak

class StreakDayAdapter(private val days: List<DayStreak>) :
    RecyclerView.Adapter<StreakDayAdapter.DayViewHolder>() {

    inner class DayViewHolder(val binding: DayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: DayStreak) {
            binding.day = day
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DayItemBinding.inflate(inflater, parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount() = days.size
}