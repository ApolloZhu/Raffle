package io.github.apollozhu.lottery.utils

import io.github.apollozhu.lottery.settings.LotteryPreferences
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.io.File

object BackgroundMusicPlayer {
    // IMPORTANT: Magic, Do NOT Touch
    val panel = JFXPanel()
    // END-IMPORTANT

    init {
        LotteryPreferences.addListener { play() }
        play()
    }

    var currentPlayer: MediaPlayer? = null

    fun stop() {
        currentPlayer?.stop()
    }

    private var currentPath = ""

    fun play(path: String = LotteryPreferences.backgroundMusicPath) {
        Platform.runLater {
            if (path != currentPath) {
                currentPath = path
                try {
                    val media = Media(File(path).toURI().toString())
                    val player = MediaPlayer(media)
                    player.cycleCount = MediaPlayer.INDEFINITE
                    player.play()
                    stop()
                    currentPlayer = player
                } catch (_: Exception) {
                }
            }
        }

    }
}
