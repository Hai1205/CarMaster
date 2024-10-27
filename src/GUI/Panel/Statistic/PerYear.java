package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByIncomeAndExpenseDTO;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Component.Chart.BarChart.Chart;
import GUI.Component.Chart.BarChart.ModelChart;
import helper.Formater;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Tran Nhat Sinh
 */
public final class PerYear extends JPanel implements ActionListener {

    private PanelBorderRadius pnlChart;
    private JPanel pnl_top;
    private StatisticBUS sttBUS;
    private JTextField beginYear, endYear;
    private Chart chart;
    private JButton btnreset, btnStatistic, btnexport;

    private JTable sttTable;
    private JScrollPane scrollSttTable;
    private DefaultTableModel tblModel;
    private ArrayList<ByIncomeAndExpenseDTO> dataset;
    private int currentYear;

    public PerYear(StatisticBUS sttBUS) {
        this.sttBUS = sttBUS;
        this.currentYear = LocalDate.now().getYear();
        this.dataset = this.sttBUS.getIncomeAndExpensePerYear(currentYear - 5, currentYear);
        initComponent();
        loadDataIntoTable(dataset);
    }

    public void loadDataIntoTable(ArrayList<ByIncomeAndExpenseDTO> list) {
        tblModel.setRowCount(0);
        for (ByIncomeAndExpenseDTO i : dataset) {
            tblModel.addRow(new Object[] {
                    i.getDate(), Formater.FormatVND(i.getExpense()), Formater.FormatVND(i.getIncome()),
                    Formater.FormatVND(i.getProfits())
            });
        }
    }

    public void loadDataChart(ArrayList<ByIncomeAndExpenseDTO> list) {
        pnlChart.removeAll();
        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        for (ByIncomeAndExpenseDTO i : dataset) {
            chart.addData(new ModelChart("Năm " + i.getDate(),
                    new double[] { i.getExpense(), i.getIncome(), i.getProfits() }));
        }
        chart.repaint();
        chart.validate();
        pnlChart.add(chart);
        pnlChart.repaint();
        pnlChart.validate();
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout());
        JLabel lblChonNamBatDau, lblChonNamKetThuc;
        lblChonNamBatDau = new JLabel("Từ năm");
        lblChonNamKetThuc = new JLabel("Đến năm");

        beginYear = new JTextField("");
        endYear = new JTextField("");

        PlainDocument doc_start = (PlainDocument) beginYear.getDocument();
        doc_start.setDocumentFilter(new NumericDocumentFilter());
        PlainDocument doc_end = (PlainDocument) endYear.getDocument();
        doc_end.setDocumentFilter(new NumericDocumentFilter());

        btnStatistic = new JButton("Thống kê");
        btnreset = new JButton("Làm mới");
        btnexport = new JButton("Xuất excel");
        btnStatistic.addActionListener(this);
        btnreset.addActionListener(this);
        btnexport.addActionListener(this);

        pnl_top.add(lblChonNamBatDau);
        pnl_top.add(beginYear);
        pnl_top.add(lblChonNamKetThuc);
        pnl_top.add(endYear);
        pnl_top.add(btnStatistic);
        pnl_top.add(btnreset);
        pnl_top.add(btnexport);

        pnlChart = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(pnlChart, BoxLayout.Y_AXIS);
        pnlChart.setLayout(boxly);

        loadDataChart(dataset);

        sttTable = new JTable();
        scrollSttTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Năm", "Vốn", "Doanh thu", "Lợi nhuận" };
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

        TableSorter.configureTableColumnSorter(sttTable, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 1, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 2, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(sttTable, 3, TableSorter.VND_CURRENCY_COMPARATOR);

        this.add(pnl_top, BorderLayout.NORTH);
        this.add(pnlChart, BorderLayout.CENTER);
        this.add(scrollSttTable, BorderLayout.SOUTH);

        this.add(pnl_top, BorderLayout.NORTH);
        this.add(pnlChart, BorderLayout.CENTER);
    }

    private void statistic() {
        String beginYearTxt = beginYear.getText();
        String endYearTxt = endYear.getText();
        
        if (beginYearTxt.isEmpty() || endYearTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ năm bắt đầu và năm kết thúc!");
            return;
        } 

        try {
            int begin = Integer.parseInt(beginYearTxt);
            int end = Integer.parseInt(endYearTxt);

            if (begin > currentYear || end > currentYear) {
                JOptionPane.showMessageDialog(this, "Năm không được lớn hơn năm hiện tại");
                return;
            } else if (end < begin || end <= 2018 || begin <= 2018) {
                JOptionPane.showMessageDialog(this, "Năm kết thúc không được bé hơn năm bắt đầu và phải lớn hơn 2018");
                endYear.setText("2018");
                return;
            } else if(end - begin > 10){
                JOptionPane.showMessageDialog(this, "Khoảng cách tối đa giữa năm bắt đầu và kết thúc là 10 năm");
                return;
            }

            this.dataset = this.sttBUS.getIncomeAndExpensePerYear(begin, end);
            loadDataChart(dataset);
            loadDataIntoTable(dataset);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho năm!");
        }
    }

    private void reset() {
        beginYear.setText("");
        endYear.setText("");
        this.dataset = this.sttBUS.getIncomeAndExpensePerYear(currentYear - 5, currentYear);
        loadDataChart(dataset);
        loadDataIntoTable(dataset);
    }

    private void export() {
        try {
            JTableExporter.exportJTableToExcel(sttTable);
        } catch (IOException ex) {
            Logger.getLogger(PerYear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnStatistic) {
            statistic();
        } else if (source == btnreset) {
            reset();
        } else if (source == btnexport) {
            export();
        }
    }
}
