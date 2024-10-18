/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.PermissionBUS;
import DTO.PermissionDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.PermissionDialog;
import GUI.Main;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ASUS
 */
public class PermissionPanel extends JPanel implements ActionListener {

    private final JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private PanelBorderRadius main, functionBar;
    private JPanel contentCenter;
    private JTable tblpms;
    private JScrollPane scrollTable;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private final Main m;
    private PermissionBUS pmsBUS;
    private ArrayList<PermissionDTO> pmsList;
    private final Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        pmsBUS = new PermissionBUS();
        pmsList = pmsBUS.getPmsList();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 20));
        this.add(contentCenter);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = {"create", "update", "import", "export"};
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000005", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch();
        search.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String info = search.getTxtSearchForm().getText();
                ArrayList<PermissionDTO> rs = pmsBUS.search(info);
                loadDataIntoTalbe(rs);
            }
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        tblpms = new JTable();
        tblpms.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã nhóm quyền", "Tên nhóm quyền", "Giới hạn"};
        tblModel.setColumnIdentifiers(header);
        tblpms.setModel(tblModel);
        scrollTable.setViewportView(tblpms);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tblpms.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setPreferredWidth(300);
        tblpms.setFocusable(false);
        main.add(scrollTable);

        search.getBtnReset().addActionListener((ActionEvent e) -> {
            search.setTxtSearchForm("");
            loadNewDataIntoTable();
        });
    }

    public PermissionPanel(Main m) {
        this.m = m;
        initComponent();
        loadDataIntoTalbe(pmsList);
    }

    private void loadDataIntoTalbe(ArrayList<PermissionDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (PermissionDTO pmsDTO : result) {
            tblModel.addRow(new Object[]{
                pmsDTO.getPermissionID(), pmsDTO.getPermissionName(), pmsDTO.getSlot()
            });
        }
    }

    private void loadNewDataIntoTable() {
        pmsList = pmsBUS.getPmsList();
        loadDataIntoTalbe(pmsList);
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new PermissionDialog(pmsBUS, owner, "Thêm nhóm quyền", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = this.getRowSelected();
            if (index >= 0) {
                new PermissionDialog(pmsBUS, owner, "Chỉnh sửa nhóm quyền", true, "update", pmsList.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tblpms);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        loadNewDataIntoTable();
    }

    public int getRowSelected() {
        int index = tblpms.getSelectedRow();
        if (tblpms.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhóm quyền");
            return -1;
        }
        return index;
    }
}
