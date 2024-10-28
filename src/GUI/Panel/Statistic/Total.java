package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByPerDateInMonthDTO;
import GUI.Component.TableSorter;
import GUI.Component.itemTaskbar;
import GUI.Component.Chart.CurveChart.CurveChart;
import GUI.Component.Chart.CurveChart.ModelChart2;
import helper.Formater;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tran Nhat Sinh
 */
public class Total extends JPanel {

    private JPanel jp_top, jp_center, pnlChart;
    private itemTaskbar[] listitem;
    private CurveChart chart;
    private JTable statisticTable;
    private JScrollPane scrollStatisticTable;
    private DefaultTableModel tblModel;
    private ArrayList<ByPerDateInMonthDTO> dataset;
    private String[][] getStr = {
            { "Sản phẩm hiện có trong kho", "car.svg", "" },
            { "Khách từ trước đến nay", "stafff.svg", "" },
            { "Nhân viên còn hoạt động", "customerr.svg", "" } };

    public Total(StatisticBUS sttBUS) {
        getStr[0][2] = sttBUS.getProductQuantity() + "";
        getStr[1][2] = sttBUS.getCustomerQuantity() + "";
        getStr[2][2] = sttBUS.getEmployeeQuantity() + "";
        dataset = sttBUS.getLast7Days();
        initComponent();
        loadDataIntoTable(dataset);
    }

    public void loadDataChart() {
        for (ByPerDateInMonthDTO i : dataset) {
            chart.addData(new ModelChart2(i.getDate() + "",
                    new double[] { i.getExpense(), i.getIncome(), i.getProfits() }));
        }
    }

    public void loadDataIntoTable(ArrayList<ByPerDateInMonthDTO> list) {
        Collections.reverse(list);
        tblModel.setRowCount(0);
        for (ByPerDateInMonthDTO i : list) {
            tblModel.addRow(new Object[] {
                    i.getDate(), Formater.FormatVND(i.getExpense()), Formater.FormatVND(i.getIncome()),
                    Formater.FormatVND(i.getProfits())
            });
        }
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        jp_top = new JPanel();
        jp_top.setLayout(new GridLayout(1, 3, 20, 0));
        jp_top.setOpaque(false);
        jp_top.setBorder(new EmptyBorder(10, 10, 10, 10));
        jp_top.setPreferredSize(new Dimension(0, 120));
        listitem = new itemTaskbar[getStr.length];
        for (int i = 0; i < getStr.length; i++) {
            listitem[i] = new itemTaskbar(getStr[i][1], getStr[i][2], getStr[i][0], 0);
            jp_top.add(listitem[i]);
        }

        jp_center = new JPanel(new BorderLayout());
        jp_center.setBackground(Color.WHITE);
        JPanel jp_center_top = new JPanel(new FlowLayout());
        jp_center_top.setBorder(new EmptyBorder(10, 0, 0, 10));
        jp_center_top.setOpaque(false);
        JLabel txtChartName = new JLabel("Doanh thu 7 ngày gần nhất");
        txtChartName.putClientProperty("FlatLaf.style", "font: 150% $medium.font");
        jp_center_top.add(txtChartName);

        pnlChart = new JPanel();
        pnlChart.setOpaque(false);
        pnlChart.setLayout(new BorderLayout(0, 0));
        chart = new CurveChart();
        chart.addLegend("Vốn", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Doanh thu", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(211, 84, 0), new Color(230, 126, 34));

        loadDataChart();

        chart.start();
        pnlChart.add(chart, BorderLayout.CENTER);

        jp_center.add(jp_center_top, BorderLayout.NORTH);
        jp_center.add(pnlChart, BorderLayout.CENTER);

        statisticTable = new JTable();
        scrollStatisticTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Ngày", "Vốn", "Doanh thu", "Lợi nhuận" };
        tblModel.setColumnIdentifiers(header);
        statisticTable.setModel(tblModel);
        statisticTable.setAutoCreateRowSorter(true);
        statisticTable.setDefaultEditor(Object.class, null);
        scrollStatisticTable.setViewportView(statisticTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        statisticTable.setDefaultRenderer(Object.class, centerRenderer);
        statisticTable.setFocusable(false);
        scrollStatisticTable.setPreferredSize(new Dimension(0, 250));
        statisticTable.setRowHeight(40);

        TableSorter.configureTableColumnSorter(statisticTable, 0, TableSorter.DATE_COMPARATOR);
        TableSorter.configureTableColumnSorter(statisticTable, 1, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(statisticTable, 2, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(statisticTable, 3, TableSorter.VND_CURRENCY_COMPARATOR);

        this.add(jp_top, BorderLayout.NORTH);
        this.add(jp_center, BorderLayout.CENTER);
        this.add(scrollStatisticTable, BorderLayout.SOUTH);
    }
}
