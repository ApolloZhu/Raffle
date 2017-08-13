package io.github.apollozhu.lottery

import java.awt.Color
import java.awt.Container
import java.awt.GraphicsEnvironment
import javax.swing.JFrame
import javax.swing.JTabbedPane


fun main(args: Array<String>) {
    val tabbedPane = JTabbedPane()
    val lotteryPanel = LotteryPanel()
    tabbedPane.addTab("抽奖", lotteryPanel)
    LotteryPreferences.addListener { setBackgroundRecursively(container = lotteryPanel) }
    setBackgroundRecursively(container = lotteryPanel)
    val settingsPanel = LotterySettingsPanel()
    tabbedPane.addTab("设置", settingsPanel)
    tabbedPane.selectedIndex = 1

    val frame = JFrame("抽奖软件（开源代码 github.com/ApolloZhu/Lottery）")
    frame.contentPane = tabbedPane
    frame.bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    LotteryPreferences.addListener { frame.background = LotteryPreferences.backgroundColor }
    frame.background = LotteryPreferences.backgroundColor
    frame.isVisible = true

    Player.play(/*"/Users/Apollonian/Music/Music Converter/m.wav"*/)
}

fun setBackgroundRecursively(color: Color = LotteryPreferences.backgroundColor, container: Container) {
    for (component in container.components) {
        component.background = color
        if (component is Container) {
            setBackgroundRecursively(color, component)
        }
    }
}
