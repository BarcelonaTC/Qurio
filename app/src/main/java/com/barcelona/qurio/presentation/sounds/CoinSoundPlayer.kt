package com.barcelona.qurio.presentation.sounds

import android.content.Context
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import androidx.annotation.RawRes

class CoinSoundPlayer(private val context: Context?) {

    private var soundPool: SoundPool? = null
    private var coinSoundId: Int = 0

    fun loadSound(@RawRes soundRes: Int, onLoaded: () -> Unit) {
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .build()

        coinSoundId = soundPool?.load(context, soundRes, 1) ?: 0

        soundPool?.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                onLoaded()
            }
        }
    }

    fun play() {
        soundPool?.play(coinSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        Handler(Looper.getMainLooper()).postDelayed({
            soundPool?.release()
            soundPool = null
        }, 1000)
    }
}