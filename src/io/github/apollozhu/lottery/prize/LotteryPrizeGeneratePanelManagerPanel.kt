package io.github.apollozhu.lottery.prize

import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Font
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class LotteryPrizeGeneratePanelManagerPanel : JPanel() {

    private val cardLayout = CardLayout()
    private val panelList = ArrayList<LotteryPrizeGeneratePanel>()
    private val panelsContainer = JPanel()
    private var curIndex = 0
    private var nextId = Integer.MIN_VALUE

    private val label = JLabel("奖项设置")
    private val deleteButton = JButton("删除")
    private val previousButton = JButton("上一个")
    private val nextButton = JButton("下一个")

    init {
        layout = BorderLayout()

        add(label, BorderLayout.NORTH)
        label.font = label.font.deriveFont(Font.BOLD, 35f)

        add(panelsContainer, BorderLayout.CENTER)
        panelsContainer.layout = cardLayout
        addPrize()

        val controls = JPanel()
        add(controls, BorderLayout.SOUTH)
        controls.add(deleteButton)
        deleteButton.addActionListener { removePrize(it) }
        deleteButton.isEnabled = hasRemovablePrize()

        val button = JButton("添加")
        controls.add(button)
        button.addActionListener { addPrize(it) }

        controls.add(previousButton)
        previousButton.addActionListener {
            curIndex--
            indexShifted()
        }

        controls.add(nextButton)
        nextButton.addActionListener {
            curIndex++
            indexShifted()
        }
    }

    private fun indexShifted() {
        if (!hasRemovablePrize()) {
            previousButton.isEnabled = false
            nextButton.isEnabled = false
            deleteButton.isEnabled = false
        } else {
            previousButton.isEnabled = curIndex - 1 >= 0
            nextButton.isEnabled = curIndex + 1 < panelList.size
            deleteButton.isEnabled = true
        }
        cardLayout.show(panelsContainer, panelList[curIndex].identifier)
        val prizeName = panelList[curIndex].prizeName
        label.text = "奖项设置 ${curIndex + 1}/${panelList.size} ${if (prizeName.isBlank()) "" else " - $prizeName"}"
    }

    // FIXME: Not human friendly
    private fun addPrize(ignored: ActionEvent? = null) {
        val newPanel = LotteryPrizeGeneratePanel(nextId++.toString() + "")
        panelList.add(curIndex, newPanel)
        panelsContainer.add(newPanel, newPanel.identifier, curIndex)
        if (curIndex + 1 < panelList.size) {
            curIndex += 1
        }
        indexShifted()
    }

    internal fun hasRemovablePrize(): Boolean {
        return panelList.size > 1
    }

    // FIXME: Not human friendly
    private fun removePrize(ignored: ActionEvent? = null) {
        if (curIndex == panelList.size - 1) {
            cardLayout.show(panelsContainer, panelList[--curIndex].identifier)
        }
        panelList.removeAt(++curIndex)
        panelsContainer.remove(curIndex--)
        indexShifted()
    }

    val prizes: Array<LotteryPrizeModel>
        get() = panelList.map { it.model }
                .filter { it != null }
                .toTypedArray() as Array<LotteryPrizeModel>
}
