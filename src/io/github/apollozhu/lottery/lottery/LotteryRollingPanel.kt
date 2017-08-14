package io.github.apollozhu.lottery.lottery

import io.github.apollozhu.lottery.utils.RandomSelector
import javax.swing.Timer

class LotteryRollingPanel : LotteryCenterPanel() {
    private val timer = Timer(30) {
        label.text = RandomSelector.list[RandomSelector.nextIndex()]
        checkState()
    }

    fun stop() = timer.stop()

    fun start() {
        if (RandomSelector.hasNext()) timer.start()
    }

    private fun checkState() {
        if (!RandomSelector.hasNext()) stop()
    }

    init {
        start()
    }

    override fun setVisible(aFlag: Boolean) {
        super.setVisible(aFlag)
        if (aFlag) timer.start() else timer.stop()
    }
}
