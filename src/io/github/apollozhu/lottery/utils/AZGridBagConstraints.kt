package io.github.apollozhu.lottery.utils

import java.awt.GridBagConstraints
import java.awt.Insets

class AZGridBagConstraints(gridx: Int = RELATIVE, gridy: Int = RELATIVE, gridwidth: Int = 1, gridheight: Int = 1, weightx: Double = 0.0, weighty: Double = 0.0, anchor: Int = CENTER, fill: Int = BOTH, insets: Insets = Insets(0, 0, 0, 0), ipadx: Int = 0, ipady: Int = 0) : GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady) {}
