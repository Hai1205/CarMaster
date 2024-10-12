package GUI.Dialog;

import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.ButtonCustom;
import GUI.Component.NumericDocumentFilter;
import helper.Tool;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import DTO.CustomerDTO;
import GUI.Panel.CustomerPanel;

public class CustomerDialog extends JDialog implements MouseListener {

    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnAdd, btnUpdate;
    private InputForm txtCustomerName, txtPhone, txtAddress;
    private final CustomerPanel ctmPanel;
    private CustomerDTO ctmDTO;

    public CustomerDialog(CustomerPanel ctmPanel, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.ctmPanel = ctmPanel;
        initComponents(title, type);
    }

    public CustomerDialog(CustomerPanel ctmPanel, JFrame owner, String title, boolean modal, String type,
            CustomerDTO ctmDTO) {
        super(owner, title, modal);
        this.ctmDTO = ctmDTO;
        this.ctmPanel = ctmPanel;

        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        txtCustomerName = new InputForm("Tên khách hàng");
        txtAddress = new InputForm("Địa chỉ");
        txtPhone = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) txtPhone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));

        this.setSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(txtCustomerName);
        pnlMain.add(txtPhone);
        pnlMain.add(txtAddress);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm", "success", 14);
        btnUpdate = new ButtonCustom("Cập nhật", "success", 14);

        // Add MouseListener btn
        btnAdd.addMouseListener(this);
        btnUpdate.addMouseListener(this);

        switch (type) {
            case "create" -> {
                pnlButtom.add(btnAdd);
            }
            case "update" -> {
                setCustomerName(ctmDTO.getCustomerName());
                setPhone(ctmDTO.getPhone());
                setAddress(ctmDTO.getAddress());
                pnlButtom.add(btnUpdate);
            }
        }

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setCustomerName(String name) {
        txtCustomerName.setText(name);
    }

    public String getCustomerName() {
        return txtCustomerName.getText();
    }

    public String getPhone() {
        return txtPhone.getText();
    }

    public void setPhone(String phone) {
        this.txtPhone.setText(phone);
    }

    public String getAddress() {
        return txtAddress.getText();
    }

    public void setAddress(String address) {
        this.txtAddress.setText(address);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    private boolean Valid() {
        if (Tool.isEmpty(txtCustomerName.getText()) || Tool.isEmpty(txtAddress.getText())
                || Tool.isEmpty(txtPhone.getText()) || !Tool.isNumber(txtPhone.getText())) {
            return false;
        }

        return true;
    }

    private void add() {
        if (!Valid()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
        }

        String customerID = "CTM" + Tool.randomID();
        ctmPanel.getCustomerBUS()
                .add(new DTO.CustomerDTO(customerID, txtCustomerName.getText(), txtAddress.getText(),
                        txtPhone.getText()));
        ctmPanel.loadNewDataIntoTabel();
        dispose();
    }

    private void update() {
        if (!Valid()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
        }

        ctmPanel.getCustomerBUS()
                .update(new CustomerDTO(ctmDTO.getCustomerID(), txtCustomerName.getText(), txtAddress.getText(),
                        txtPhone.getText()));
        ctmPanel.loadNewDataIntoTabel();
        dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == btnAdd) {
            add();
        } else if (e.getSource() == btnUpdate) {
            update();
        } else {
            dispose();
        }
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
