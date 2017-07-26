package io.github.apollozhu.lottery

import javax.swing.JFrame
import java.awt.Toolkit

class LotteryFrame : JFrame("抽奖软件（开源代码 github.com/ApolloZhu/Lottery）") {
    init {
        size = Toolkit.getDefaultToolkit().getScreenSize()
        isResizable = false
        isAlwaysOnTop = true
        type = Type.UTILITY
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }
}
