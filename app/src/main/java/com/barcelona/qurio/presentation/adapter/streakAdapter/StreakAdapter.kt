package com.barcelona.qurio.presentation.adapter.streakAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.DayItemBinding
import com.barcelona.qurio.databinding.StreakItemBinding
import com.barcelona.qurio.presentation.model.streak.StreakModel

class StreakAdapter(private val streak: StreakModel) :
    RecyclerView.Adapter<StreakAdapter.StreakViewHolder>() {

    inner class StreakViewHolder(val binding: StreakItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(streak: StreakModel) {
            binding.streak = streak
            binding.daysContainer.removeAllViews()

            val inflater = LayoutInflater.from(binding.root.context)
            streak.days.forEach { day ->
                val dayBinding: DayItemBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.day_item,
                    binding.daysContainer,
                    false
                )
                dayBinding.day = day
                binding.daysContainer.addView(dayBinding.root)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreakViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: StreakItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.streak_item, parent, false)
        return StreakViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreakViewHolder, position: Int) {
        holder.bind(streak)
    }

    override fun getItemCount(): Int = 1
}