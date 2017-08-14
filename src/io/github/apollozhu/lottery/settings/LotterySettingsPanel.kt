package io.github.apollozhu.lottery.settings

import io.github.apollozhu.lottery.prize.LotteryPrizeGeneratePanelManagerPanel
import java.awt.BorderLayout
import javax.swing.JPanel

class LotterySettingsPanel : JPanel() {
    private val basic = LotterySettingsBasicSettingsPanel()
    private val prize = LotteryPrizeGeneratePanelManagerPanel()

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
            LotteryPreferences.fireUpdate()
        }
    }
}
