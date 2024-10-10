/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.ProductBUS;
import DTO.ProductDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.ProductDialog;
import GUI.Main;
import helper.Formater;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class ProductPanel extends JPanel implements ActionListener {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private final JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private JTable productTable;
    private JScrollPane productScrollTable;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private final Main m;
    private ProductBUS pdBUS;
    private ArrayList<ProductDTO> pdList;
    private final Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        pdBUS = new ProductBUS();
        pdList = pdBUS.getProductList();

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        productTable = new JTable();
        productScrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã sản phẩm", "Tên nhà cung cấp", "Tên sản phẩm", "Số lượng", "Giá gốc",
                "Giá niêm yết",
                "Trạng thái" };
        tblModel.setColumnIdentifiers(header);
        productTable.setModel(tblModel);
        productScrollTable.setViewportView(productTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = productTable.getColumnModel();
        for (int i = 0; i < 7; i++) {
            if (i != 1) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        productTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        productTable.setFocusable(false);
        productTable.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(productTable, 2, TableSorter.INTEGER_COMPARATOR);
        productTable.setDefaultEditor(Object.class, null);
        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "export" };
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000006", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch();
        search.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.getTxtSearchForm().getText();
                pdList = pdBUS.search(txt);
                loadDataIntoTalbe(pdList);
            }

        });

        search.getBtnReset().addActionListener((ActionEvent e) -> {
            search.setTxtSearchForm("");
            pdList = pdBUS.getProductList();
            loadDataIntoTalbe(pdList);
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(productScrollTable);
    }

    public ProductPanel(Main m) {
        this.m = m;
        initComponent();
        loadDataIntoTalbe(pdList);
    }

    public void loadDataIntoTalbe(ArrayList<ProductDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (ProductDTO pdDTO : result) {
            String supplierName = pdBUS.getSupplierNameByID(pdDTO.getSupplierID());
            tblModel.addRow(new Object[] { pdDTO.getProductID(), supplierName, pdDTO.getProductName(), pdDTO.getQuantity(),
                    Formater.FormatVND(pdDTO.getBasicPrice()), Formater.FormatVND(pdDTO.getSellPrice()), pdDTO.getStatus() });
        }
    }

    public void loadNewDataIntoTable() {
        this.pdList = pdBUS.getProductList();
        loadDataIntoTalbe(pdList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new ProductDialog(this, owner, "Thêm sản phẩm mới", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                new ProductDialog(this, owner, "Chỉnh sửa sản phẩm", true, "update",
                        pdList.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(productTable);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getRowSelected() {
        int index = productTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
        }
        return index;
    }

    private void initPadding() {
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
    }
}
