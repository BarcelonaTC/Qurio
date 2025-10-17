package com.barcelona.qurio.presentation.adapter.achievementAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.R

class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val achievementImage: ImageView = itemView.findViewById(R.id.achievment_image)
    val achievementTitle: TextView = itemView.findViewById(R.id.achievment_title)
}