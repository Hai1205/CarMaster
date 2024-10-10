/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog.Property;

import BUS.PermissionBUS;
import BUS.Property.ColorBUS;
import DTO.Property.ColorDTO;
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
public class ColorDialog extends JDialog implements MouseListener {
    private HeaderTitle headTite;
    private JPanel top, main, bottom;
    private InputForm txtColorName;
    private DefaultTableModel tblModel;
    private JTable table;
    private JScrollPane scrollTable;
    private ButtonCustom add, update;
    private ColorBUS clBUS;
    private ArrayList<ColorDTO> list;
    private PermissionBUS pmsBUS;

    public ColorDialog(JFrame owner, String title, boolean modal, String permissionID) {
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
        clBUS = new ColorBUS();
        list = clBUS.getList();
        this.setSize(new Dimension(425, 500));
        this.setLayout(new BorderLayout(0, 0));
        this.setResizable(false);
        headTite = new HeaderTitle("QUẢN LÝ MÀU SẮC");
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
        txtColorName = new InputForm("Tên màu sắc");
        txtColorName.setPreferredSize(new Dimension(250, 70));
        table = new JTable();
        table.setBackground(Color.WHITE);
        table.addMouseListener(this);
        table.setAutoCreateRowSorter(true);
        scrollTable = new JScrollPane(table);
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã màu sắc", "Tên màu sắc"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        scrollTable.setViewportView(table);
        scrollTable.setPreferredSize(new Dimension(420, 250));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        main.add(txtColorName);
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

    private void loadDataIntoTable(ArrayList<ColorDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (ColorDTO clDTO : result) {
            tblModel.addRow(new Object[]{
                clDTO.getColorID(), clDTO.getColorName()
            });
        }
    }

    private void loadNewDataIntoTable() {
        list = clBUS.getList();
        loadDataIntoTable(list);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == add) {
            if (Tool.isEmpty(txtColorName.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên màu sắc mới!");
            } else {
                String colorID = "CL" + Tool.randomID();
                String clorName = txtColorName.getText();
                if (clBUS.checkName(clorName) == null) {
                    clBUS.add(new ColorDTO(colorID, clorName));
                    loadNewDataIntoTable();
                    txtColorName.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Tên màu sắc này đã tồn tại!");
                }
            }
        } else if (e.getSource() == update) {
            int index = getRowSelected();
            if (index != -1) {
                if (Tool.isEmpty(txtColorName.getText())) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên màu sắc!");
                } else {
                    String clorName = txtColorName.getText();
                    if (clBUS.checkName(clorName) == null) {
                        clBUS.update(new ColorDTO(list.get(index).getColorID(), clorName));
                        loadNewDataIntoTable();
                        txtColorName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Tên màu sắc này đã tồn tại!");
                    }
                }
            }
        } else if (e.getSource() == table) {
            int index = table.getSelectedRow();
            txtColorName.setText(""+list.get(index).getColorName());
        }
    }

    public int getRowSelected() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại nhiên liệu!");
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
