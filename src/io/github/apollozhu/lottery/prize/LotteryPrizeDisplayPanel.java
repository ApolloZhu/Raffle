package io.github.apollozhu.lottery.prize;

import io.github.apollozhu.lottery.lottery.LotteryCenterPanel;

import javax.swing.*;
import java.awt.*;

public class LotteryPrizeDisplayPanel extends LotteryCenterPanel {
    @Override
    protected Object constraintForLabel() {
        return BorderLayout.NORTH;
    }

    private BorderLayout layout = new BorderLayout();

    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(layout);
    }

    public LotteryPrizeDisplayPanel(String name, LotteryPrizeModel prizeModel) {
        JLabel image = new JLabel(prizeModel.getImageIcon());
        add(image, BorderLayout.CENTER);
    }
}
