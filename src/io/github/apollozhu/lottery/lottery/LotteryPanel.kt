package io.github.apollozhu.lottery.lottery

import io.github.apollozhu.lottery.prize.LotteryPrizeModel
import io.github.apollozhu.lottery.settings.LotteryPreferences
import io.github.apollozhu.lottery.tabbedPane
import io.github.apollozhu.lottery.utils.PreferenceLoading
import io.github.apollozhu.lottery.utils.RandomSelector
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.prefs.PreferenceChangeEvent
import javax.swing.*

class LotteryPanel : JPanel(), PreferenceLoading {

    private val title = JLabel("", SwingConstants.CENTER)
    private val subtitle = JLabel("", SwingConstants.CENTER)
    private val box = object : JComboBox<LotteryPrizeModel>() {
        override fun setBackground(bg: Color?) {}
    }
    private val withdrawButton = JButton("放弃")
    private val nextButton = JButton("抽奖")
    private val settingsButton = JButton("设置")
    private val centerPanels = LotteryCenterPanelManagerPanel(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) = next()
    })

    init {
        layout = BorderLayout()

        val nPanel = JPanel()
        add(nPanel, BorderLayout.NORTH)
        nPanel.layout = GridLayout(3, 1)
        nPanel.add(JPanel())
        nPanel.add(title)
        nPanel.add(subtitle)

        add(centerPanels, BorderLayout.CENTER)

        val sPanel = JPanel()
        add(sPanel, BorderLayout.SOUTH)

        box.addItemListener { load(box.getSelectedItem() as LotteryPrizeModel?) }
        box.setRenderer { _, model, _, _, _ -> JLabel(if (model == null) "无效奖项" else model.name) }
        sPanel.add(box)

        withdrawButton.addActionListener { withdraw() }
        withdrawButton.isEnabled = false
        sPanel.add(withdrawButton)

        nextButton.addActionListener { next() }
        sPanel.add(nextButton)

        settingsButton.addActionListener { tabbedPane.selectedIndex = 1 }
        sPanel.add(settingsButton)

        LotteryPreferences.addListener { loadPreferences() }
        loadPreferences()

        RandomSelector.addListener { reloadCandidateList() }
    }

    override fun loadPreferences(ignored: PreferenceChangeEvent?) {
        title.text = LotteryPreferences.title
        title.font = title.font.deriveFont(LotteryPreferences.titleSize)
        title.foreground = LotteryPreferences.titleColor

        subtitle.text = LotteryPreferences.subtitle
        subtitle.font = subtitle.font.deriveFont(LotteryPreferences.subtitleSize)
        subtitle.foreground = LotteryPreferences.subtitleColor
        subtitle.isVisible = subtitle.text.isNotBlank()

        nextButton.isEnabled = RandomSelector.hasNext()

        centerPanels.background = LotteryPreferences.backgroundColor

        box.removeAllItems()
        for (model in LotteryPreferences.prizes) {
            box.addItem(model)
        }
    }

    private var winner = ""
    private var prizeModel: LotteryPrizeModel? = null
    private var left: Int = 0

    fun load(model: LotteryPrizeModel?) {
        if (model == null) return
        prizeModel = model
        left = prizeModel!!.count
        winner = ""
        reloadCandidateList()
    }

    fun reloadCandidateList() = stateDidChange(false)

    private var isWaiting: Boolean = false

    fun next() {
        if (!hasNext()) return
        if (isWaiting) {
            isWaiting = false
            left--
            winner = RandomSelector.next()!!
            stateDidChange(true)
            centerPanels.showFor(winner, prizeModel!!)
        } else {
            isWaiting = true
            withdrawButton.isEnabled = false
            centerPanels.showRoller()
        }
    }

    fun withdraw() {
        left++
        RandomSelector.add(winner)
        stateDidChange(false)
        centerPanels.showPlaceHodler()
    }

    operator fun hasNext(): Boolean {
        return prizeModel != null && RandomSelector.hasNext() && left > 0
    }

    private fun stateDidChange(isWithdrawButtonEnabled: Boolean) {
        nextButton.isEnabled = hasNext()
        withdrawButton.isEnabled = isWithdrawButtonEnabled
    }
}
