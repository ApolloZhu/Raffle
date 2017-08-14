package io.github.apollozhu.lottery.settings

import java.awt.BorderLayout
import javax.swing.JPanel

class LotterySettingsPanel : JPanel() {
    private val basic = LotteryBasicSettingsPanel()
    private val prize = LotteryPrizeManagerPanel()

    init {
        layout = BorderLayout()
        add(basic, BorderLayout.NORTH)
        add(prize, BorderLayout.CENTER)
    }

    override fun setVisible(aFlag: Boolean) {
        super.setVisible(aFlag)
        if (aFlag) basic.loadPreferences() else {
            basic.savePreferences()
            LotteryPreferences.prizes = prize.prizes
        }
    }
}
