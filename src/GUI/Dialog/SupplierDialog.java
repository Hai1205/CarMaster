/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import DTO.SupplierDTO;
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

import BUS.SupplierBUS;

/**
 *
 */
public class SupplierDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnAdd, btnUpdate;
    private InputForm supplierName, address, email, phone;
    private SupplierDTO spDTO;
    private SupplierBUS spBUS;

    public SupplierDialog(SupplierBUS spBUS, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.spBUS=spBUS;
        initComponents(title, type);
    }

    public SupplierDialog(SupplierBUS spBUS, JFrame owner, String title, boolean modal, String type,
            SupplierDTO spDTO) {
        super(owner, title, modal);
        this.spDTO = spDTO;
        this.spBUS=spBUS;
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
        btnUpdate = new ButtonCustom("Cập nhật", "success", 14);

        // Add MouseListener btn
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);

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
        
        String supplierID = spBUS.createID();
        spBUS.add(new SupplierDTO(supplierID, supplierName.getText(), address.getText(), email.getText(),
                        phone.getText()));
        dispose();
    }

    private void update() {
        if (!valid()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        spBUS.update(new SupplierDTO(spDTO.getSupplierID(), supplierName.getText(), address.getText(),
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
