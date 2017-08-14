package io.github.apollozhu.lottery.prize

import io.github.apollozhu.lottery.utils.AZGridBagConstraints
import io.github.apollozhu.swing.AZJButton
import java.awt.GridBagLayout
import java.awt.Image
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


data class LotteryPrizeGeneratePanel(val identifier: String) : JPanel() {
    private val nameTextField = JTextField()
    private val countTextField = JTextField()
    private val imagePathButton = AZJButton("选择图片")
    private var prizeImage: Image? = null

    init {
        layout = GridBagLayout()
        add(JLabel("名称"), AZGridBagConstraints(0, 0))
        add(nameTextField, AZGridBagConstraints(1, 0, weightx = 100.0))
        add(JLabel("人数"), AZGridBagConstraints(0, 1))
        add(countTextField, AZGridBagConstraints(1, 1, weightx = 100.0))
        add(JLabel("图片"), AZGridBagConstraints(0, 2, gridheight = 2, weighty = 100.0))
        add(imagePathButton, AZGridBagConstraints(1, 2, gridheight = 2, weightx = 100.0, weighty = 100.0))
        imagePathButton.addActionListener {
            val chooser = JFileChooser()
            chooser.dialogTitle = "选择${if (hasPrizeName()) prizeName else "奖品"}图片"
            chooser.dialogType = JFileChooser.FILES_ONLY
            chooser.fileFilter = FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", "png")
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagePathButton.text = chooser.selectedFile.path
                prizeImage = ImageIcon(imagePathButton.text).image
                imagePathButton.icon = ImageIcon(prizeImage!!.getScaledInstance(imagePathButton.width, imagePathButton.height, Image.SCALE_DEFAULT))
            }
        }
    }

    val prizeName: String
        get() = nameTextField.text.trim()

    fun hasPrizeName() = prizeName.isNotBlank()

    val prizeCount: String
        get() = countTextField.text.trim()

    val unsafePrizeCount: Int?
        get() = prizeCount.toIntOrNull()

    fun isDataValid() = hasPrizeName()
            && unsafePrizeCount != null
            && unsafePrizeCount!! > 0

    val model: LotteryPrizeModel?
        get() = if (isDataValid()) LotteryPrizeModel(prizeName, unsafePrizeCount!!, prizeImage) else null
}
