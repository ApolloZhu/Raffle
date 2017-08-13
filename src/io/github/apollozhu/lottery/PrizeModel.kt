package io.github.apollozhu.lottery

import javax.swing.ImageIcon

data class PrizeModel(val name: String, val count: Int, val imagePath: String) {
    val image = ImageIcon(imagePath).image
}
