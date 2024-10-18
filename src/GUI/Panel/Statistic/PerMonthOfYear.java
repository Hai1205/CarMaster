package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByMonthOfYearDTO;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Component.Chart.BarChart.Chart;
import GUI.Component.Chart.BarChart.ModelChart;
import com.toedter.calendar.JYearChooser;
import helper.Formater;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
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
public final class PerMonthOfYear extends JPanel implements ActionListener{

    private PanelBorderRadius pnlChart;
    private JPanel pnl_top;
    private StatisticBUS sttBUS;
    private JYearChooser yearchooser;
    private Chart chart;
    private JButton export;
    private JTable sttTable;
    private JScrollPane scrollSttTable;
    private DefaultTableModel tblModel;

    public PerMonthOfYear(StatisticBUS sttBUS) {
        this.sttBUS = sttBUS;
        initComponent();
        loadPerMonthOfYear(yearchooser.getYear());
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout());
        JLabel lblChonNam = new JLabel("Chọn năm");
        yearchooser = new JYearChooser();
        yearchooser.addPropertyChangeListener("year", (PropertyChangeEvent e) -> {
            int year = (Integer) e.getNewValue();
            loadPerMonthOfYear(year);
        });

        export = new JButton("Xuất Excel");
        export.addActionListener(this);
        pnl_top.add(lblChonNam);
        pnl_top.add(yearchooser);
        pnl_top.add(export);

        pnlChart = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(pnlChart, BoxLayout.Y_AXIS);
        pnlChart.setLayout(boxly);
        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        pnlChart.add(chart);
        sttTable = new JTable();
        scrollSttTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Tháng", "Chi phí", "Doanh thu", "Lợi nhuận"};
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

        TableSorter.configureTableColumnSorter(sttTable, 0, TableSorter.STRING_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 1, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 2, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 3, TableSorter.VND_CURRENCY_COMPARATOR);

        this.add(pnl_top, BorderLayout.NORTH);
        this.add(pnlChart, BorderLayout.CENTER);
        this.add(scrollSttTable, BorderLayout.SOUTH);
    }

    public void loadPerMonthOfYear(int year) {
        ArrayList<ByMonthOfYearDTO> list = sttBUS.getByMonthOfYear(year);
        pnlChart.remove(chart);
        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        for (int i = 0; i < list.size(); i++) {
            chart.addData(new ModelChart("Tháng " + (i + 1), new double[]{list.get(i).getExpense(), list.get(i).getIncome(), list.get(i).getProfits()}));
        }
        chart.repaint();
        chart.validate();
        pnlChart.add(chart);
        pnlChart.repaint();
        pnlChart.validate();
        tblModel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            tblModel.addRow(new Object[]{
                "Tháng " + (i + 1), Formater.FormatVND(list.get(i).getExpense()), Formater.FormatVND(list.get(i).getIncome()), Formater.FormatVND(list.get(i).getProfits())
            });
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == export) {
            try {
                JTableExporter.exportJTableToExcel(sttTable);
            } catch (IOException ex) {
                Logger.getLogger(PerMonthOfYear.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
