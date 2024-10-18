/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByStockDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public final class Stock extends JPanel implements ActionListener, KeyListener, PropertyChangeListener {

    private PanelBorderRadius importLeft, importCenter;
    private JTable stockTable;
    private JScrollPane scrollStockTable;
    private DefaultTableModel tblModel;
    private InputForm search;
    private InputDate beginDate, endDate;
    private ButtonCustom export, reset;
    private HashMap<String, ArrayList<ByStockDTO>> pdList;
    private StatisticBUS sttBUS;

    public Stock(StatisticBUS sttBUS) {
        this.sttBUS = sttBUS;
        pdList = sttBUS.getStock();
        initComponent();
        loadDataTalbe(pdList);
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        importLeft = new PanelBorderRadius();
        importLeft.setPreferredSize(new Dimension(300, 100));
        importLeft.setLayout(new BorderLayout());
        importLeft.setBorder(new EmptyBorder(0, 0, 0, 5));
        JPanel left_content = new JPanel(new GridLayout(4, 1));
        left_content.setPreferredSize(new Dimension(300, 360));
        importLeft.add(left_content, BorderLayout.NORTH);

        search = new InputForm("Tìm kiếm sản phẩm");
        search.getTxtForm().putClientProperty("JTextField.showClearButton", true);
        beginDate = new InputDate("Từ ngày");
        endDate = new InputDate("Đến ngày");

        search.getTxtForm().addKeyListener(this);
        beginDate.getDateChooser().addPropertyChangeListener(this);
        endDate.getDateChooser().addPropertyChangeListener(this);

        JPanel btn_layout = new JPanel(new BorderLayout());
        JPanel btninner = new JPanel(new GridLayout(1, 2));
        btninner.setOpaque(false);
        btn_layout.setPreferredSize(new Dimension(30, 36));
        btn_layout.setBorder(new EmptyBorder(20, 10, 0, 10));
        btn_layout.setBackground(Color.white);
        export = new ButtonCustom("Xuất Excel", "excel", 14);
        reset = new ButtonCustom("Làm mới", "danger", 14);

        export.addActionListener(this);
        reset.addActionListener(this);

        btninner.add(export);
        btninner.add(reset);
        btn_layout.add(btninner, BorderLayout.NORTH);

        left_content.add(search);
        left_content.add(beginDate);
        left_content.add(endDate);
        left_content.add(btn_layout);

        importCenter = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(importCenter, BoxLayout.Y_AXIS);
        importCenter.setLayout(boxly);

        stockTable = new JTable();
        scrollStockTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã sản phẩm", "Tên sản phẩm", "Tồn đầu kỳ", "Nhập trong kỳ", "Xuất trong kỳ", "Tồn cuối kỳ"};
        tblModel.setColumnIdentifiers(header);
        stockTable.setModel(tblModel);
        stockTable.setAutoCreateRowSorter(true);
        stockTable.setDefaultEditor(Object.class, null);
        scrollStockTable.setViewportView(stockTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        stockTable.setDefaultRenderer(Object.class, centerRenderer);
        stockTable.setFocusable(false);
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(100);

        TableSorter.configureTableColumnSorter(stockTable, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(stockTable, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(stockTable, 3, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(stockTable, 4, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(stockTable, 5, TableSorter.INTEGER_COMPARATOR);

        importCenter.add(scrollStockTable);

        this.add(importLeft, BorderLayout.WEST);
        this.add(importCenter, BorderLayout.CENTER);
    }

    public void Fillter() throws ParseException {
        if (validateSelectDate()) {
            String input = search.getText() != null ? search.getText() : "";
            Date begin = beginDate.getDate() != null ? beginDate.getDate() : new Date(0);
            Date end = endDate.getDate() != null ? endDate.getDate() : new Date();
            this.pdList = sttBUS.filterStock(input, begin, end);
            loadDataTalbe(this.pdList);
        }
    }

    public void resetForm() throws ParseException {
        search.setText("");
        beginDate.getDateChooser().setCalendar(null);
        endDate.getDateChooser().setCalendar(null);
        Fillter();
    }

    public boolean validateSelectDate() throws ParseException {
        Date begin = beginDate.getDate();
        Date end = endDate.getDate();

        Date current_date = new Date();
        if (begin != null && begin.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            beginDate.getDateChooser().setCalendar(null);
            return false;
        }
        if (end != null && end.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            endDate.getDateChooser().setCalendar(null);
            return false;
        }
        if (begin != null && end != null && begin.after(end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            endDate.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    private void loadDataTalbe(HashMap<String, ArrayList<ByStockDTO>> list) {
        tblModel.setRowCount(0);
        for (String i : list.keySet()) {
            int[] quantity = sttBUS.getQuantity(list.get(i));
            tblModel.addRow(new Object[]{
                i, list.get(i).get(0).getProductName(), quantity[0], quantity[1], quantity[2], quantity[3]
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == export) {
            try {
                JTableExporter.exportJTableToExcel(stockTable);
            } catch (IOException ex) {
                Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (source == reset) {
            try {
                resetForm();
            } catch (ParseException ex) {
                Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
