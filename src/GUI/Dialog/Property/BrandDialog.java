/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog.Property;

import BUS.Property.BrandBUS;
import BUS.PermissionBUS;
import DTO.Property.BrandDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.Tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ASUS
 */
public class BrandDialog extends JDialog implements MouseListener {

    private HeaderTitle headTite;
    private JPanel top, main, bottom;
    private InputForm txtBrandName;
    private DefaultTableModel tblModel;
    private JTable table;
    private JScrollPane scrollTable;
    private ButtonCustom add, update;
    private BrandBUS bBUS;
    private ArrayList<BrandDTO> list;
    private PermissionBUS pmsBUS;

    public BrandDialog(JFrame owner, String title, boolean modal, String permissionID) {
        super(owner, title, modal);
        initComponent();
        checkPermisson(permissionID);
        loadDataIntoTable(list);
        this.setVisible(true);
    }

    public void checkPermisson(String permissionID) {
        if (!pmsBUS.checkPermisson(permissionID, "FT000008", "create")) {
            add.setVisible(false);
        }
        if (!pmsBUS.checkPermisson(permissionID, "FT000008", "update")) {
            update.setVisible(false);
        }
    }

    public void initComponent() {
        pmsBUS = new PermissionBUS();
        bBUS = new BrandBUS();
        list = bBUS.getList();
        this.setSize(new Dimension(425, 500));
        this.setLayout(new BorderLayout(0, 0));
        this.setResizable(false);
        headTite = new HeaderTitle("QUẢN LÝ HÃNG XE");
        this.setBackground(Color.white);
        top = new JPanel();
        main = new JPanel();
        bottom = new JPanel();

        top.setLayout(new GridLayout(1, 1));
        top.setBackground(Color.WHITE);
        top.setPreferredSize(new Dimension(0, 70));
        top.add(headTite);

        main.setBackground(Color.WHITE);
        main.setPreferredSize(new Dimension(420, 200));
        txtBrandName = new InputForm("Tên hãng xe");
        txtBrandName.setPreferredSize(new Dimension(250, 70));
        table = new JTable();
        table.setDefaultEditor(Object.class, null);
        table.setBackground(Color.WHITE);
        table.addMouseListener(this);
        table.setAutoCreateRowSorter(true);
        scrollTable = new JScrollPane(table);
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã hãng xe", "Tên hãng xe"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        scrollTable.setViewportView(table);
        scrollTable.setPreferredSize(new Dimension(420, 250));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        main.add(txtBrandName);
        main.add(scrollTable);

        add = new ButtonCustom("Thêm", "excel", 15, 100, 40);
        add.addMouseListener(this);
        update = new ButtonCustom("Sửa", "success", 15, 100, 40);
        update.addMouseListener(this);
        bottom.setBackground(Color.white);
        bottom.setLayout(new FlowLayout(1, 20, 20));
        bottom.add(add);
        bottom.add(update);
        bottom.setPreferredSize(new Dimension(0, 70));

        this.add(top, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
    }

    private void loadDataIntoTable(ArrayList<BrandDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (BrandDTO bDTO : result) {
            tblModel.addRow(new Object[]{
                bDTO.getBrandID(), bDTO.getBrandName()
            });
        }
    }

    private void loadNewDataIntoTable() {
        list = bBUS.getList();
        loadDataIntoTable(list);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == add) {
            if (Tool.isEmpty(txtBrandName.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hãng xe mới!");
            } else {
                String brandID = "B" + Tool.randomID();
                String brandName = txtBrandName.getText();
                if (bBUS.checkBrandName(brandName) == null) {
                    bBUS.add(new BrandDTO(brandID, brandName));
                    loadNewDataIntoTable();
                    txtBrandName.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Tên hãng xe này đã tồn tại!");
                }
            }
        } else if (e.getSource() == update) {
            int index = getRowSelected();
            if (index != -1) {
                if (Tool.isEmpty(txtBrandName.getText())) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hãng xe!");
                } else {
                    String brandName = txtBrandName.getText();
                    if (bBUS.checkBrandName(brandName) == null) {
                        bBUS.update(new BrandDTO(list.get(index).getBrandName(), brandName));
                        loadNewDataIntoTable();
                        txtBrandName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Tên hãng xe này đã tồn tại!");
                    }
                }
            }
        } else if (e.getSource() == table) {
            int index = table.getSelectedRow();
            txtBrandName.setText(""+list.get(index).getBrandName());
        }
    }

    public int getRowSelected() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hãng xe!");
        }
        return index;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
