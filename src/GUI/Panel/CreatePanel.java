package GUI.Panel;

import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.IntegratedSearch;
import GUI.Component.NumericDocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Main;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import BUS.CustomerBUS;
import BUS.ImportBUS;
import BUS.InvoiceBUS;
import BUS.ProductBUS;
import BUS.SupplierBUS;
import DTO.EmployeeDTO;
import DTO.ImportDTO;
import DTO.ImportDetailDTO;
import DTO.InvoiceDTO;
import DTO.InvoiceDetailDTO;
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

public final class CreatePanel extends JPanel implements ItemListener, ActionListener {

    private PanelBorderRadius right, left;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main,
            content_btn;
    private JPanel content_top, content_left, ProductInfoPanel;
    private JPanel right_top, right_center, right_bottom, totalCost;
    private JTable detailTable, pdTable;
    private JScrollPane scrollImportTable, scrollProductTable;
    private DefaultTableCellRenderer centerRenderer;
    private DefaultTableModel ipTblModel, pdTblModel;
    private ButtonCustom btnAdd, btnEdit, btnDelete, btnCancel, btnConfirm;
    private InputForm txtEmployee, txtProductID, txtProductName, txtImportPrice, txtSellPrice, txtquantity;
    private PlainDocument sp;
    private SelectForm spCbx, ctmCbx;
    private IntegratedSearch txtSearch;
    private JLabel lblCost;
    private Main m;
    private Color BackgroundColor = new Color(240, 247, 250);

    private ProductBUS pdBUS;
    private SupplierBUS spBUS;
    private CustomerBUS ctmBUS;
    private ImportBUS ipBUS;
    private InvoiceBUS ivBUS;
    private EmployeeDTO epDTO;

    private ArrayList<ProductDTO> pdList;
    private ArrayList<ImportDetailDTO> ipdList;
    private ArrayList<InvoiceDetailDTO> ivdList;
    private String importID, invoiceID;

    private InvoicePanel ivPanel;

    public CreatePanel(EmployeeDTO nv, String type, Main m) {
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

    public CreatePanel(EmployeeDTO nv, String type, Main m, InvoicePanel ivPanel) {
        pdBUS = new ProductBUS();
        ctmBUS = new CustomerBUS();
        ivBUS = new InvoiceBUS();
        pdList = pdBUS.getProductList();

        this.ivPanel = ivPanel;
        this.epDTO = nv;
        this.m = m;
        invoiceID = "IV" + Tool.randomID();
        ivdList = new ArrayList<>();
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

        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);

        contentLeft();

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
        detailTable = new JTable();
        scrollImportTable = new JScrollPane();
        ipTblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        ipTblModel.setColumnIdentifiers(header);
        detailTable.setModel(ipTblModel);
        scrollImportTable.setViewportView(detailTable);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = detailTable.getColumnModel();
        for (int i = 0; i < 4; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        detailTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        detailTable.setDefaultEditor(Object.class, null);
        detailTable.setFocusable(false);
        scrollImportTable.setViewportView(detailTable);

        detailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = detailTable.getSelectedRow();
                if (index != -1) {
                    actionbtn("update");
                }
            }
        });
    }

    private void contentLeft() {
        content_left = new JPanel(new BorderLayout());
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        searchField();

        tableProduct();

        content_left.add(txtSearch, BorderLayout.NORTH);
        content_left.add(scrollProductTable, BorderLayout.CENTER);
    }

    private void searchField() {
        txtSearch = new IntegratedSearch();
        txtSearch.setPreferredSize(new Dimension(0, 80));
        txtSearch.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ArrayList<ProductDTO> rs = pdBUS.search(txtSearch.getTxtSearchForm().getText());
                loadProductDataIntoTalbe(rs);
            }
        });

    }

    private void tableProduct() {
        pdTable = new JTable();
        scrollProductTable = new JScrollPane();
        pdTblModel = new DefaultTableModel();
        String[] headerSP = new String[] { "Mã Sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng tồn" };
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
        btnAdd.setEnabled(false);
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

        if (ivPanel == null) {
            spCbx = new SelectForm("Nhà cung cấp", spBUS.getListSupplierName());
        } else {
            spCbx = new SelectForm("Khách hàng", ctmBUS.getListCustomerName());
        }

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

        txtSellPrice = new InputForm("Đơn giá");
        txtSellPrice.setEditable(false);
        sp = (PlainDocument) txtSellPrice.getTxtForm().getDocument();

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
        if (ivPanel == null) {
            right_top.add(txtImportPrice);
        }
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
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);

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
            pdTblModel.addRow(new Object[] { pdDTO.getProductID(), pdDTO.getProductName(),
                    Formater.FormatVND(pdDTO.getSellPrice()),
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
                    new Object[] { ipdDTO.getProductID(), ipdDTO.getQuantity(), Formater.FormatVND(ipdDTO.getPrice()),
                            Formater.FormatVND(ipdDTO.getCost()) });
        }
        lblCost.setText(Formater.FormatVND(ipBUS.getTotalCost(ipdList)));
    }

    public void loadInvoiceDetailDataIntoTable(ArrayList<InvoiceDetailDTO> ivdList) {
        ipTblModel.setRowCount(0);
        if (ivdList == null) {
            return;
        }

        for (InvoiceDetailDTO ivdDTO : ivdList) {
            ipTblModel.addRow(
                    new Object[] { ivdDTO.getProductID(), ivdDTO.getQuantity(), Formater.FormatVND(ivdDTO.getPrice()),
                            Formater.FormatVND(ivdDTO.getCost()) });
        }
        lblCost.setText(Formater.FormatVND(ivBUS.getTotalCost(ivdList)));
    }

    public void setProductInfo(ProductDTO pdDTO) {
        this.txtProductID.setText(pdDTO.getProductID());
        this.txtProductName.setText(pdDTO.getProductName());

        Long price = (ivPanel == null) ? pdDTO.getBasicPrice() : pdDTO.getSellPrice();

        boolean isEditable = pdDTO.getSellPrice() == 0;
        this.txtSellPrice.setEditable(isEditable);
        sp.setDocumentFilter(isEditable ? new NumericDocumentFilter() : null);

        if (!isEditable) {
            this.txtSellPrice.setText(Formater.FormatVND(price));
        }
    }

    public ImportDetailDTO getImportDetailInfo() {
        String productID = txtProductID.getText();
        long price = Long.parseLong(txtImportPrice.getText());
        int quantity = Integer.parseInt(txtquantity.getText());
        return new ImportDetailDTO(importID, productID, price, quantity, quantity * price);
    }

    public InvoiceDetailDTO getInvoiceDetailInfo() {
        String productID = txtProductID.getText();
        long price = Long.parseLong(txtSellPrice.getText().replaceAll("\\.", "").replaceAll("đ", ""));
        int quantity = Integer.parseInt(txtquantity.getText());
        return new InvoiceDetailDTO(importID, productID, price, quantity, quantity * price);
    }

    public boolean validateImport() {
        if (Tool.isEmpty(txtProductID.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Tool.isEmail(txtSellPrice.getText())) {
            JOptionPane.showMessageDialog(this, "Giá bán không được để rỗng !", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
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
    }

    public void addImport() {
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
        txtSellPrice.setEditable(false);
    }

    public void addInvoice() {
        InvoiceDetailDTO ivdDTO = getInvoiceDetailInfo();
        int index = ivBUS.findIndexByProductID(ivdList, ivdDTO.getProductID());
        if (index == 0) {
            ivdList.add(ivdDTO);
        } else {
            int quantity = ivdList.get(index).getQuantity() + ivdDTO.getQuantity();
            ivdList.get(index).setQuantity(quantity);
        }
        loadInvoiceDetailDataIntoTable(ivdList);
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
        this.txtSellPrice.setText("");
        this.txtquantity.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (ivPanel == null) {
            if (source == btnAdd && validateImport()) {
                addImport();
            } else if (source == btnDelete) {
                deleteImport();
            } else if (source == btnEdit) {
                editImport();
            } else if (source == btnConfirm) {
                confirm();
            } else if (source == btnCancel) {
                m.setPanel(new ImportPanel(m, epDTO));
            }
        } else {
            if (source == btnAdd && validateImport()) {
                addInvoice();
            } else if (source == btnDelete) {
                deleteInvoice();
            } else if (source == btnEdit) {
                editInvoice();
            } else if (source == btnConfirm) {
                confirm();
            } else if (source == btnCancel) {
                m.setPanel(new InvoicePanel(m, epDTO));
            }
        }
    }

    private void editImport() {
        int index = detailTable.getSelectedRow();
        int quantity = Integer.parseInt(txtquantity.getText());
        ipdList.get(index).setQuantity(quantity);
        loadImportDetailDataIntoTable(ipdList);
    }

    private void editInvoice() {
        int index = detailTable.getSelectedRow();
        int quantity = Integer.parseInt(txtquantity.getText());
        ivdList.get(index).setQuantity(quantity);
        loadInvoiceDetailDataIntoTable(ivdList);
    }

    private void deleteImport() {
        int index = detailTable.getSelectedRow();
        ipdList.remove(index);
        actionbtn("add");
        loadImportDetailDataIntoTable(ipdList);
        resetForm();
    }

    private void deleteInvoice() {
        int index = detailTable.getSelectedRow();
        ivdList.remove(index);
        actionbtn("add");
        loadInvoiceDetailDataIntoTable(ivdList);
        resetForm();
    }

    private void confirm() {
        if (ipdList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu nhập !",
                "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (input != 0) {
            return;
        }

        String employeeID = m.getEmployee().getEmployeeID();
        if (ivPanel == null) {
            String supplierID = spBUS.getIDByName((String) spCbx.getSelectedItem());
            long totalCost = ipBUS.getTotalCost(ipdList);
            ipBUS.add(new ImportDTO(importID, supplierID, employeeID, totalCost), ipdList);
            ImportPanel ipPanel = new ImportPanel(m, epDTO);
            m.setPanel(ipPanel);
        } else {
            String customerID = ctmBUS.getIDByName((String) ctmCbx.getSelectedItem());
            long totalCost = ivBUS.getTotalCost(ivdList);
            ivBUS.add(new InvoiceDTO(invoiceID, customerID, employeeID, totalCost), ivdList);
            InvoicePanel ivPanel = new InvoicePanel(m, epDTO);
            m.setPanel(ivPanel);
        }
    }
}