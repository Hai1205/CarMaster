package GUI.Panel;

import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Main;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import BUS.ImportBUS;
import BUS.ProductBUS;
import BUS.SupplierBUS;
import DTO.EmployeeDTO;
import DTO.ImportDTO;
import DTO.ImportDetailDTO;
import DTO.ProductDTO;
import helper.Formater;
import helper.Tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

public final class CreateImportPanel extends JPanel implements ItemListener, ActionListener {

    private PanelBorderRadius right, left;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main,
            content_btn;
    private JPanel content_top, content_left, content_right, ProductInfoPanel;
    private JPanel right_top, right_center, right_bottom, totalCost;
    private JTable ipTable, pdTable;
    private JScrollPane scrollImportTable, scrollProductTable;
    private DefaultTableCellRenderer centerRenderer;
    private DefaultTableModel ipTblModel, pdTblModel;
    private ButtonCustom btnAdd, btnEdit, btnDelete, btnCancel, btnConfirm;
    private InputForm txtEmployee, txtProductID, txtProductName, txtImportPrice, txtSellPrice, txtquantity;
    private SelectForm spCbx;
    private JTextField txtSearch;
    private JLabel lblCost;
    private Main m;
    private Color BackgroundColor = new Color(240, 247, 250);

    private ProductBUS pdBUS;
    private SupplierBUS spBUS;
    private ImportBUS ipBUS;
    private EmployeeDTO epDTO;

    private ArrayList<ProductDTO> pdList;
    private ArrayList<ImportDetailDTO> ipdList;
    private String importID;

    public CreateImportPanel(EmployeeDTO nv, String type, Main m) {
        pdBUS = new ProductBUS();
        spBUS = new SupplierBUS();
        ipBUS = new ImportBUS();
        pdList = pdBUS.getProductList();

        this.epDTO = nv;
        this.m = m;
        importID = "IP" + Tool.randomID();
        ipdList = new ArrayList<>();
        initComponent(type);
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 5));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 5));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(5, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(5, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent(String type) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        centerRenderer = new DefaultTableCellRenderer();

        tableImportDetail();

        tableProduct();

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        left_top = new JPanel();
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        contentTop();

        content_top.add(content_left);

        button();

        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        left_top.add(content_top, BorderLayout.CENTER);
        main = new JPanel();
        main.setOpaque(false);
        main.setPreferredSize(new Dimension(0, 250));
        main.setBorder(new EmptyBorder(0, 5, 10, 10));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(scrollImportTable);
        left.add(left_top, BorderLayout.CENTER);
        left.add(main, BorderLayout.SOUTH);

        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        rightTop();

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

        rightBottom();

        left_top.add(content_btn, BorderLayout.SOUTH);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
    }

    private void tableImportDetail() {
        ipTable = new JTable();
        scrollImportTable = new JScrollPane();
        ipTblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        ipTblModel.setColumnIdentifiers(header);
        ipTable.setModel(ipTblModel);
        scrollImportTable.setViewportView(ipTable);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = ipTable.getColumnModel();
        for (int i = 0; i < 4; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        ipTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        ipTable.setDefaultEditor(Object.class, null);
        ipTable.setFocusable(false);
        scrollImportTable.setViewportView(ipTable);

        ipTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = ipTable.getSelectedRow();
                if (index != -1) {
                    actionbtn("update");
                }
            }
        });
    }

    private void contentTop() {
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);

        content_left = new JPanel(new BorderLayout());
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        searchField();

        content_left.add(txtSearch, BorderLayout.NORTH);
        content_left.add(scrollProductTable, BorderLayout.CENTER);
    }

    private void tableProduct() {
        pdTable = new JTable();
        scrollProductTable = new JScrollPane();
        pdTblModel = new DefaultTableModel();
        String[] headerSP = new String[] { "Mã SP", "Tên sản phẩm", "Giá bán", "Số lượng tồn" };
        pdTblModel.setColumnIdentifiers(headerSP);
        pdTable.setModel(pdTblModel);
        scrollProductTable.setViewportView(pdTable);
        pdTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        pdTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        pdTable.setDefaultEditor(Object.class, null);
        pdTable.setFocusable(false);
        scrollProductTable.setViewportView(pdTable);
        loadProductDataIntoTalbe(pdList);

        pdTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = pdTable.getSelectedRow();
                if (index != -1) {
                    resetForm();
                    setProductInfo(pdList.get(index));
                    if (pdList.get(index) == null) {
                        actionbtn("update");
                    } else {
                        actionbtn("add");
                    }
                }
            }
        });
    }

    private void searchField() {
        txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.showClearButton", true);
        txtSearch.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ArrayList<ProductDTO> rs = pdBUS.search(txtSearch.getText());
                loadProductDataIntoTalbe(rs);
            }
        });

        txtSearch.setPreferredSize(new Dimension(100, 40));
    }

    private void button() {
        content_btn = new JPanel();
        content_btn.setPreferredSize(new Dimension(0, 47));
        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
        content_btn.setOpaque(false);
        btnAdd = new ButtonCustom("Thêm sản phẩm", "success", 14);
        btnEdit = new ButtonCustom("Sửa sản phẩm", "warning", 14);
        btnDelete = new ButtonCustom("Xoá sản phẩm", "danger", 14);
        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAdd);
        content_btn.add(btnEdit);
        content_btn.add(btnDelete);
    }

    private void rightTop() {
        right_top = new JPanel(new GridLayout(6, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 540));
        right_top.setOpaque(false);

        txtEmployee = new InputForm("Nhân viên nhập");
        txtEmployee.setText(epDTO.getEmployeeName());
        txtEmployee.setEditable(false);

        spCbx = new SelectForm("Nhà cung cấp", spBUS.getListSupplierName());
        ProductInfoPanel = new JPanel(new BorderLayout());
        ProductInfoPanel.setPreferredSize(new Dimension(100, 260));

        ProductInfoPanel = new JPanel(new BorderLayout());
        ProductInfoPanel.setPreferredSize(new Dimension(100, 260));
        txtProductID = new InputForm("Mã sản phẩm");
        txtProductID.setEditable(false);
        txtProductName = new InputForm("Tên sản phẩm");
        txtProductName.setEditable(false);
        ProductInfoPanel.add(txtProductID, BorderLayout.WEST);
        ProductInfoPanel.add(txtProductName, BorderLayout.CENTER);

        txtSellPrice = new InputForm("Giá bán");
        txtSellPrice.setEditable(false);
        PlainDocument sp = (PlainDocument) txtSellPrice.getTxtForm().getDocument();
        sp.setDocumentFilter((new NumericDocumentFilter()));

        txtImportPrice = new InputForm("Giá nhập");
        PlainDocument ipp = (PlainDocument) txtImportPrice.getTxtForm().getDocument();
        ipp.setDocumentFilter((new NumericDocumentFilter()));

        txtquantity = new InputForm("Số lượng");
        PlainDocument qtt = (PlainDocument) txtquantity.getTxtForm().getDocument();
        qtt.setDocumentFilter((new NumericDocumentFilter()));

        right_top.add(txtEmployee);
        right_top.add(spCbx);
        right_top.add(ProductInfoPanel);
        right_top.add(txtSellPrice);
        right_top.add(txtImportPrice);
        right_top.add(txtquantity);
    }

    private void rightBottom() {
        totalCost = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        totalCost.setOpaque(false);
        JLabel lblTotalCost = new JLabel("TỔNG TIỀN:");
        lblTotalCost.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        lblCost = new JLabel("0đ");
        lblCost.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        lblTotalCost.setForeground(new Color(255, 51, 51));
        totalCost.add(lblTotalCost);
        totalCost.add(lblCost);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.setOpaque(false);
        btnCancel = new ButtonCustom("Hủy", "danger", 14);
        btnCancel.setPreferredSize(new Dimension(120, 40));
        btnCancel.addActionListener(this);
        btnConfirm = new ButtonCustom("Xác nhận", "excel", 14);
        btnConfirm.setPreferredSize(new Dimension(120, 40));
        btnConfirm.addActionListener(this);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnConfirm);

        right_bottom = new JPanel(new BorderLayout());
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);
        right_bottom.add(totalCost, BorderLayout.NORTH);
        right_bottom.add(buttonPanel, BorderLayout.CENTER);
    }

    public void loadProductDataIntoTalbe(ArrayList<DTO.ProductDTO> pdList) {
        pdTblModel.setRowCount(0);
        if (pdList == null) {
            return;
        }

        for (ProductDTO pdDTO : pdList) {
            pdTblModel.addRow(new Object[] { pdDTO.getProductID(), pdDTO.getProductName(), pdDTO.getSellPrice(),
                    pdDTO.getQuantity() });
        }
    }

    public void loadImportDetailDataIntoTable(ArrayList<ImportDetailDTO> ipdList) {
        ipTblModel.setRowCount(0);
        if (ipdList == null) {
            return;
        }

        for (ImportDetailDTO ipdDTO : ipdList) {
            ipTblModel.addRow(
                    new Object[] { ipdDTO.getProductID(), ipdDTO.getQuantity(), ipdDTO.getPrice(), ipdDTO.getCost() });
        }
        lblCost.setText(Formater.FormatVND(ipBUS.getTotalCost(ipdList)));
    }

    public void setProductInfo(ProductDTO pdDTO) {
        this.txtProductID.setText(pdDTO.getProductID());
        this.txtProductName.setText(pdDTO.getProductName());
        // this.txtImportPrice.setText("");
    }

    public ImportDetailDTO getImportDetailInfo() {
        String productID = txtProductID.getText();
        long importPrice = Long.parseLong(txtImportPrice.getText());
        int quantity = Integer.parseInt(txtquantity.getText());
        return new ImportDetailDTO(importID, productID, importPrice, quantity, quantity * importPrice);
    }

    public boolean validateImport() {
        if (Tool.isEmpty(txtProductID.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Tool.isEmail(txtImportPrice.getText())) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được để rỗng !", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Tool.isEmpty(txtquantity.getText()) || !Tool.isNumber(txtquantity.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để rỗng và phải là số!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Object source = e.getSource();
        // if (source == cbxCauhinh.cbb) {
        // int index = cbxCauhinh.cbb.getSelectedIndex();
        // this.txtImportPrice.setText(Integer.toString(ch.get(index).getGianhap()));
        // ImportDetailDTO ctp = checkTonTai();
        // if (ctp == null) {
        // actionbtn("add");
        // this.txtquantity.setText("");
        // this.txtMaImeiTheoLo.setText("");
        // this.textAreaImei.setText("");
        // } else {
        // actionbtn("update");
        // setImei(ctp);
        // }
        // } else if (source == cbxPtNhap.cbb) {
        // int index = cbxPtNhap.cbb.getSelectedIndex();
        // CardLayout c = (CardLayout) content_right_bottom.getLayout();
        // switch (index) {
        // case 0 ->
        // c.first(content_right_bottom);
        // case 1 ->
        // c.last(content_right_bottom);
        // }
    }

    public void addImportDetail() {
        ImportDetailDTO ipdDTO = getImportDetailInfo();
        int index = ipBUS.findIndexByProductID(ipdList, ipdDTO.getProductID());
        if (index == 0) {
            ipdList.add(ipdDTO);
        } else {
            int quantity = ipdList.get(index).getQuantity() + ipdDTO.getQuantity();
            ipdList.get(index).setQuantity(quantity);
        }
        loadImportDetailDataIntoTable(ipdList);
        resetForm();
    }

    public void actionbtn(String type) {
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAdd.setEnabled(val_1);
        btnEdit.setEnabled(val_2);
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }

    public void resetForm() {
        this.txtProductID.setText("");
        this.txtProductName.setText("");
        this.txtImportPrice.setText("");
        this.txtquantity.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAdd && validateImport()) {
            addImportDetail();
        } else if (source == btnDelete) {
            delete();
        } else if (source == btnEdit) {
            edit();
        } else if (source == btnConfirm) {
            confirm();
        } else if (source == btnCancel) {
            m.setPanel(new ImportPanel(m, epDTO));
        }
    }

    private void edit() {
        int index = ipTable.getSelectedRow();
        int quantity = Integer.parseInt(txtquantity.getText());
        ipdList.get(index).setQuantity(quantity);
        loadImportDetailDataIntoTable(ipdList);
    }

    private void delete() {
        int index = ipTable.getSelectedRow();
        ipdList.remove(index);
        actionbtn("add");
        loadImportDetailDataIntoTable(ipdList);
        resetForm();
    }

    private void confirm() {
        if (ipdList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu nhập !",
                    "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                String supplierID = spBUS.getIDByName((String) spCbx.getSelectedItem());
                String employeeID = m.getEmployee().getEmployeeID();
                long totalCost = ipBUS.getTotalCost(ipdList);
                ipBUS.add(new ImportDTO(importID, supplierID, employeeID, totalCost), ipdList);
                ImportPanel ipPanel = new ImportPanel(m, epDTO);
                m.setPanel(ipPanel);
            }
        }
    }
}
