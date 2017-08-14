package io.github.apollozhu.lottery.lottery

import io.github.apollozhu.lottery.settings.LotteryPreferences
import io.github.apollozhu.lottery.utils.PreferenceLoading
import java.awt.Font
import java.util.prefs.PreferenceChangeEvent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

open class LotteryCenterPanel : JPanel(), PreferenceLoading {
    protected var label = JLabel("", SwingConstants.CENTER)

    protected open fun addLabel() {
        add(label)
    }

    init {
        addLabel()
        LotteryPreferences.addListener { loadPreferences() }
        loadPreferences()
    }

    override fun setVisible(aFlag: Boolean) {
        super.setVisible(aFlag)
        if (aFlag) loadPreferences()
    }

    override fun loadPreferences(ignored: PreferenceChangeEvent?) {
        label.font = label.font.deriveFont(Font.BOLD, LotteryPreferences.winnerSize)
        label.foreground = LotteryPreferences.winnerColor
        background = LotteryPreferences.backgroundColor
    }
}
