/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.EmployeeBUS;
import DTO.EmployeeDTO;
import helper.BCrypt;
import helper.SendEmailSMTP;
import helper.Tool;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class ForgotPassword extends JDialog implements ActionListener {

    private JButton btnSendMail, btnConfirmOTP, btnChangePass;
    private JPanel jpTop, jpMain, jpCard_1, jpCard_2, jpCard_3;
    private JLabel lblTitle, lblNhapEmail, lblNhapOTP, lblNhapPassword;
    private JTextField txtEmail, txtOTP;
    private JPasswordField txtPassword;
    private String emailCheck;
    private EmployeeBUS epBUS;

    public ForgotPassword(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    public void initComponents() {
        epBUS = new EmployeeBUS();

        this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.setTitle("Quên mật khẩu");
        this.setSize(new Dimension(500, 200));
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        jpTop = new JPanel(new BorderLayout());
        jpTop.setBackground(new Color(22, 122, 198));
        jpTop.setPreferredSize(new Dimension(400, 60));

        lblTitle = new JLabel();
        lblTitle.setFont(new Font("Segoe UI", 1, 18));
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setText("QUÊN MẬT KHẨU");
        lblTitle.setPreferredSize(new Dimension(400, 50));
        jpTop.add(lblTitle, BorderLayout.CENTER);

        jpMain = new JPanel();
        jpMain.setLayout(new CardLayout());

        // Step 1
        jpCard_1 = new JPanel(new FlowLayout(2, 10, 10));
        jpCard_1.setBackground(new Color(255, 255, 255));
        lblNhapEmail = new JLabel();
        lblNhapEmail.setText("Nhập địa chỉ email");
        lblNhapEmail.setHorizontalAlignment(Label.LEFT);
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new java.awt.Dimension(350, 35));

        btnSendMail = new JButton("Gửi mã");
        btnSendMail.setPreferredSize(new Dimension(100, 35));
        btnSendMail.addActionListener(this);
        jpCard_1.add(lblNhapEmail);
        jpCard_1.add(txtEmail);
        jpCard_1.add(btnSendMail);

        // Step 2
        jpCard_2 = new JPanel(new FlowLayout(2, 10, 10));
        jpCard_2.setBackground(new Color(255, 255, 255));
        lblNhapOTP = new JLabel();
        lblNhapOTP.setText("Nhập mã OTP");

        txtOTP = new JTextField();
        txtOTP.setPreferredSize(new java.awt.Dimension(350, 35));

        btnConfirmOTP = new JButton("Xác nhận");
        btnConfirmOTP.setPreferredSize(new Dimension(100, 35));
        btnConfirmOTP.addActionListener(this);
        jpCard_2.add(lblNhapOTP);
        jpCard_2.add(txtOTP);
        jpCard_2.add(btnConfirmOTP);

        // Step 3
        jpCard_3 = new JPanel(new FlowLayout(2, 10, 10));
        jpCard_3.setBackground(new Color(255, 255, 255));
        lblNhapPassword = new JLabel();
        lblNhapPassword.setText("Nhập mật khẩu mới");

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new java.awt.Dimension(350, 35));

        btnChangePass = new JButton("Xác nhận");
        btnChangePass.setPreferredSize(new Dimension(100, 35));
        btnChangePass.addActionListener(this);
        jpCard_3.add(lblNhapPassword);
        jpCard_3.add(txtPassword);
        jpCard_3.add(btnChangePass);

        jpMain.add(jpCard_1);
        jpMain.add(jpCard_2);
        jpMain.add(jpCard_3);

        this.getContentPane().add(jpTop, BorderLayout.NORTH);
        this.getContentPane().add(jpMain, BorderLayout.CENTER);

    }

    private void sendMail() {
        String email = txtEmail.getText().trim();

        if (email.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống email");
            return;
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email");
            return;
        }

        EmployeeDTO epDTO = epBUS.getEmployeeByEmail(email);
        if (epDTO == null) {
            JOptionPane.showMessageDialog(this, "Tài khoản của email này không tồn tại trên hệ thống");
            return;
        }

        CardLayout c = (CardLayout) jpMain.getLayout();
        c.next(jpMain);
        emailCheck = email;

        String otp = Tool.randomID();
        SendEmailSMTP.sendOTP(email, otp);
        epBUS.sendOTP(email, Integer.parseInt(otp));
    }

    private void confirmOTP() {
        String otp = txtOTP.getText().trim();

        if (otp.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống mã OTP");
            return;
        }

        Pattern digitPattern = Pattern.compile("\\d{6}");
        Matcher matcher = digitPattern.matcher(otp);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã OTP có 6 chữ số!");
            return;
        }

        boolean check = epBUS.checkOTP(emailCheck, Integer.parseInt(otp));
        if (check) {
            CardLayout c = (CardLayout) jpMain.getLayout();
            c.next(jpMain);
        } else {
            JOptionPane.showMessageDialog(this, "Mã OTP không khớp");
        }
    }

    private void changePass() {
        @SuppressWarnings("deprecation")
        String pass = txtPassword.getText().trim();
        if (pass.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu");
            return;
        }

        String password = BCrypt.hashpw(pass, BCrypt.gensalt(12));
        epBUS.updatePassword(emailCheck, password);
        epBUS.sendOTP(emailCheck, 0);
        JOptionPane.showMessageDialog(this, "Thay đổi mật khẩu thành công");
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSendMail) {
            sendMail();
        } else if (e.getSource() == btnConfirmOTP) {
            confirmOTP();
        } else if (e.getSource() == btnChangePass) {
            changePass();
        }
    }

}
