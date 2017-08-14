package io.github.apollozhu.lottery.prize

import io.github.apollozhu.lottery.lottery.LotteryCenterPanel

open class LotteryPrizeDisplayTextOnlyPanel : LotteryCenterPanel() {
    open fun displayFor(name: String, prizeModel: LotteryPrizeModel) {
        label.text = "恭喜 $name 获得 ${prizeModel.name}"
    }
}
