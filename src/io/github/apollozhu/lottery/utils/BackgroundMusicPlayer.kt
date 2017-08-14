package io.github.apollozhu.lottery.utils

import io.github.apollozhu.lottery.settings.LotteryPreferences
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

object BackgroundMusicPlayer {
    init {
        LotteryPreferences.addListener { play() }
        play()
    }

    private var clip = AudioSystem.getClip()
    private var currentPath = ""

    fun stop() {
        try {
            clip.close()
        } catch (ignored: Exception) {
        }
    }

    fun play(path: String = LotteryPreferences.backgroundMusicPath) {
        if (path == currentPath) {
            return
        }
        currentPath = path
        stop()
        Thread {
            try {
                clip = AudioSystem.getClip()
                AudioSystem.getAudioInputStream(File(path)).use { stream ->
                    clip.open(stream)
                    clip.loop(Clip.LOOP_CONTINUOUSLY)
                }
            } catch (ignored: Exception) {
            }
        }.start()
    }
}
