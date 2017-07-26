package io.github.apollozhu.lottery

import sun.audio.AudioPlayer
import sun.audio.AudioStream
import sun.audio.ContinuousAudioDataStream
import java.io.FileInputStream
import java.util.prefs.Preferences

object LotteryFrameSettings {
    private val preferences = Preferences.userNodeForPackage(javaClass)

    var title: String
        get() = preferences.get("title", "抽奖")
        set(value) = preferences.put("title", value)

    var subtitle: String
        get() = preferences.get("subtitle", "")
        set(value) = preferences.put("subtitle", value)

    var musicPath: String
        get() = preferences.get("musicPath", "")
        set(value) {
            preferences.put("musicPath", value)
            Player.play(value)
        }

    var listPath: String
        get() = preferences.get("listPath", "")
        set(value) = preferences.put("listPath", value)
}
