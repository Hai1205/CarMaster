/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.EmployeeBUS;
import DTO.EmployeeDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public final class EmployeePanel extends JPanel {

    private JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private EmployeeBUS epBUS;
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable epTable;
    private JScrollPane scrollTable;
    private MainFunction mainFunction;
    public IntegratedSearch search;
    private Main m;
    private ArrayList<EmployeeDTO> epList;
    private final Color BackgroundColor = new Color(240, 247, 250);
    private DefaultTableModel tblModel;

    public EmployeePanel(PanelBorderRadius main) {
        this.main = main;
        initComponent();
    }

    public EmployeePanel(Main m) {
        this.m = m;
        initComponent();
    }

    private void initComponent() {
        epBUS = new EmployeeBUS(this);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở
        // giữa mà contentCenter không bị dính sát vào các thành phần khác
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm
        // kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        String[] action = { "create", "update", "import", "export" };
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000003", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(epBUS);
        }
        functionBar.add(mainFunction);
        search = new IntegratedSearch();
        functionBar.add(search);
        search.getBtnReset().addActionListener(epBUS);
        search.getTxtSearchForm().addKeyListener((KeyListener) new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String info = search.getTxtSearchForm().getText();
                ArrayList<EmployeeDTO> rs = epBUS.search(info);
                epBUS.loadDataIntoTable(rs);
            }
        });

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        epTable = new JTable();
        scrollTable = new JScrollPane();
        epTable = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã nhân viên", "Mã nhóm quyên", "Họ tên", "Email", "Giới tính", "Ngày Sinh",
                "Số điện thoại", "Ngày tuyển", "Lương", "Trạng thái" };

        tblModel.setColumnIdentifiers(header);
        epTable.setModel(tblModel);
        epTable.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        epTable.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        epTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        epTable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        epTable.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(epTable, 8, TableSorter.VND_CURRENCY_COMPARATOR);
        scrollTable.setViewportView(epTable);
        epList = epBUS.getList("");
        epBUS.loadDataIntoTable(epList);
        epTable.setDefaultEditor(Object.class, null);
        main.add(scrollTable);
    }

    public int getRow() {
        return epTable.getSelectedRow();
    }

    public EmployeeDTO getEmployee() {
        epList = epBUS.getList("");
        return epList.get(epTable.getSelectedRow());
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    public JFrame getOwner() {
        return owner;
    }
}
