/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.InputImage;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.ProductPanel;
import helper.Formater;
import helper.Tool;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.PlainDocument;

import BUS.ProductBUS;
import BUS.SupplierBUS;
import BUS.Property.BrandBUS;
import BUS.Property.ColorBUS;
import BUS.Property.DiscountBUS;
import BUS.Property.FuelBUS;
import BUS.Property.SeatBUS;
import BUS.Property.StyleBUS;
import DTO.ProductDTO;
import DTO.ProductDetailDTO;

/**
 *
 * @author ASUS
 */

public class ProductDialog extends JDialog implements ActionListener {
    private HeaderTitle titlePage;
    private JPanel pnProductInfo, pnbottom, pnCenter, pnProductInfoRight, pnmain;
    private ButtonCustom btnAdd, btnSave, btnCancel;
    private InputForm txtProductName, txtBasicPrice, txtYearOfManufacture;
    private SelectForm cbBrandName, cbSeeting, cbColor, cbStyle, cbFuel, cbDiscount, cbGearBox, cbStatus, cbSupplier;
    private InputImage productImg;
    private DefaultTableCellRenderer centerRenderer;
    private GUI.Panel.ProductPanel pdPanel;

    private ProductBUS pdBUS;
    private BrandBUS bBUS;
    private ColorBUS clBUS;
    private DiscountBUS dcBUS;
    private FuelBUS fBUS;
    private SeatBUS sBUS;
    private StyleBUS stBUS;
    private String productID;
    private SupplierBUS spBUS;

    private String[] arrBrandName;
    private String[] arrColorName;
    private String[] arrStyleName;
    private String[] arrFuelType;
    private String[] arrNumberOfSeeting;
    private String[] arrDiscountPercent;
    private String[] arrSupplierName;

    private ProductDetailDTO pddDTO;
    private ProductDTO pdDTO;

    public void init(ProductPanel pdPanel) {
        this.pdPanel = pdPanel;

        centerRenderer = new DefaultTableCellRenderer();
        pdBUS = new ProductBUS();
        bBUS = new BrandBUS();
        clBUS = new ColorBUS();
        dcBUS = new DiscountBUS();
        fBUS = new FuelBUS();
        sBUS = new SeatBUS();
        stBUS = new StyleBUS();
        spBUS = new SupplierBUS();

        arrBrandName = bBUS.getListBrandName();
        arrColorName = clBUS.getListColorName();
        arrStyleName = stBUS.getListStyleName();
        arrFuelType = fBUS.getListFuelType();
        arrNumberOfSeeting = sBUS.getListNumberOfSeeting();
        arrDiscountPercent = dcBUS.getListDiscountPercent();
        arrSupplierName = spBUS.getListSupplierName();
    }

    public ProductDialog(ProductPanel pdPanel, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        productID = "PD" + Tool.randomID();
        init(pdPanel);
        initCardOne(type);
        initComponents(title);
    }

    public ProductDialog(ProductPanel pdPanel, JFrame owner, String title, boolean modal, String type,
            ProductDTO pdDTO) {
        super(owner, title, modal);
        init(pdPanel);
        initCardOne(type);
        setInfo(pdDTO);
        this.pdDTO = pdDTO;
        productID = pdDTO.getProductID();
        initComponents(title);
    }

    public void initCardOne(String type) {
        pnCenter = new JPanel(new BorderLayout());
        pnProductInfo = new JPanel(new GridLayout(3, 4, 0, 0));
        pnProductInfo.setBackground(Color.WHITE);
        pnCenter.add(pnProductInfo, BorderLayout.CENTER);

        pnProductInfoRight = new JPanel();
        pnProductInfoRight.setBackground(Color.WHITE);
        pnProductInfoRight.setPreferredSize(new Dimension(300, 600));
        pnProductInfoRight.setBorder(new EmptyBorder(0, 10, 0, 10));
        pnCenter.add(pnProductInfoRight, BorderLayout.WEST);

        txtProductName = new InputForm("Tên sản phẩm");

        cbSupplier = new SelectForm("Nhà cung cấp", arrSupplierName);
        if (type.equals("update")) {
            cbSupplier.getCbb().setEnabled(false);
        }

        txtBasicPrice = new InputForm("Giá gốc");
        txtBasicPrice.setText("0đ");
        txtBasicPrice.getTxtForm().setEnabled(false);

        cbStatus = new SelectForm("Trạng thái", new String[] { "Còn bán", "Ngưng bán" });
        cbBrandName = new SelectForm("Tên hãng", arrBrandName);

        txtYearOfManufacture = new InputForm("Năm sản xuẩt");
        PlainDocument MFG = (PlainDocument) txtYearOfManufacture.getTxtForm().getDocument();
        MFG.setDocumentFilter((new NumericDocumentFilter()));

        cbSeeting = new SelectForm("Số ghế ngồi", arrNumberOfSeeting);
        cbStyle = new SelectForm("Kiểu dáng xe", arrStyleName);
        cbFuel = new SelectForm("Loại nhiên liệu", arrFuelType);
        cbColor = new SelectForm("Màu sắc", arrColorName);
        cbGearBox = new SelectForm("Hộp số", new String[] { "Số sàn", "Số tự động" });
        cbDiscount = new SelectForm("Phần trăm giảm giá", arrDiscountPercent);
        productImg = new InputImage("Hình minh họa");

        pnProductInfo.add(txtProductName);
        pnProductInfo.add(cbSupplier);
        pnProductInfo.add(txtBasicPrice);
        pnProductInfo.add(cbStatus);
        pnProductInfo.add(cbBrandName);
        pnProductInfo.add(txtYearOfManufacture);
        pnProductInfo.add(cbSeeting);
        pnProductInfo.add(cbStyle);
        pnProductInfo.add(cbFuel);
        pnProductInfo.add(cbColor);
        pnProductInfo.add(cbGearBox);
        pnProductInfo.add(cbDiscount);
        pnProductInfoRight.add(productImg);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        switch (type) {
            case "update" -> {
                btnSave = new ButtonCustom("Lưu", "success", 14);
                btnSave.addActionListener(this);
                pnbottom.add(btnSave);
            }
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm", "success", 14);
                btnAdd.addActionListener(this);
                pnbottom.add(btnAdd);
            }
        }

        btnCancel = new ButtonCustom("Huỷ", "danger", 14);
        btnCancel.addActionListener(this);
        pnbottom.add(btnCancel);
        pnCenter.add(pnbottom, BorderLayout.SOUTH);
    }

    public void initComponents(String title) {
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.setSize(new Dimension(1150, 480));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnmain = new JPanel(new CardLayout());
        pnmain.add(pnCenter);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String addImage(String urlImg) {
        Random randomGenerator = new Random();
        int ram = randomGenerator.nextInt(1000);
        File sourceFile = new File(urlImg);
        String destPath = "./src/img_product";
        File destFolder = new File(destPath);
        String newName = ram + sourceFile.getName();
        try {
            Path dest = Paths.get(destFolder.getPath(), newName);
            Files.copy(sourceFile.toPath(), dest);
        } catch (IOException e) {
        }
        return newName;
    }

    private void save() {
        if (!validateCardOne()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        int input = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn chỉnh sửa chi tiết sản phẩm?", "Chỉnh sửa chi tiết",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        // 0=ok, 2=cancel
        if (input == 0) {
            getInfo();
            pdBUS.updateProduct(pdDTO);
            pdBUS.updateProductDetail(pddDTO);
            pdPanel.loadNewDataIntoTable();
            dispose();
        }
    }

    private void add() {
        if (!validateCardOne()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        getInfo();
        pdDTO.setProductID(productID);
        pdBUS.addProduct(pdDTO);
        pdBUS.addProductDetail(pddDTO);
        pdPanel.loadNewDataIntoTable();
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAdd) {
            add();
        } else if (source == btnSave) {
            save();
        } else {
            dispose();
        }
    }

    public void getInfo() {
        String supplierID = spBUS.getIDByName(cbSupplier.getValue());
        String pdName = txtProductName.getText();
        String pdImg = productImg.getUrl_img();
        Long pdBasicPrice = Long.parseLong(txtBasicPrice.getText().replaceAll("\\.", "").replaceAll("đ", ""));
        String pdStatus = cbStatus.getValue();
        String pdBrand = cbBrandName.getValue();
        int pdMFG = Integer.parseInt(txtYearOfManufacture.getText());
        int pdSeet = Integer.parseInt(cbSeeting.getValue());
        String pdStyle = cbStyle.getValue();
        String pdFuel = cbFuel.getValue();
        String pdColor = cbColor.getValue();
        String pdGearBox = cbGearBox.getValue();
        int pdDiscount = Integer.parseInt(cbDiscount.getValue().replaceAll("%", ""));
        long pdSellPrice = pdBasicPrice * (100 - pdDiscount) / 100;
        pdDTO = new ProductDTO(productID, supplierID, pdName, pdImg, pdStatus, pdSellPrice);
        pddDTO = new ProductDetailDTO(productID, pdBrand, pdStyle, pdFuel, pdColor, pdGearBox, pdMFG, pdSeet,
                pdDiscount);
    }

    public void setInfo(ProductDTO pdDTO) {
        String supplierName = spBUS.getNameByID(pdDTO.getSupplierID());
        cbSupplier.setSelectedItem(supplierName);
        txtProductName.setText(pdDTO.getProductName());
        productImg.setUrl_img(pdDTO.getProductImg());
        txtBasicPrice.setText(Formater.FormatVND(pdDTO.getBasicPrice()));
        cbStatus.setSelectedItem(pdDTO.getStatus());

        pddDTO = pdBUS.getProductDetailByID(pdDTO.getProductID());
        cbBrandName.setSelectedItem(pddDTO.getBrandName());
        txtYearOfManufacture.setText(pddDTO.getYearOfManufacture() + "");
        cbSeeting.getCbb().setSelectedItem(pddDTO.getNumberOfSeating() + "");
        cbStyle.getCbb().setSelectedItem(pddDTO.getStyleName());
        cbFuel.getCbb().setSelectedItem(pddDTO.getFuelType());
        cbColor.getCbb().setSelectedItem(pddDTO.getColorName());
        cbGearBox.getCbb().setSelectedItem(pddDTO.getGearBox());
        cbDiscount.getCbb().setSelectedItem(pddDTO.getDiscountPercent() + "%");
    }

    public boolean validateCardOne() {
        if (Tool.isEmpty(txtProductName.getText()) || Tool.isEmpty(txtYearOfManufacture.getText())
                || Tool.isEmpty(productImg.getUrl_img())) {
            return false;
        }
        return true;
    }
}
