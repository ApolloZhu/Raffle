package io.github.apollozhu.lottery

import io.github.apollozhu.lottery.lottery.LotteryPanel
import io.github.apollozhu.lottery.settings.LotteryPreferences
import io.github.apollozhu.lottery.settings.LotterySettingsPanel
import io.github.apollozhu.lottery.utils.BackgroundMusicPlayer
import java.awt.Color
import java.awt.Container
import java.awt.GraphicsEnvironment
import javax.swing.JFrame
import javax.swing.JTabbedPane


private val settingsPanel = LotterySettingsPanel()
val frame = JFrame("抽奖软件（开源代码 github.com/ApolloZhu/Lottery）- 朱智语个人作品")
val tabbedPane = JTabbedPane()

fun main(args: Array<String>) {

    val lotteryPanel = LotteryPanel()
    tabbedPane.addTab("抽奖", lotteryPanel)
    LotteryPreferences.addListener { setBackgroundRecursively(container = lotteryPanel) }
    setBackgroundRecursively(container = lotteryPanel)

    tabbedPane.addTab("设置", settingsPanel)
    tabbedPane.selectedIndex = 1

    frame.contentPane = tabbedPane
    frame.bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    LotteryPreferences.addListener { frame.background = LotteryPreferences.backgroundColor }
    frame.background = LotteryPreferences.backgroundColor
    frame.isVisible = true

    BackgroundMusicPlayer.play(/*"/Users/Apollonian/Music/Music Converter/m.wav"*/)
}

fun setBackgroundRecursively(color: Color = LotteryPreferences.backgroundColor, container: Container) {
    for (component in container.components) {
        component.background = color
        if (component is Container) {
            setBackgroundRecursively(color, component)
        }
    }
}
