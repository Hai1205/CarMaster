package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 */
public final class Statistic extends JPanel {

    private JTabbedPane tabbedPane;
    private JPanel supplier, customer;
    private IncomeAndExpense incomeAndExpense;
    // private Stock stock;
    private Color BackgroundColor = new Color(240, 247, 250);
    private StatisticBUS sttBUS;

    public Statistic() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        sttBUS = new StatisticBUS();
        // total = new Total(sttBUS);
        // stock = new Stock(sttBUS);
        incomeAndExpense = new IncomeAndExpense(sttBUS);
        customer = new ByCustomer(sttBUS);
        supplier = new BySupplier(sttBUS);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        // tabbedPane.addTab("Tổng quan", total);
        // tabbedPane.addTab("Tồn kho", stock);
        tabbedPane.addTab("Doanh thu", incomeAndExpense);
        tabbedPane.addTab("Khách hàng", customer);
        tabbedPane.addTab("Nhà cung cấp", supplier);

        this.add(tabbedPane);
    }
}
