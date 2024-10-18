package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 */
public class IncomeAndExpense extends JPanel {

    private JTabbedPane tabbedPane;
    private InThisMonth inThisMonth;
    private PerYear perYear;
    private PerMonthOfYear perMonthOfYear;
    private DateToDate dateToDate;
    private Color BackgroundColor = new Color(240, 247, 250);

    public IncomeAndExpense(StatisticBUS sttBUS) {
        initComponent(sttBUS);
    }

    public void initComponent(StatisticBUS sttBUS) {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        inThisMonth = new InThisMonth(sttBUS);
        perYear = new PerYear(sttBUS);
        perMonthOfYear = new PerMonthOfYear(sttBUS);
        dateToDate = new DateToDate(sttBUS);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.addTab("Thống kê theo năm", perYear);
        tabbedPane.addTab("Thống kê từng tháng trong năm", perMonthOfYear);
        tabbedPane.addTab("Thống kê từng ngày trong tháng", inThisMonth);
        tabbedPane.addTab("Thống kê từ ngày đến ngày", dateToDate);

        this.add(tabbedPane);
    }
}
