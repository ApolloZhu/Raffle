package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.utils.RandomSelector;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LotteryRollingPanel extends LotteryCenterPanel {
    public LotteryRollingPanel() {
        Executors.newScheduledThreadPool(3).schedule(() -> {
            label.setText(RandomSelector.INSTANCE.getList().get(RandomSelector.INSTANCE.nextIndex()));
            System.out.println(RandomSelector.INSTANCE.nextIndex());
        }, 1, TimeUnit.MILLISECONDS);
    }
}
