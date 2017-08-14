package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.settings.LotteryPreferences;
import io.github.apollozhu.lottery.utils.PreferenceLoading;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.PreferenceChangeEvent;

public class LotteryCenterPanel extends JPanel implements PreferenceLoading {
    protected JLabel label = new JLabel("", SwingConstants.CENTER);

    protected void addLabel() {
        add(label);
    }

    public LotteryCenterPanel() {
        addLabel();
        LotteryPreferences.INSTANCE.addListener(this::loadPreferences);
        loadPreferences();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) loadPreferences();
    }

    public void loadPreferences(PreferenceChangeEvent ignored) {
        label.setFont(label.getFont().deriveFont(Font.BOLD, LotteryPreferences.INSTANCE.getWinnerSize()));
        label.setForeground(LotteryPreferences.INSTANCE.getWinnerColor());
        setBackground(LotteryPreferences.INSTANCE.getBackgroundColor());
    }
}
