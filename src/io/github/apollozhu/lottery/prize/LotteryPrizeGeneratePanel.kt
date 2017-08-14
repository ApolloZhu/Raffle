package io.github.apollozhu.lottery.prize

import io.github.apollozhu.lottery.utils.AZGridBagConstraints
import io.github.apollozhu.swing.AZJButton
import java.awt.GridBagLayout
import java.awt.Image
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


data class LotteryPrizeGeneratePanel(val identifier: String) : JPanel() {
    private val name = JTextField()
    private val count = JTextField()
    private val imagePathButton = AZJButton("选择图片")

    init {
        layout = GridBagLayout()
        add(JLabel("名称"), AZGridBagConstraints(0, 0))
        add(name, AZGridBagConstraints(1, 0, weightx = 100.0))
        add(JLabel("人数"), AZGridBagConstraints(0, 1))
        add(count, AZGridBagConstraints(1, 1, weightx = 100.0))
        add(JLabel("图片"), AZGridBagConstraints(0, 2, gridheight = 2, weighty = 100.0))
        add(imagePathButton, AZGridBagConstraints(1, 2, gridheight = 2, weightx = 100.0, weighty = 100.0))
        imagePathButton.preferredSize.height = name.maximumSize.height
        imagePathButton.addActionListener {
            val chooser = JFileChooser()
            chooser.dialogTitle = "选择${if (name.text.isBlank()) "奖品" else name.text.trim()}图片"
            chooser.dialogType = JFileChooser.FILES_ONLY
            chooser.fileFilter = FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", "png")
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagePathButton.text = chooser.selectedFile.path
                imagePathButton.icon = ImageIcon(ImageIcon(imagePathButton.text).image.getScaledInstance(imagePathButton.width, imagePathButton.height, Image.SCALE_DEFAULT))
            }
        }
    }

    val prizeName: String
        get() = name.text.trim()

    val prizeCount: String
        get() = count.text.trim()

    val unsafePrizeCount: Int?
        get() = prizeCount.toIntOrNull()

    val prizeImagePath: String
        get() = imagePathButton.text.trim()

    val isDataValid: Boolean
        get() = prizeName.isNotBlank()
                && unsafePrizeCount != null
                && unsafePrizeCount!! > 0

    val model: LotteryPrizeModel?
        get() = if (isDataValid) LotteryPrizeModel(prizeName, unsafePrizeCount!!, prizeImagePath) else null
}
