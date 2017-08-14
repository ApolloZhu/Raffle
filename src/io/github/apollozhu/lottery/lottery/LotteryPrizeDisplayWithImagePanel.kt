package io.github.apollozhu.lottery.lottery

import SpringUtilities
import io.github.apollozhu.lottery.frame
import io.github.apollozhu.lottery.prize.LotteryPrizeModel
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
        SpringUtilities.makeCompactGrid(this, 1, 2, 8, 8, 50, 8)
    }

    override fun displayFor(name: String, prizeModel: LotteryPrizeModel) {
        super.displayFor(name, prizeModel)
        if (!prizeModel.hasImage) return
        val image = prizeModel.image!!
        val icon = ImageIcon(image)
        val toWidth = if (icon.iconWidth < icon.iconHeight) -1 else frame.bounds.width / 4
        val toHeight = if (icon.iconWidth < icon.iconHeight) frame.bounds.height / 4 else -1
        imageLabel!!.icon = ImageIcon(image.getScaledInstance(toWidth, toHeight, Image.SCALE_DEFAULT))
    }
}
