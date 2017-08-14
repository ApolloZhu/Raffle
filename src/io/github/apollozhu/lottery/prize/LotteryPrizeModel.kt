package io.github.apollozhu.lottery.prize

import java.awt.Image
import javax.swing.Icon

data class LotteryPrizeModel(val name: String, val count: Int, val image: Image?) {
    val hasImage = image != null
}
