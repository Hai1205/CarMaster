/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.Statistic;

import BUS.StatisticBUS;
import DTO.Statistic.ByCustomerDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import helper.Formater;
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
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author andin
 */
public class ByCustomer extends JPanel implements ActionListener, KeyListener, PropertyChangeListener {

    PanelBorderRadius importLeft, importCenter;
    JTable tblKH;
    JScrollPane scrollStockTable;
    DefaultTableModel tblModel;
    InputForm customerName;
    InputDate beginDate, endDate;
    ButtonCustom export, reset;
    StatisticBUS sttBUS;
    ArrayList<ByCustomerDTO> list;

    public ByCustomer(StatisticBUS sttBUS) {
        this.sttBUS = sttBUS;
        list = sttBUS.getAllCustomer();
        initComponent();
        loadDataTable(list);
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

        customerName = new InputForm("Tìm kiếm khách hàng");
        customerName.getTxtForm().putClientProperty("JTextField.showClearButton", true);
        customerName.getTxtForm().addKeyListener(this);
        beginDate = new InputDate("Từ ngày");
        endDate = new InputDate("Đến ngày");
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

        left_content.add(customerName);
        left_content.add(beginDate);
        left_content.add(endDate);
        left_content.add(btn_layout);

        importCenter = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(importCenter, BoxLayout.Y_AXIS);
        importCenter.setLayout(boxly);

        tblKH = new JTable();
        scrollStockTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã khách hàng", "Tên khách hàng", "Số lượng hóa đơn", "Tổng tiền"};
        tblModel.setColumnIdentifiers(header);
        tblKH.setModel(tblModel);
        tblKH.setAutoCreateRowSorter(true);
        tblKH.setDefaultEditor(Object.class, null);
        scrollStockTable.setViewportView(tblKH);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKH.setDefaultRenderer(Object.class, centerRenderer);
        tblKH.setFocusable(false);
        tblKH.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblKH.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblKH.getColumnModel().getColumn(3).setPreferredWidth(50);
        
        TableSorter.configureTableColumnSorter(tblKH, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 2, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 3, TableSorter.VND_CURRENCY_COMPARATOR);
        
        importCenter.add(scrollStockTable);

        this.add(importLeft, BorderLayout.WEST);
        this.add(importCenter, BorderLayout.CENTER);
    }

    public boolean validateSelectDate() throws ParseException {
        java.util.Date time_start = beginDate.getDate();
        java.util.Date time_end = endDate.getDate();

        java.util.Date current_date = new java.util.Date();
        if (time_start != null && time_start.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            beginDate.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            endDate.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            endDate.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    public void Fillter() throws ParseException {
        if (validateSelectDate()) {
            String input = customerName.getText() != null ? customerName.getText() : "";
            java.util.Date time_start = beginDate.getDate() != null ? beginDate.getDate() : new java.util.Date(0);
            java.util.Date time_end = endDate.getDate() != null ? endDate.getDate() : new java.util.Date(System.currentTimeMillis());
            this.list = sttBUS.FilterCustomer(input, new Date(time_start.getTime()), new Date(time_end.getTime()));
            loadDataTable(list);
        }
    }

    public void loadDataTable(ArrayList<ByCustomerDTO> result) {
        tblModel.setRowCount(0);
        for (ByCustomerDTO i : result) {
            tblModel.addRow(new Object[]{
                i.getCustomerID(), i.getCustomerName(), i.getInvoiceQuantity(), Formater.FormatVND(i.getTotalCost())
            });
        }
    }

    public void resetForm() throws ParseException {
        customerName.setText("");
        beginDate.getDateChooser().setCalendar(null);
        endDate.getDateChooser().setCalendar(null);
        Fillter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == export) {
            try {
                JTableExporter.exportJTableToExcel(tblKH);
            } catch (IOException ex) {
                Logger.getLogger(ByCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (source == reset) {
            try {
                resetForm();
            } catch (ParseException ex) {
                Logger.getLogger(ByCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ByCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ByCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
