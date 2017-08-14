package io.github.apollozhu.lottery.prize

import javax.swing.ImageIcon

data class LotteryPrizeModel(val name: String, val count: Int, val imagePath: String) {
    val imageIcon = ImageIcon(imagePath)
    val image = imageIcon.image
}
