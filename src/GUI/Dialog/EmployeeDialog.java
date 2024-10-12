package GUI.Dialog;

import BUS.EmployeeBUS;
import BUS.PermissionBUS;
import DTO.EmployeeDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import helper.BCrypt;
import helper.SendEmailSMTP;
import helper.Tool;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Date;

import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;

/**
 *
 * @author ASUS
 */
public class EmployeeDialog extends JDialog {

    private final EmployeeBUS epBUS;
    private EmployeeDTO epDTO;
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnUpdate;
    private InputForm employeeName, phone, email, salary;
    private JLabel password;
    private SelectForm permissionName, status;
    private ButtonGroup gender;
    private JRadioButton male;
    private JRadioButton female;
    private InputDate jcBd;
    private final PermissionBUS pmsBUS;
    // private final JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private EmployeeDialog epDialog;

    public EmployeeDialog(EmployeeBUS epBUS, JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        this.pmsBUS = new PermissionBUS();
        this.epBUS = epBUS;
        init(title, type);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public EmployeeDialog(EmployeeBUS epBUS, JFrame owner, boolean modal, String title, String type,
            EmployeeDTO epDTO) {
        super(owner, title, modal);
        this.pmsBUS = new PermissionBUS();
        this.epBUS = epBUS;
        this.epDTO = epDTO;
        init(title, type);

        setEmployee(title);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public EmployeeDTO getEmployee() {
        return epDTO;
    }

    public void resetChange() {
        epDTO = epBUS.getEmployeeByID(epDTO.getEmployeeID());
    }

    private void setEmployee(String title) {
        String permissionNameStr = pmsBUS.getNameByID(epDTO.getPermissionID());
        permissionName.setSelectedItem(permissionNameStr);
        employeeName.setText(epDTO.getEmployeeName());
        email.setEditable(false);
        email.setText(epDTO.getEmail());

        if (title.equals("Thông tin tài khoản")) {
            epDialog = this;
            password.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    new MyAccount(null, epDialog, "Đổi mật khẩu", true);
                }
            });
        }

        if (epDTO.getGender().equals("Nam")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        jcBd.setDate(epDTO.getDOB());
        phone.setText(epDTO.getPhone());
        salary.setText(epDTO.getSalary() + "");
        status.setSelectedItem(epDTO.getStatus());
    }

    public void init(String title, String type) {
        Dimension inputSize = new Dimension(100, 110);
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);

        employeeName = new InputForm("Họ và tên");
        email = new InputForm("Email");

        password = new JLabel("Đổi mật khẩu");
        password.setPreferredSize(new Dimension(320, 40));
        password.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        
        permissionName = new SelectForm("Nhóm quyền", pmsBUS.getPermission());
        permissionName.setPreferredSize(inputSize);
        
        phone = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        
        male = new JRadioButton("Nam");
        female = new JRadioButton("Nữ");
        gender = new ButtonGroup();
        gender.add(male);
        gender.add(female);
        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel jgender = new JPanel(new GridLayout(1, 2));
        jgender.setSize(new Dimension(500, 80));
        jgender.setBackground(Color.white);
        jgender.add(male);
        jgender.add(female);
        JLabel labelGender = new JLabel("Giới tính");
        jpanelG.add(labelGender);
        jpanelG.add(jgender);
        
        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, -5, 10, 10));
        jpaneljd.setSize(new Dimension(200, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);
        jcBd = new InputDate("Ngày sinh");
        jcBd.setPreferredSize(new Dimension(405, 100));
        jpaneljd.add(jcBd);
        
        salary = new InputForm("Lương tháng");
        PlainDocument slr = (PlainDocument) salary.getTxtForm().getDocument();
        slr.setDocumentFilter((new NumericDocumentFilter()));
        
        status = new SelectForm("Trạng thái", new String[] { "Hoạt động", "Ngưng hoạt động" });
        status.setPreferredSize(inputSize);
        
        // Thêm các thành phần vào main panel
        main.add(permissionName);
        main.add(employeeName);
        main.add(email);
        if (title.equals("Thông tin tài khoản")) {
            main.add(password);
        }
        main.add(phone);
        main.add(jpanelG);
        main.add(jpaneljd);
        main.add(salary);
        main.add(status);
        
        // Cuộn trang khi nội dung quá dài
        JScrollPane scrollPane = new JScrollPane(main);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
        
        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm", "success", 14);
        btnUpdate = new ButtonCustom("Cập nhật", "success", 14);
        
        btnAdd.addActionListener((ActionEvent e) -> {
            add();
        });

        btnUpdate.addActionListener((ActionEvent e) -> {
            update();
        });

        switch (type) {
            case "create" ->
                bottom.add(btnAdd);
            case "update" ->
                bottom.add(btnUpdate);
            default ->
                throw new AssertionError();
        }

        this.add(titlePage, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    }

    private void update() {
        try {
            if (ValidationInput()) {
                try {
                    String permissionIDStr = pmsBUS.getIDByName(permissionName.getValue());
                    String employeeNameStr = employeeName.getText();
                    Date birthDay = new Date(jcBd.getDate().getTime());
                    String phoneStr = phone.getText();
                    String genderStr = "";
                    if (male.isSelected()) {
                        genderStr = "Nam";
                    } else if (female.isSelected()) {
                        genderStr = "Nữ";
                    }
                    String salaryStr = salary.getText();
                    String statusStr = (String) status.getValue();

                    epBUS.update(new EmployeeDTO(epDTO.getEmployeeID(), permissionIDStr, employeeNameStr,
                            epDTO.getHireDate(), genderStr, phoneStr, epDTO.getEmail(), epDTO.getPassword(),
                            statusStr, birthDay, Integer.parseInt(salaryStr), 0));
                    this.dispose();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void add() {
        try {
            if (ValidationInput()) {
                if (checkEmail(email.getText())) {
                    String genderStr = "";
                    if (male.isSelected()) {
                        genderStr = "Nam";
                    } else if (female.isSelected()) {
                        genderStr = "Nữ";
                    }
                    String employeeID = epBUS.createID();
                    String permissionIDStr = pmsBUS.getIDByName(permissionName.getValue());
                    String txtName = employeeName.getText();
                    Date birthDay = new Date(jcBd.getDate().getTime());
                    String txtPhone = phone.getText();
                    String yourPassword = Tool.createPassword();
                    String passwordStr = BCrypt.hashpw(yourPassword, BCrypt.gensalt(12));
                    String txtEmail = email.getText();
                    String statusStr = status.getValue();
                    String salaryStr = salary.getText();
                    java.sql.Timestamp hiredate = new java.sql.Timestamp(System.currentTimeMillis());

                    SendEmailSMTP.sendPassword(txtEmail, yourPassword);
                    epBUS.insert(new EmployeeDTO(employeeID, permissionIDStr, txtName, hiredate, genderStr, txtPhone,
                            txtEmail, passwordStr, statusStr, birthDay, Integer.parseInt(salaryStr), 0));
                    this.dispose();
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private boolean ValidationInput() throws ParseException {
        if (Tool.isEmpty(employeeName.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (employeeName.getText().length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên ít nhất 6 kí tự!");
            return false;
        } else if (Tool.isEmpty(email.getText()) || !Tool.isEmail(email.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Tool.isEmpty(phone.getText()) && !Tool.isPhoneNumber(phone.getText())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (jcBd.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            return false;
        } else if (!male.isSelected() && !female.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return false;
        }

        return true;
    }

    public boolean checkEmail(String email) {
        if (!(epBUS.getEmployeeByEmail(email) == null)) {
            JOptionPane.showMessageDialog(this, "Email này đã được sử dụng!");
            return false;
        }
        return true;
    }
}
