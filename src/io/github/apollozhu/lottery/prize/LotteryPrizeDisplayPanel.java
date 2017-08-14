package io.github.apollozhu.lottery.prize;

import io.github.apollozhu.lottery.lottery.LotteryCenterPanel;

import javax.swing.*;
import java.awt.*;

public class LotteryPrizeDisplayPanel extends LotteryCenterPanel {

    private JLabel imageLabel;

    @Override
    protected void addLabel() {
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(imageLabel = new JLabel(), BorderLayout.CENTER);
    }

    public void displayFor(String name, LotteryPrizeModel prizeModel) {
        label.setText("恭喜 " + name + " 获得 " + prizeModel.getName());
        imageLabel.setIcon(new ImageIcon(prizeModel.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_DEFAULT)));
    }
}
