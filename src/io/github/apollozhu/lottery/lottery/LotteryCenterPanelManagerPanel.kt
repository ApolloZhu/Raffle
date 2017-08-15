package io.github.apollozhu.lottery.lottery

import io.github.apollozhu.lottery.prize.LotteryPrizeDisplayTextOnlyPanel
import io.github.apollozhu.lottery.prize.LotteryPrizeDisplayWithImagePanel
import io.github.apollozhu.lottery.prize.LotteryPrizeModel
import java.awt.CardLayout
import java.awt.event.MouseListener
import javax.swing.JPanel

class LotteryCenterPanelManagerPanel(clickListener: MouseListener) : JPanel() {
    private val cardLayout = CardLayout()

    private val placeholder = LotteryCenterPanel()
    private val roller = LotteryRollingPanel()
    private val displayWithImage = LotteryPrizeDisplayWithImagePanel()
    private val displayTextOnly = LotteryPrizeDisplayTextOnlyPanel()

    init {
        layout = cardLayout
        add(placeholder, "placeholder")
        add(roller, "roller")
        add(displayWithImage, "displayWithImage")
        add(displayTextOnly, "displayTextOnly")
        addMouseListener(clickListener)
    }

    fun showPlaceHodler() = cardLayout.show(this, "placeholder")

    fun showRoller() = cardLayout.show(this, "roller")

    fun showFor(winner: String, prizeModel: LotteryPrizeModel) {
        if (prizeModel.hasImage) {
            displayWithImage.displayFor(winner, prizeModel)
            cardLayout.show(this, "displayWithImage")
        } else {
            displayTextOnly.displayFor(winner, prizeModel)
            cardLayout.show(this, "displayTextOnly")
        }
    }
}
