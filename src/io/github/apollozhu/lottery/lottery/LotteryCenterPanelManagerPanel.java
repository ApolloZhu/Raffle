package io.github.apollozhu.lottery.lottery;

import io.github.apollozhu.lottery.prize.LotteryPrizeDisplayPanel;
import io.github.apollozhu.lottery.prize.LotteryPrizeModel;

import javax.swing.*;
import java.awt.*;

public class LotteryCenterPanelManagerPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();

    private LotteryCenterPanel placeholder = new LotteryCenterPanel();
    private LotteryRollingPanel roller = new LotteryRollingPanel();
    private LotteryPrizeDisplayPanel winnerDisplay = new LotteryPrizeDisplayPanel();
    private LotteryCenterPanel center;

    public LotteryCenterPanelManagerPanel() {
        setLayout(cardLayout);
        add(placeholder, "placeholder");
        add(roller, "roller");
        add(winnerDisplay, "winnerDisplay");
    }

    public void showPlaceHodler() {
        cardLayout.show(this, "placeholder");
    }

    public void showRoller() {
        cardLayout.show(this, "roller");
    }

    public void showFor(String winner, LotteryPrizeModel prizeModel) {
        winnerDisplay.displayFor(winner, prizeModel);
        cardLayout.show(this, "winnerDisplay");
    }
}
