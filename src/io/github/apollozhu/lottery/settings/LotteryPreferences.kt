package io.github.apollozhu.lottery.settings

import io.github.apollozhu.lottery.settings.LotteryPreferences.forEachListener
import io.github.apollozhu.util.AZListenable
import java.awt.Color
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences
import javax.swing.event.EventListenerList

object LotteryPreferences : AZListenable<PreferenceChangeListener> {
    private val preferences = Preferences.userNodeForPackage(javaClass)

    private val list = EventListenerList()
    override fun getListenerList() = list
    internal fun fireUpdate() = forEachListener { it.preferenceChange(null) }

    var title: String
        get() {
            val title = preferences.get("title", "")
            return if (title.isBlank()) "抽奖" else title
        }
        set(value) = preferences.put("title", value)

    var titleSize: Float
        get() = preferences.getFloat("titleSize", 80f)
        set(value) = preferences.putFloat("titleSize", value)

    var titleColor: Color
        get() = Color(preferences.getInt("titleColor", 0XFFFF00))
        set(value) = preferences.putInt("titleColor", value.rgb)

    var subtitle: String
        get() = preferences.get("subtitle", "")
        set(value) = preferences.put("subtitle", value)

    var subtitleSize: Float
        get() = preferences.getFloat("subtitleSize", 50f)
        set(value) = preferences.putFloat("subtitleSize", value)

    var subtitleColor: Color
        get() = Color(preferences.getInt("subtitleColor", 0))
        set(value) = preferences.putInt("subtitleColor", value.rgb)

    var backgroundMusicPath: String
        get() {
            val backgroundMusicPath = preferences.get("backgroundMusicPath", "")
            return if (backgroundMusicPath.isBlank()) "选择背景音乐" else backgroundMusicPath
        }
        set(value) = preferences.put("backgroundMusicPath", value)

    var backgroundImagePath: String
        get() {
            val backgroundImagePath = preferences.get("backgroundImagePath", "")
            return if (backgroundImagePath.isBlank()) "选择背景图片" else backgroundImagePath
        }
        set(value) = preferences.put("backgroundImagePath", value)

    var backgroundColor: Color
        get() = Color(preferences.getInt("backgroundColor", 0xFF0000))
        set(value) = preferences.putInt("backgroundColor", value.rgb)

    var listPath: String
        get() {
            val listPath = preferences.get("listPath", "")
            return if (listPath.isBlank()) "选择人员名单" else listPath
        }
        set(value) = preferences.put("listPath", value)
}
