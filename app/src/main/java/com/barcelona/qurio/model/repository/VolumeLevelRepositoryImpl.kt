package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.VolumeLevelDao
import com.barcelona.qurio.model.local.entity.VolumeEntity
import com.barcelona.qurio.presenter.repository.VolumeLevelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VolumeLevelRepositoryImpl @Inject constructor(
    private val dao: VolumeLevelDao
) : VolumeLevelRepository {

    override suspend fun getSoundVolumeLevel(): Int = withContext(Dispatchers.IO) {
        dao.getVolumeLevels()?.soundVolumeLevel ?: DEFAULT_SOUND_VOLUME
    }

    override suspend fun getMusicVolumeLevel(): Int = withContext(Dispatchers.IO) {
        dao.getVolumeLevels()?.musicVolumeLevel ?: DEFAULT_MUSIC_VOLUME
    }

    override suspend fun saveVolumeLevels(soundLevel: Int, musicLevel: Int) = withContext(Dispatchers.IO) {
        val current = dao.getVolumeLevels()

        if (current == null) {
            dao.insertOrUpdate(
                VolumeEntity(
                    soundVolumeLevel = soundLevel,
                    musicVolumeLevel = musicLevel
                )
            )
        } else {
            dao.insertOrUpdate(
                current.copy(
                    soundVolumeLevel = soundLevel,
                    musicVolumeLevel = musicLevel
                )
            )
        }
    }

    companion object {
        private const val DEFAULT_SOUND_VOLUME = 100
        private const val DEFAULT_MUSIC_VOLUME = 100
    }
}
