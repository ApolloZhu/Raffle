package io.github.apollozhu.lottery.prize

import SpringUtilities
import io.github.apollozhu.lottery.frame
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.SpringLayout
import javax.swing.SwingConstants

class LotteryPrizeDisplayWithImagePanel : LotteryPrizeDisplayTextOnlyPanel() {
    private var imageLabel: JLabel? = null

    override fun addLabel() {
        layout = SpringLayout()
        imageLabel = JLabel("", SwingConstants.RIGHT)
        add(imageLabel!!)
        label = JLabel("", SwingConstants.LEFT)
        add(label)
        SpringUtilities.makeCompactGrid(this, 1, 2, 50, 50, 50, 8)
    }

    override fun displayFor(name: String, prizeModel: LotteryPrizeModel) {
        super.displayFor(name, prizeModel)
        if (!prizeModel.hasImage) return
        val image = prizeModel.image!!
        val icon = ImageIcon(image)
        val isLongThin = icon.iconWidth < icon.iconHeight
        val toWidth = if (isLongThin) -1 else frame.bounds.width / 4
        val toHeight = if (isLongThin) frame.bounds.height / 4 else -1
        val scaled = image.getScaledInstance(toWidth, toHeight, Image.SCALE_DEFAULT)
        imageLabel!!.icon = ImageIcon(scaled)
    }
}
