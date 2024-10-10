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
import helper.Tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 * @author ASUS
 */
public class EmployeeDialog extends JDialog {

    private final EmployeeBUS epBUS;
    private EmployeeDTO epDTO;
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnUpdate, btnExit;
    private InputForm employeeName, phone, email, password, salary;
    private SelectForm permissionName, status;
    private ButtonGroup gender;
    private JRadioButton male;
    private JRadioButton female;
    private InputDate jcBd;
    private final PermissionBUS pmsBUS;

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

        String permissionNameStr = pmsBUS.getNameByID(epDTO.getPermissionID());
        permissionName.setSelectedItem(permissionNameStr);
        employeeName.setText(epDTO.getEmployeeName());
        email.setText(epDTO.getEmail());

        if (epDTO.getGender().equals("Nam")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        jcBd.setDate(epDTO.getDOB());
        phone.setText(epDTO.getPhone());
        salary.setText(epDTO.getSalary() + "");
        status.setSelectedItem(epDTO.getStatus());

        password.setVisible(false);
        email.setVisible(false);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
        password = new InputForm("Mật khẩu", "password");
        password.setPreferredSize(inputSize);
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
        main.add(password);
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
        btnAdd = new ButtonCustom("Thêm người dùng", "success", 14);
        btnUpdate = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);

        btnExit.addActionListener((ActionEvent e) -> {
            dispose();
        });

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

        bottom.add(btnExit);

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
                    String employeeID = "EP" + Tool.randomID();
                    String permissionIDStr = pmsBUS.getIDByName(permissionName.getValue());
                    String txtName = employeeName.getText();
                    Date birthDay = new Date(jcBd.getDate().getTime());
                    String txtPhone = phone.getText();
                    String passwordStr = BCrypt.hashpw(password.getPass(), BCrypt.gensalt(12));
                    String txtEmail = email.getText();
                    String statusStr = status.getValue();
                    String salaryStr = salary.getText();
                    java.sql.Timestamp hiredate = new java.sql.Timestamp(System.currentTimeMillis());

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
