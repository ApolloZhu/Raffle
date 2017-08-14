package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.settings.LotteryPreferences;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.PreferenceChangeEvent;

public class LotteryCenterPanel extends JPanel {

    protected Object constraintForLabel() {
        return null;
    }

    protected JLabel label = new JLabel();

    public LotteryCenterPanel() {
        add(label, constraintForLabel());
        LotteryPreferences.INSTANCE.addListener(this::loadPreferences);
        loadPreferences(null);
    }

    private void loadPreferences(PreferenceChangeEvent ignored) {
        label.setFont(label.getFont().deriveFont(Font.BOLD, LotteryPreferences.INSTANCE.getWinnerSize()));
        label.setForeground(LotteryPreferences.INSTANCE.getWinnerColor());
        setBackground(LotteryPreferences.INSTANCE.getBackgroundColor());
    }
}
