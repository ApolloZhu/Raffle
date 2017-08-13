package io.github.apollozhu.lottery

import SpringUtilities
import io.github.apollozhu.swing.AZJButton
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

class LotterySettingsPanel : JPanel() {

    private val titleTextField = JTextField()
    private val titleSizeTextField = JTextField()
    private val titleColorButton = AZJButton()

    private val subtitleTextField = JTextField()
    private val subtitleSizeTextField = JTextField()
    private val subtitleColorButton = AZJButton()

    private val backgroundMusicPathButton = AZJButton()
    private val backgroundImagePathButton = AZJButton()
    private val backgroundColorButton = AZJButton()

    private val listButton = AZJButton()

    init {
        layout = BorderLayout()

        val nPanel = JPanel()
        add(nPanel, BorderLayout.NORTH)
        nPanel.layout = BorderLayout()

        val uiSettingsPanel = JPanel()
        nPanel.add(uiSettingsPanel, BorderLayout.CENTER)
        uiSettingsPanel.layout = SpringLayout()

        var label = JLabel("标题", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        uiSettingsPanel.add(titleTextField)
        label = JLabel("大小", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        uiSettingsPanel.add(titleSizeTextField)
        label = JLabel("颜色", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        titleColorButton.addActionListener {
            titleColorButton.background = JColorChooser.showDialog(this, "选择标题的颜色", titleColorButton.background) ?: titleColorButton.background
        }
        uiSettingsPanel.add(titleColorButton)

        label = JLabel("副标题", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        uiSettingsPanel.add(subtitleTextField)
        label = JLabel("大小", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        uiSettingsPanel.add(subtitleSizeTextField)
        label = JLabel("颜色", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        subtitleColorButton.addActionListener {
            subtitleColorButton.background = JColorChooser.showDialog(this, "选择副标题的颜色", subtitleColorButton.background) ?: subtitleColorButton.background
        }
        uiSettingsPanel.add(subtitleColorButton)

        label = JLabel("背景音乐", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        backgroundMusicPathButton.addActionListener {
            val chooser = JFileChooser(backgroundMusicPathButton.text)
            chooser.dialogTitle = "选择背景音乐"
            chooser.dialogType = JFileChooser.FILES_ONLY
            chooser.fileFilter = FileNameExtensionFilter("Music", "wav")
            when (chooser.showOpenDialog(this)) {
                JFileChooser.APPROVE_OPTION -> backgroundMusicPathButton.text = chooser.selectedFile.path
                else -> backgroundMusicPathButton.text = ""
            }
        }
        uiSettingsPanel.add(backgroundMusicPathButton)
        label = JLabel("背景图片", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        backgroundImagePathButton.isEnabled = false
        uiSettingsPanel.add(backgroundImagePathButton)
        label = JLabel("背景颜色", SwingConstants.RIGHT)
        uiSettingsPanel.add(label)
        backgroundColorButton.addActionListener {
            backgroundColorButton.background = JColorChooser.showDialog(this, "选择背景颜色", backgroundColorButton.background) ?: backgroundColorButton.background
        }
        uiSettingsPanel.add(backgroundColorButton)

        SpringUtilities.makeCompactGrid(uiSettingsPanel, 3, 6, 8, 8, 8, 8)

        val candidateListPanel = JPanel()
        nPanel.add(candidateListPanel, BorderLayout.SOUTH)
        candidateListPanel.layout = SpringLayout()
        label = JLabel("人员名单", SwingConstants.RIGHT)
        candidateListPanel.add(label)
        listButton.addActionListener {
            val chooser = JFileChooser(listButton.text)
            chooser.dialogTitle = "选择人员名单"
            chooser.dialogType = JFileChooser.FILES_ONLY
            when (chooser.showOpenDialog(this)) {
                JFileChooser.APPROVE_OPTION -> listButton.text = chooser.selectedFile.path
            }
        }
        candidateListPanel.add(listButton)
        SpringUtilities.makeCompactGrid(candidateListPanel, 1, 2, 8, 8, 8, 8)

        add(PrizePanel(), BorderLayout.CENTER)
    }

    fun loadPreferences() {
        titleTextField.text = LotteryPreferences.title
        titleSizeTextField.text = "${LotteryPreferences.titleSize}"
        titleColorButton.background = LotteryPreferences.titleColor

        subtitleTextField.text = LotteryPreferences.subtitle
        subtitleSizeTextField.text = "${LotteryPreferences.subtitleSize}"
        subtitleColorButton.background = LotteryPreferences.subtitleColor

        backgroundMusicPathButton.text = LotteryPreferences.backgroundMusicPath
        backgroundImagePathButton.text = LotteryPreferences.backgroundImagePath
        backgroundColorButton.background = LotteryPreferences.backgroundColor

        listButton.text = LotteryPreferences.listPath
    }

    fun savePreferences() {
        LotteryPreferences.title = titleTextField.text
        LotteryPreferences.titleSize = titleSizeTextField.text.toFloatOrNull() ?: LotteryPreferences.titleSize
        LotteryPreferences.titleColor = titleColorButton.background

        LotteryPreferences.subtitle = subtitleTextField.text
        LotteryPreferences.subtitleSize = subtitleSizeTextField.text.toFloatOrNull() ?: LotteryPreferences.subtitleSize
        LotteryPreferences.subtitleColor = subtitleColorButton.background

        LotteryPreferences.backgroundMusicPath = backgroundMusicPathButton.text
        LotteryPreferences.backgroundImagePath = backgroundImagePathButton.text
        LotteryPreferences.backgroundColor = backgroundColorButton.background

        LotteryPreferences.listPath = listButton.text

        LotteryPreferences.fireUpdate()
    }

    override fun setVisible(aFlag: Boolean) {
        super.setVisible(aFlag)
        if (aFlag) loadPreferences() else savePreferences()
    }
}
