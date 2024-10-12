/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this main2late
 */
package GUI.Dialog;

import BUS.EmployeeBUS;
import DTO.EmployeeDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.BCrypt;
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

public class MyAccount extends JDialog implements ActionListener {

//    private CardLayout card;
    private ButtonCustom save;
    private HeaderTitle title;
    private JPanel top, center, top_center, main_center, bottom;
    private InputForm currentPass, newPass, confirmPass;
    private EmployeeDTO epDTO;
    private EmployeeBUS epBUS;
    private EmployeeDialog epDialog;
    private JPanel changePassPanel;

    public MyAccount(JFrame owner, EmployeeDialog epDialog, String title, boolean modal) {
        super(owner, title, modal);
        initComponent(epDialog);
        this.setLocationRelativeTo(null);
    }

    public void initComponent(EmployeeDialog epDialog) {
        epBUS = new EmployeeBUS();
        this.epDialog = epDialog;
        this.setSize(400, 500);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        epDTO = epDialog.getEmployee();
        top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("ĐỔI MẬT KHẨU");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        top_center = new JPanel(new FlowLayout(1, 40, 0));
        top_center.setBorder(new EmptyBorder(20, 0, 0, 0));
        top_center.setBackground(Color.WHITE);
        main_center = new JPanel();
        main_center.setBorder(new EmptyBorder(0, 20, 0, 20));
        main_center.setBackground(Color.WHITE);

        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(top_center, BorderLayout.NORTH);
        center.add(main_center, BorderLayout.CENTER);

        changePassPanel = new JPanel(new GridLayout(3, 1));
        changePassPanel.setPreferredSize(new Dimension(400, 300));
        currentPass = new InputForm("Mật khẩu hiện tại", "password");
        newPass = new InputForm("Mật khẩu mới", "password");
        confirmPass = new InputForm("Nhập lại mật khẩu mới", "password");
        changePassPanel.add(currentPass);
        changePassPanel.add(newPass);
        changePassPanel.add(confirmPass);
        main_center.add(changePassPanel);

        this.add(center, BorderLayout.CENTER);

        bottom = new JPanel(new FlowLayout(1, 20, 10));
        bottom.setBackground(Color.WHITE);

        save = new ButtonCustom("Cập nhật", "success", 15);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            changPass();
        }

    }

    private void changPass() {
        if (Tool.isEmpty(currentPass.getPass())) {
            JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        } else if (Tool.isEmpty(newPass.getPass()) || newPass.getPass().length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        } else if (Tool.isEmpty(confirmPass.getPass())) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (newPass.getPass().equals(confirmPass.getPass()) == false) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            if (BCrypt.checkpw(currentPass.getPass(), epDTO.getPassword())) {
                String password = BCrypt.hashpw(confirmPass.getPass(), BCrypt.gensalt(12));
                epBUS.updatePassword(epDTO.getEmail(), password);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                currentPass.setPass("");
                newPass.setPass("");
                confirmPass.setPass("");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
        }
        epDialog.resetChange();
    }
}
