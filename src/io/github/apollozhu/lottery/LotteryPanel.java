package io.github.apollozhu.lottery;

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
    private JLabel winnerLabel = new JLabel();

    public LotteryPanel() {
        setLayout(new BorderLayout());

        JPanel nPanel = new JPanel();
        add(nPanel, BorderLayout.NORTH);
        nPanel.setLayout(new GridLayout(2, 1));
        nPanel.add(title);
        nPanel.add(subtitle);

        JPanel cPanel = new JPanel();
        add(cPanel, BorderLayout.CENTER);
        cPanel.add(winnerLabel);

        JPanel sPanel = new JPanel();
        add(sPanel, BorderLayout.SOUTH);

        sPanel.add(box);
        box.addActionListener(l -> {
            load((LotteryPrizeModel) box.getSelectedItem());
        });

        withdrawButton.addActionListener(this::withdraw);
        withdrawButton.setEnabled(false);
        sPanel.add(withdrawButton);

        nextButton.addActionListener(this::next);
        sPanel.add(nextButton);

        LotteryPreferences.INSTANCE.addListener(this::loadPreferences);
        loadPreferences(null);

        RandomSelector.INSTANCE.addListener(this::reloadCandidateList);
    }

    public void loadPreferences(PreferenceChangeEvent e) {
        title.setText(LotteryPreferences.INSTANCE.getTitle());
        title.setFont(title.getFont().deriveFont(LotteryPreferences.INSTANCE.getTitleSize()));
        title.setForeground(LotteryPreferences.INSTANCE.getTitleColor());

        subtitle.setText(LotteryPreferences.INSTANCE.getSubtitle());
        subtitle.setFont(subtitle.getFont().deriveFont(LotteryPreferences.INSTANCE.getSubtitleSize()));
        subtitle.setForeground(LotteryPreferences.INSTANCE.getSubtitleColor());
        subtitle.setVisible(!subtitle.getText().isEmpty());

        winnerLabel.setFont(winnerLabel.getFont().deriveFont(Font.BOLD, LotteryPreferences.INSTANCE.getWinnerSize()));
        winnerLabel.setForeground(LotteryPreferences.INSTANCE.getWinnerColor());

        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext());

        box.removeAllItems();
        for (LotteryPrizeModel model : LotteryPreferences.INSTANCE.getPrizes()) {
            box.addItem(model);
        }
    }

    private String winner = "";
    private LotteryPrizeModel prizeModel;
    private int total, left;

    public void load(LotteryPrizeModel model) {
        prizeModel = model;
        total = model.getCount();
        left = total;
        winner = "";
        reloadCandidateList(null);
    }

    public void reloadCandidateList(ChangeEvent ignored) {
        stateDidChange(false);
    }

    public void next(ActionEvent ignored) {
        left--;
        winner = RandomSelector.INSTANCE.next();
        winnerLabel.setText("恭喜： " + winner);
        stateDidChange(true);
    }

    public void withdraw(ActionEvent ignored) {
        left++;
        RandomSelector.INSTANCE.add(winner);
        winnerLabel.setText(winner + " 放弃领奖");
        stateDidChange(false);
    }

    private void stateDidChange(boolean isWithdrawButtonEnabled) {
        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext() && left > 0);
        withdrawButton.setEnabled(isWithdrawButtonEnabled);
    }
}
