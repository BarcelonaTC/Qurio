package com.barcelona.qurio.presentation.adapter.achievementAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.R
import com.barcelona.qurio.presentation.model.Achievement

@SuppressLint("NotifyDataSetChanged")
class AchievementAdapter(
    private var achievements: List<Achievement>,
    private val onAchievementClicked: (achievementId: Int) -> Unit
) : RecyclerView.Adapter<AchievementViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.achievement_item, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AchievementViewHolder,
        position: Int
    ) {
        val achievement = achievements[position]
        val currentImageRes = if (achievement.isLocked) achievement.lockedImage
        else achievement.imageRes
        holder.achievementImage.setImageResource(currentImageRes)
        holder.achievementTitle.text = achievement.title
        holder.itemView.setOnClickListener { onAchievementClicked(achievement.id) }
    }

    override fun getItemCount(): Int = achievements.size

    fun updateAchievements(updatedAchievements: List<Achievement>) {
        achievements = updatedAchievements
        notifyDataSetChanged()
    }

}