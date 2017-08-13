package io.github.apollozhu.lottery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.prefs.PreferenceChangeEvent;

public class LotteryPanel extends JPanel {

    private JLabel title = new JLabel("", SwingConstants.CENTER);
    private JLabel subtitle = new JLabel("", SwingConstants.CENTER);
    private JButton withdrawButton = new JButton("放弃");
    private JButton nextButton = new JButton("抽奖");

    public LotteryPanel() {
        setLayout(new BorderLayout());

        JPanel nPanel = new JPanel();
        add(nPanel, BorderLayout.NORTH);
        nPanel.setLayout(new GridLayout(2, 1));
        nPanel.add(title);
        nPanel.add(subtitle);

        JPanel sPanel = new JPanel();
        add(sPanel, BorderLayout.SOUTH);

        withdrawButton.addActionListener(this::withdraw);
        withdrawButton.setEnabled(false);
        sPanel.add(withdrawButton);

        nextButton.addActionListener(this::next);
        sPanel.add(nextButton);

        LotteryPreferences.INSTANCE.addListener(this::loadPreferences);
        loadPreferences(null);
    }

    public void loadPreferences(PreferenceChangeEvent e) {
        title.setText(LotteryPreferences.INSTANCE.getTitle());
        title.setFont(title.getFont().deriveFont(LotteryPreferences.INSTANCE.getTitleSize()));
        title.setForeground(LotteryPreferences.INSTANCE.getTitleColor());

        subtitle.setText(LotteryPreferences.INSTANCE.getSubtitle());
        subtitle.setFont(subtitle.getFont().deriveFont(LotteryPreferences.INSTANCE.getSubtitleSize()));
        subtitle.setForeground(LotteryPreferences.INSTANCE.getSubtitleColor());
        subtitle.setVisible(!subtitle.getText().isEmpty());

        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext());
    }

    public void exit(ActionEvent ignored) {
        // TODO: Save Changes
        System.exit(0);
    }

    private String winner = "";

    public void next(ActionEvent ignored) {
        winner = RandomSelector.INSTANCE.next();
        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext());
        withdrawButton.setEnabled(true);
    }

    public void withdraw(ActionEvent ignored) {
        RandomSelector.INSTANCE.add(winner);
        nextButton.setEnabled(RandomSelector.INSTANCE.hasNext());
        withdrawButton.setEnabled(false);
    }
}
