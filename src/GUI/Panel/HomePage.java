package GUI.Panel;

import BUS.StatisticBUS;
import GUI.Panel.Statistic.Total;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 */
public final class HomePage extends JPanel {

    private JPanel total;
    private Color BackgroundColor = new Color(240, 247, 250);
    private StatisticBUS sttBUS;

    public HomePage() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        sttBUS = new StatisticBUS();
        total = new Total(sttBUS);

        this.add(total);
    }
}
