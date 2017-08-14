package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.utils.RandomSelector;

import javax.swing.*;

public class LotteryRollingPanel extends LotteryCenterPanel {
    private Timer timer = new Timer(30, ignored -> {
        label.setText(RandomSelector.INSTANCE.getList().get(RandomSelector.INSTANCE.nextIndex()));
        System.out.println(RandomSelector.INSTANCE.nextIndex());
    });

    public LotteryRollingPanel() {
        timer.start();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}
