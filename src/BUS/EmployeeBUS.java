/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.EmployeeDAO;
import DTO.EmployeeDTO;
import GUI.Dialog.EmployeeDialog;
import GUI.LogIn;
import GUI.Main;
import GUI.Panel.EmployeePanel;
import helper.BCrypt;
import helper.Formater;
import helper.Tool;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class EmployeeBUS implements ActionListener, DocumentListener {

    private LogIn logIn;
    private EmployeePanel epPanel;
    public ArrayList<EmployeeDTO> epList;

    public String createID() {
        String ID;
        do {
            ID = "EP" + Tool.randomID();
        } while (getEmployeeByID(ID) != null);
        return ID;
    }

    public EmployeeBUS() {
        epList = EmployeeDAO.getList("");
    }

    public EmployeeBUS(EmployeePanel epPanel) {
        this.epPanel = epPanel;
        epList = EmployeeDAO.getList("");
    }

    public EmployeeBUS(LogIn logIn) {
        this.logIn = logIn;
        epList = EmployeeDAO.getList("");
    }

    public EmployeePanel getEmployeePanel() {
        return epPanel;
    }

    public void insert(EmployeeDTO epDTO) {
        EmployeeDAO.insert(epDTO);
    }

    public EmployeeDTO getEmployeeByID(String employeeID) {
        return EmployeeDAO.getEmployeeByID(employeeID);
    }

    public String getNameByID(String employeeID) {
        return getEmployeeByID(employeeID).getEmployeeName();
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        return EmployeeDAO.getEmployeeByEmail(email);
    }

    public ArrayList<EmployeeDTO> getList(String status) {
        return EmployeeDAO.getList(status);
    }

    public void update(EmployeeDTO epDTO) {
        EmployeeDAO.update(epDTO);
    }

    public void updatePassword(String email, String password) {
        EmployeeDAO.updatePassword(email, password);
    }

    public CompletableFuture<Void> sendOTP(String email, int OTP) {
        EmployeeDAO.sendOTP(email, OTP);
        return null;
    }

    public boolean checkOTP(String email, int OTP) {
        return EmployeeDAO.checkOTP(email, OTP);
    }

    public void loadNewDataIntoTable() {
        epList = EmployeeDAO.getList("");
        loadDataIntoTable(epList);
    }

    public void loadDataIntoTable(ArrayList<EmployeeDTO> list) {
        epPanel.getTblModel().setRowCount(0);
        if (list == null) {
            return;
        }

        for (EmployeeDTO epDTO : list) {
            epPanel.getTblModel().addRow(new Object[] {
                    epDTO.getEmployeeID(), epDTO.getPermissionID(), epDTO.getEmployeeName(), epDTO.getEmail(),
                    epDTO.getGender(), epDTO.getDOB(), epDTO.getPhone(), epDTO.getHireDate(),
                    Formater.FormatVND(epDTO.getSalary()), epDTO.getStatus()
            });
        }
    }

    public void checkLogin() throws UnsupportedLookAndFeelException {
        String emailCheck = logIn.getTxtEmail();
        String passwordCheck = logIn.getTxtPassword();

        if (emailCheck.equals("") || passwordCheck.equals("")) {
            JOptionPane.showMessageDialog(logIn, "Vui lòng nhập thông tin đầy đủ", "Cảnh báo!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        EmployeeDTO epDTO = EmployeeDAO.getEmployeeByEmail(emailCheck);

        if (epDTO == null) {
            JOptionPane.showMessageDialog(logIn, "Tài khoản của bạn không tồn tại trên hệ thống", "Cảnh báo!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (epDTO.getStatus().equals("Đã nghỉ việc")) {
            JOptionPane.showMessageDialog(logIn, "Tài khoản của bạn đang bị khóa", "Cảnh báo!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra mật khẩu
        if (!BCrypt.checkpw(passwordCheck, epDTO.getPassword())) {
            JOptionPane.showMessageDialog(logIn, "Mật khẩu không khớp", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        logIn.dispose();
        Main main = new Main(epDTO);
        main.setVisible(true);
    }

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS;
        BufferedInputStream excelBIS;
        XSSFWorkbook excelJTableImport;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    XSSFRow excelRow = excelSheet.getRow(row);

                    String employeeID = excelRow.getCell(0).getStringCellValue();
                    String permissionID = excelRow.getCell(1).getStringCellValue();
                    String employeeName = excelRow.getCell(2).getStringCellValue();
                    String email = excelRow.getCell(3).getStringCellValue();
                    String gender = excelRow.getCell(4).getStringCellValue();

                    Cell birthCell = excelRow.getCell(5);
                    Date dob = null;
                    if (birthCell != null && birthCell.getCellType() == CellType.NUMERIC) {
                        dob = birthCell.getDateCellValue();
                    } else if (birthCell != null && birthCell.getCellType() == CellType.STRING) {
                        String dateString = birthCell.getStringCellValue();
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            dob = formatter.parse(dateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    java.sql.Date birth = (dob != null) ? new java.sql.Date(dob.getTime()) : null;

                    String phone = excelRow.getCell(6).getStringCellValue();

                    Timestamp hire = null;
                    Cell hireCell = excelRow.getCell(7);
                    if (hireCell != null) {
                        if (hireCell.getCellType() == CellType.NUMERIC) {
                            // Nếu ô là kiểu số, tức là kiểu ngày tháng
                            java.util.Date hireDate = hireCell.getDateCellValue();
                            hire = new Timestamp(hireDate.getTime());
                        } else if (hireCell.getCellType() == CellType.STRING) {
                            // Nếu ô là kiểu chuỗi, ta cần chuyển đổi chuỗi ngày
                            String hireDateString = hireCell.getStringCellValue();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Thay đổi định
                                                                                                      // dạng nếu cần
                            try {
                                java.util.Date hireDate = formatter.parse(hireDateString);
                                hire = new Timestamp(hireDate.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    int salary = 0;
                    Cell salaryCell = excelRow.getCell(8);

                    if (salaryCell != null) {
                        if (salaryCell.getCellType() == CellType.NUMERIC) {
                            // Nếu ô là kiểu số
                            salary = (int) salaryCell.getNumericCellValue();
                        } else if (salaryCell.getCellType() == CellType.STRING) {
                            // Nếu ô là kiểu chuỗi, ta cần chuyển đổi chuỗi sang số
                            String salaryString = salaryCell.getStringCellValue();
                            try {
                                salary = Integer.parseInt(salaryString); // Chuyển đổi chuỗi thành số
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                salary = 0; // Có thể đặt lương về 0 hoặc một giá trị mặc định nào đó
                            }
                        }
                    }

                    String status = excelRow.getCell(9).getStringCellValue();
                    String password = excelRow.getCell(10).getStringCellValue();

                    if (Tool.isEmpty(employeeID) || Tool.isEmpty(permissionID) || Tool.isEmpty(employeeName)
                            || Tool.isEmpty(email) || !Tool.isEmail(email) || Tool.isEmpty(password)
                            || Tool.isEmpty(phone) || !Tool.isPhoneNumber(phone)
                            || Tool.isEmpty(gender) || salary == 0 || Tool.isEmpty(status)) {
                        check = 0;
                    }
                    if (check == 0) {
                        k++;
                    } else {
                        EmployeeDAO.insert(new EmployeeDTO(employeeID, permissionID, employeeName, hire, gender, phone,
                                email, password, status, birth, salary, 0));
                    }
                }

                JOptionPane.showMessageDialog(null, "Nhập file Excel thành công");

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel");
            }
        }

        if (k != 0) {
            JOptionPane.showMessageDialog(null, "Những dữ liệu không chuẩn không được thêm vào");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        switch (btn) {
            case "THÊM" -> {
                new EmployeeDialog(this, epPanel.getOwner(), true, "Thêm nhân viên", "create");
            }
            case "CẬP NHẬT" -> {
                int index = epPanel.getRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa");
                } else {
                    new EmployeeDialog(this, epPanel.getOwner(), true, "Sửa nhân viên", "update",
                            epPanel.getEmployee());
                }
            }
            case "NHẬP EXCEL" -> {
                importExcel();
            }
            case "XUẤT EXCEL" -> {
                String[] header = new String[] { "Mã nhân viên", "Mã nhóm quyên", "Họ và tên                     ",
                        "Email                                ", "Giới tính", "Ngày Sinh", "Số điện thoại",
                        "Ngày tuyển       ", "Lương(VND)", "Trạng thái    ",
                        "Mật khẩu                                                                           " };
                exportExcel(epList, header);
            }
        }
        loadNewDataIntoTable();
    }

    private static void writeHeader(String[] list, Sheet sheet, int rowIndex) {
        CellStyle cellStyle = createStyleForHeader(sheet);
        Row row = sheet.createRow(rowIndex);
        Cell cell;
        for (int i = 0; i < list.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(list[i]);
            sheet.autoSizeColumn(i);
        }
    }

    public int getIndexById(String employeeID) {
        int i = 0;
        int index = -1;
        int size = this.epList.size();
        while (i < size && index == -1) {
            if (this.epList.get(i).getEmployeeID().equals(employeeID)) {
                index = i;
            } else {
                i++;
            }
        }
        return index;
    }

    public String[] getListEmployeeName() {
        int size = epList.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = epList.get(i).getEmployeeName();
        }
        return result;
    }

    public int getIndex() {
        return epPanel.getRow();
    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportExcel(ArrayList<EmployeeDTO> list, String[] header) {
        try {
            if (!list.isEmpty()) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(epPanel.getOwner());
                File saveFile = jFileChooser.getSelectedFile();
                if (saveFile != null) {
                    saveFile = new File(saveFile.toString() + ".xlsx");
                    FileOutputStream out;
                    try (Workbook wb = new XSSFWorkbook()) {
                        Sheet sheet = wb.createSheet("Nhân viên");
                        writeHeader(header, sheet, 0);
                        int rowIndex = 1;
                        for (EmployeeDTO epDTO : list) {
                            Row row = sheet.createRow(rowIndex++);
                            writeEmployee(epDTO, row);
                        }
                        out = new FileOutputStream(new File(saveFile.toString()));
                        wb.write(out);
                    }
                    out.close();
                    openFile(saveFile.toString());
                }
            }
        } catch (HeadlessException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeEmployee(EmployeeDTO epDTO, Row row) {
        CellStyle cellStyleFormatNumber = null;
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");

            // Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }
        Cell cell = row.createCell(0);
        cell.setCellValue(epDTO.getEmployeeID());

        cell = row.createCell(1);
        cell.setCellValue(epDTO.getPermissionID());

        cell = row.createCell(2);
        cell.setCellValue(epDTO.getEmployeeName());

        cell = row.createCell(3);
        cell.setCellValue(epDTO.getEmail());

        cell = row.createCell(4);
        cell.setCellValue(epDTO.getGender());

        cell = row.createCell(5);
        cell.setCellValue("" + epDTO.getDOB());

        cell = row.createCell(6);
        cell.setCellValue(epDTO.getPhone());

        cell = row.createCell(7);
        cell.setCellValue("" + epDTO.getHireDate());

        cell = row.createCell(8);
        cell.setCellValue("" + epDTO.getSalary());

        cell = row.createCell(9);
        cell.setCellValue("" + epDTO.getStatus());

        cell = row.createCell(10);
        cell.setCellValue("" + epDTO.getPassword());
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    public ArrayList<EmployeeDTO> search(String info) {
        return EmployeeDAO.search(info);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
