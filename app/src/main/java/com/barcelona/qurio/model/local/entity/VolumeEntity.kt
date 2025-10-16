package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "volume_level")
data class VolumeEntity(
    @PrimaryKey val id: Int = 1,
    val soundVolumeLevel: Int,
    val musicVolumeLevel: Int
)