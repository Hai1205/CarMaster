/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import DTO.SupplierDTO;
import GUI.Panel.SupplierPanel;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import helper.Tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 */
public class SupplierDialog extends JDialog implements ActionListener {

    private SupplierPanel spPanel;
    private HeaderTitle titlePage;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnAdd, btnUpdate, btnCancel;
    private InputForm supplierName, address, email, phone;
    private SupplierDTO spDTO;

    public SupplierDialog(SupplierPanel spPanel, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.spPanel = spPanel;
        initComponents(title, type);
    }

    public SupplierDialog(SupplierPanel spPanel, JFrame owner, String title, boolean modal, String type,
            SupplierDTO spDTO) {
        super(owner, title, modal);
        this.spPanel = spPanel;
        this.spDTO = spDTO;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(900, 360));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnmain = new JPanel(new GridLayout(2, 2, 20, 0));
        pnmain.setBackground(Color.white);
        supplierName = new InputForm("Tên nhà cung cấp");
        address = new InputForm("Địa chỉ");
        email = new InputForm("Email");
        phone = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));

        pnmain.add(supplierName);
        pnmain.add(address);
        pnmain.add(email);
        pnmain.add(phone);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm", "success", 14);
        btnUpdate = new ButtonCustom("Lưu", "success", 14);
        btnCancel = new ButtonCustom("Huỷ", "danger", 14);

        // Add MouseListener btn
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnCancel.addActionListener(this);

        switch (type) {
            case "create" ->
                pnbottom.add(btnAdd);
            case "update" -> {
                pnbottom.add(btnUpdate);
                initInfo();
            }
            default ->
                throw new AssertionError();
        }
        pnbottom.add(btnCancel);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.add(pnbottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initInfo() {
        supplierName.setText(spDTO.getSupplierName());
        address.setText(spDTO.getAddress());
        email.setText(spDTO.getEmail());
        phone.setText(spDTO.getPhone());
    }

    public boolean valid() {
        if (Tool.isEmpty(supplierName.getText()) || Tool.isEmpty(address.getText()) || Tool.isEmpty(email.getText())
                || !Tool.isEmail(email.getText()) || Tool.isEmpty(phone.getText()) || !Tool.isNumber(phone.getText())) {
            return false;
        }
        return true;
    }

    private void add() {
        if (!valid()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String supplierID = "SP" + Tool.randomID();
        spPanel.getSupplierBUS()
                .add(new SupplierDTO(supplierID, supplierName.getText(), address.getText(), email.getText(),
                        phone.getText()));
        dispose();
    }

    private void update() {
        if (!valid()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        spPanel.getSupplierBUS()
                .update(new SupplierDTO(spDTO.getSupplierID(), supplierName.getText(), address.getText(),
                        email.getText(), phone.getText()));
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            add();
        } else if (e.getSource() == btnUpdate) {
            update();
        } else {
            dispose();
        }
    }
}
