package io.github.apollozhu.lottery.prize

import java.awt.Image

data class LotteryPrizeModel(val name: String, val count: Int, val image: Image?) {
    val hasImage = image != null
}
