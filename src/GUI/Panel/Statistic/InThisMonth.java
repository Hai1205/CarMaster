package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByPerDateInMonthDTO;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Component.Chart.BarChart.Chart;
import GUI.Component.Chart.BarChart.ModelChart;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import helper.Formater;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public final class InThisMonth extends JPanel{

    private PanelBorderRadius pnlChart;
    private JPanel pnl_top;
    private StatisticBUS sttBUS;
    private JMonthChooser monthchooser;
    private Chart chart;
    private JTable sttTable;
    private JScrollPane scrollSttTable;
    private DefaultTableModel tblModel;
    private JYearChooser yearchooser;
    private JButton btnStt, btnExport;

    public InThisMonth(StatisticBUS sttBUS) {
        this.sttBUS = sttBUS;
        initComponent();
        int month = monthchooser.getMonth() + 1;
        int year = yearchooser.getYear();
        loadByPerDateInMonth(month, year);
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout());
        JLabel lblMonth = new JLabel("Chọn tháng");
        monthchooser = new JMonthChooser();
        JLabel lblYear = new JLabel("Chọn năm");
        yearchooser = new JYearChooser();
        pnl_top.add(lblMonth);
        pnl_top.add(monthchooser);
        pnl_top.add(lblYear);
        pnl_top.add(yearchooser);
        btnStt = new JButton("Thống kê");
        pnl_top.add(btnStt);
        btnExport = new JButton("Xuất Excel");
        pnl_top.add(btnExport);

        btnExport.addActionListener((ActionEvent e) -> {
            try {
                JTableExporter.exportJTableToExcel(sttTable);
            } catch (IOException ex) {
                Logger.getLogger(InThisMonth.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnStt.addActionListener((ActionEvent e) -> {
            int month = monthchooser.getMonth() + 1;
            int year = yearchooser.getYear();
            loadByPerDateInMonth(month, year);
        });

        pnlChart = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(pnlChart, BoxLayout.Y_AXIS);
        pnlChart.setLayout(boxly);

        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        chart.addData(new ModelChart("Tháng 1", new double[]{100, 150, 200}));
        chart.addData(new ModelChart("Tháng 2", new double[]{600, 750, 300}));
        chart.addData(new ModelChart("Tháng 3", new double[]{200, 350, 1000}));
        chart.addData(new ModelChart("Tháng 4", new double[]{480, 150, 750}));
        chart.addData(new ModelChart("Tháng 5", new double[]{100, 150, 200}));
        chart.addData(new ModelChart("Tháng 6", new double[]{600, 750, 300}));
        chart.addData(new ModelChart("Tháng 7", new double[]{200, 350, 1000}));
        chart.addData(new ModelChart("Tháng 8", new double[]{480, 150, 750}));
        chart.addData(new ModelChart("Tháng 9", new double[]{100, 150, 200}));
        chart.addData(new ModelChart("Tháng 10", new double[]{600, 750, 300}));
        chart.addData(new ModelChart("Tháng 11", new double[]{200, 350, 1000}));
        chart.addData(new ModelChart("Tháng 12", new double[]{480, 150, 750}));
        pnlChart.add(chart);

        sttTable = new JTable();
        scrollSttTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"};
        tblModel.setColumnIdentifiers(header);
        sttTable.setModel(tblModel);
        sttTable.setAutoCreateRowSorter(true);
        sttTable.setDefaultEditor(Object.class, null);
        scrollSttTable.setViewportView(sttTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        sttTable.setDefaultRenderer(Object.class, centerRenderer);
        sttTable.setFocusable(false);
        scrollSttTable.setPreferredSize(new Dimension(0, 300));
        TableSorter.configureTableColumnSorter(sttTable, 1, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 2, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 3, TableSorter.VND_CURRENCY_COMPARATOR);
        this.add(pnl_top, BorderLayout.NORTH);
        this.add(pnlChart, BorderLayout.CENTER);
        this.add(scrollSttTable, BorderLayout.SOUTH);
    }

    public void loadByPerDateInMonth(int month, int year) {
        ArrayList<ByPerDateInMonthDTO> list = sttBUS.getByPerDateInMonth(month, year);
        pnlChart.remove(chart);
        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        long expenseSum = 0;
        long incomeSum = 0;
        long profitSum = 0;
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            expenseSum += list.get(i).getExpense();
            incomeSum += list.get(i).getIncome();
            profitSum += list.get(i).getProfits();
            if (index % 3 == 0) {
                chart.addData(new ModelChart("Ngày " + (index - 2) + "->" + (index), new double[]{expenseSum, incomeSum, profitSum}));
                expenseSum = 0;
                incomeSum = 0;
                profitSum = 0;
            }
        }
        chart.repaint();
        chart.validate();
        pnlChart.add(chart);
        pnlChart.repaint();
        pnlChart.validate();
        tblModel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            tblModel.addRow(new Object[]{
                list.get(i).getDate(), Formater.FormatVND(list.get(i).getExpense()), Formater.FormatVND(list.get(i).getIncome()), Formater.FormatVND(list.get(i).getProfits())
            });
        }
    }
}
