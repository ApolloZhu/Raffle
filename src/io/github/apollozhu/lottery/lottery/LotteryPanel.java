package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.prize.LotteryPrizeDisplayPanel;
import io.github.apollozhu.lottery.prize.LotteryPrizeModel;
import io.github.apollozhu.lottery.settings.LotteryPreferences;
import io.github.apollozhu.lottery.utils.RandomSelector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.prefs.PreferenceChangeEvent;

public class LotteryPanel extends JPanel {

    private JLabel title = new JLabel("", SwingConstants.CENTER);
    private JLabel subtitle = new JLabel("", SwingConstants.CENTER);
    private JComboBox<LotteryPrizeModel> box = new JComboBox();
    private JButton withdrawButton = new JButton("放弃");
    private JButton nextButton = new JButton("抽奖");

    public LotteryPanel() {
        setLayout(new BorderLayout());

        JPanel nPanel = new JPanel();
        add(nPanel, BorderLayout.NORTH);
        nPanel.setLayout(new GridLayout(2, 1));
        nPanel.add(title);
        nPanel.add(subtitle);

        replaceCenterPanelWith(new LotteryCenterPanel());

        JPanel sPanel = new JPanel();
        add(sPanel, BorderLayout.SOUTH);

        sPanel.add(box);
        box.addItemListener(ignored -> load((LotteryPrizeModel) box.getSelectedItem()));
        box.setRenderer((list, model, index, isSelected, cellHasFocus) -> new JLabel(model == null ? "无效奖项" : model.getName()));

        withdrawButton.addActionListener(this::withdraw);
        withdrawButton.setEnabled(false);
        sPanel.add(withdrawButton);

        nextButton.addActionListener(this::next);
        sPanel.add(nextButton);

        LotteryPreferences.INSTANCE.addListener(this::loadPreferences);
        loadPreferences(null);

        RandomSelector.INSTANCE.addListener(this::reloadCandidateList);
    }

    public void loadPreferences(PreferenceChangeEvent ignored) {
        title.setText(LotteryPreferences.INSTANCE.getTitle());
        title.setFont(title.getFont().deriveFont(LotteryPreferences.INSTANCE.getTitleSize()));
        title.setForeground(LotteryPreferences.INSTANCE.getTitleColor());

        subtitle.setText(LotteryPreferences.INSTANCE.getSubtitle());
        subtitle.setFont(subtitle.getFont().deriveFont(LotteryPreferences.INSTANCE.getSubtitleSize()));
        subtitle.setForeground(LotteryPreferences.INSTANCE.getSubtitleColor());
        subtitle.setVisible(!subtitle.getText().isEmpty());

        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext());

        center.setBackground(LotteryPreferences.INSTANCE.getBackgroundColor());

        box.removeAllItems();
        System.out.println(LotteryPreferences.INSTANCE.getPrizes().length);
        for (LotteryPrizeModel model : LotteryPreferences.INSTANCE.getPrizes()) {
            box.addItem(model);
        }
    }

    private String winner = "";
    private LotteryPrizeModel prizeModel;
    private int left;

    public void load(LotteryPrizeModel model) {
        if (model == null) return;
        prizeModel = model;
        left = prizeModel.getCount();
        winner = "";
        reloadCandidateList(null);
    }

    public void reloadCandidateList(ChangeEvent ignored) {
        stateDidChange(false);
    }

    private boolean isWaiting;

    public void next(ActionEvent ignored) {
        if (!hasNext()) return;
        if (isWaiting) {
            isWaiting = false;
            left--;
            winner = RandomSelector.INSTANCE.next();
            replaceCenterPanelWith(new LotteryPrizeDisplayPanel(winner, prizeModel));
            stateDidChange(true);
        } else {
            isWaiting = true;
            withdrawButton.setEnabled(false);
            replaceCenterPanelWith(new LotteryRollingPanel());
        }
    }

    public void withdraw(ActionEvent ignored) {
        left++;
        RandomSelector.INSTANCE.add(winner);
        replaceCenterPanelWith(new LotteryCenterPanel());
        stateDidChange(false);
    }

    public boolean hasNext() {
        return RandomSelector.INSTANCE.hasNext() && left > 0;
    }

    private void stateDidChange(boolean isWithdrawButtonEnabled) {
        nextButton.setEnabled(hasNext());
        withdrawButton.setEnabled(isWithdrawButtonEnabled);
    }

    private LotteryCenterPanel center;

    private void replaceCenterPanelWith(LotteryCenterPanel panel) {
        add(panel, BorderLayout.CENTER);
        if (center != null) remove(center);
        center = panel;
    }
}