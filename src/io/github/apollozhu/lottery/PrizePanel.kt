package io.github.apollozhu.lottery

import SpringUtilities
import io.github.apollozhu.swing.AZJButton
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


class PrizePanel : JPanel() {
    private val name = JTextField()
    private val count = JTextField()
    private val imagePath = AZJButton("选择图片")

    init {
        layout = SpringLayout()
        add(JLabel("名称"))
        add(name)
        add(JLabel("人数"))
        add(count)
        add(JLabel("图片"))
        add(imagePath)
        imagePath.addActionListener {
            val chooser = JFileChooser()
            chooser.dialogTitle = "选择${if (name.text.isBlank()) "奖品" else name.text.trim()}图片"
            chooser.dialogType = JFileChooser.FILES_ONLY
            chooser.fileFilter = FileNameExtensionFilter("Images", "jpg", "gif", "png")
            when (chooser.showOpenDialog(this)) {
                JFileChooser.APPROVE_OPTION -> imagePath.text = chooser.selectedFile.path
            }
        }
        SpringUtilities.makeCompactGrid(this, 3, 2, 8, 8, 8, 8)
    }

    private val prizeName: String
        get() = name.text.trim()

    private val prizeCount: String
        get() = count.text.trim()

    private val unsafePrizeCount: Int
        get() = Integer.parseUnsignedInt(prizeCount)

    private val prizeImagePath: String
        get() = imagePath.text.trim()

    val isDataValid: Boolean
        get() = prizeName.length > 0
                && prizeCount.matches("\\d+".toRegex())
                && unsafePrizeCount > 0

    val model: PrizeModel?
        get() = if (isDataValid) PrizeModel(prizeName, unsafePrizeCount, prizeImagePath) else null
}
