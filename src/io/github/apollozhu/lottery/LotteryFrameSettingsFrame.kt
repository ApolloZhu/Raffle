package io.github.apollozhu.lottery

import javax.swing.JFrame
import javax.swing.SpringLayout

class LotteryFrameSettingsFrame : JFrame("设置") {
    init {
        layout = SpringLayout()
        SpringUtilities.makeGrid(this, 0, 0, 0, 0, 0, 0)
    }
}
