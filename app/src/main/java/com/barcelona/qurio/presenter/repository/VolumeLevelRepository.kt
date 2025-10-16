package com.barcelona.qurio.presenter.repository

interface VolumeLevelRepository {
    suspend fun getSoundVolumeLevel(): Int
    suspend fun getMusicVolumeLevel(): Int
    suspend fun saveSoundVolumeLevel(soundLevel: Int)
    suspend fun saveMusicVolumeLevel(musicLevel: Int)
}