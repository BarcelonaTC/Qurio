package com.barcelona.qurio.presenter.repository

interface VolumeLevelRepository {
    suspend fun getSoundVolumeLevel(): Int
    suspend fun getMusicVolumeLevel(): Int
    suspend fun saveVolumeLevels(soundLevel: Int, musicLevel: Int)
}