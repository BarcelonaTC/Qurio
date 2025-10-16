package com.barcelona.qurio.presentation.sounds

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.annotation.RawRes

class SoundPlayerManager(private val context: Context) {

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    private val soundMap = mutableMapOf<Int, Int>() // rawRes -> soundId
    private var soundVolume: Float = 1f
    private var musicVolume: Float = 1f

    private var mediaPlayer: MediaPlayer? = null
    private var pausedPosition: Int = 0

    fun loadSound(@RawRes soundRes: Int, onLoaded: (() -> Unit)? = null) {
        if (soundMap.containsKey(soundRes)) {
            onLoaded?.invoke()
            return
        }

        val soundId = soundPool.load(context, soundRes, 1)
        soundMap[soundRes] = soundId

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0 && sampleId == soundId) {
                onLoaded?.invoke()
            }
        }
    }

    fun playSound(@RawRes soundRes: Int) {
        soundMap[soundRes]?.let {
            soundPool.play(it, soundVolume, soundVolume, 1, 0, 1f)
        }
    }

    fun playMusic(@RawRes musicRes: Int, loop: Boolean = true) {
        mediaPlayer?.stop()
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, musicRes)?.apply {
            isLooping = loop
            setVolume(musicVolume, musicVolume)
            start()
        }
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                pausedPosition = it.currentPosition
                it.pause()
            }
        }
    }

    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.seekTo(pausedPosition)
                it.start()
            }
        }
    }

    fun setVolumeLevels(soundLevel: Int, musicLevel: Int) {
        soundVolume = soundLevel.coerceIn(0, 100) / 100f
        musicVolume = musicLevel.coerceIn(0, 100) / 100f
        mediaPlayer?.setVolume(musicVolume, musicVolume)
    }

    fun release() {
        soundPool.release()
        soundMap.clear()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}