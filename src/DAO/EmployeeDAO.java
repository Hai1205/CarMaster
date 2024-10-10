/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class EmployeeDAO {

    public static ArrayList<EmployeeDTO> getList(String status) {
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            if (status.equals("Còn làm việc")) {
                query = "SELECT * FROM employee WHERE status = 'Còn làm việc'";
            } else {
                query = "SELECT * FROM employee";
            }
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String employeeID = resultSet.getString("employeeID");
                String permissionID = resultSet.getString("permissionID");
                String employeeName = resultSet.getString("employeeName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                Date DOB = resultSet.getDate("DOB");
                Timestamp hiredate = resultSet.getTimestamp("hiredate");
                int salary = resultSet.getInt("salary");
                int OTP = resultSet.getInt("OTP");
                status = resultSet.getString("status");
                list.add(new EmployeeDTO(employeeID, permissionID, employeeName, hiredate, gender, phone, email, password, status, DOB, salary, OTP));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static EmployeeDTO getEmployeeByEmail(String email) {
        EmployeeDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM employee WHERE email = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String employeeID = resultSet.getString("employeeID");
                String permissionID = resultSet.getString("permissionID");
                String employeeName = resultSet.getString("employeeName");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                Date DOB = resultSet.getDate("DOB");
                Timestamp hiredate = resultSet.getTimestamp("hiredate");
                int salary = resultSet.getInt("salary");
                int OTP = resultSet.getInt("OTP");
                String status = resultSet.getString("status");

                epDTO = new EmployeeDTO(employeeID, permissionID, employeeName, hiredate, gender, phone, email, password, status, DOB, salary, OTP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return epDTO;
    }

    public static EmployeeDTO getEmployeeByID(String employeeID) {
        EmployeeDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM employee WHERE employeeID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, employeeID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String permissionID = resultSet.getString("permissionID");
                String employeeName = resultSet.getString("employeeName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                Timestamp DOB = resultSet.getTimestamp("DOB");
                Timestamp hiredate = resultSet.getTimestamp("hiredate");
                int salary = resultSet.getInt("salary");
                int OTP = resultSet.getInt("OTP");
                String status = resultSet.getString("status");

                epDTO = new EmployeeDTO(employeeID, permissionID, employeeName, hiredate, gender, phone, email, password, status, DOB, salary, OTP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return epDTO;
    }

    public static ArrayList<EmployeeDTO> search(String info) {
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT * FROM employee WHERE employeeID LIKE ? OR permissionID LIKE ? OR email LIKE ? OR gender LIKE ? OR employeeName LIKE ? OR DOB LIKE ?  OR YEAR(DOB) LIKE ? OR MONTH(DOB) LIKE ? OR salary LIKE ? OR phone LIKE ? OR hireDate LIKE ? OR YEAR(hireDate) LIKE ? OR MONTH(hireDate) LIKE ? OR status LIKE ?";
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
                pstmt.setString(5, searchValue);
                pstmt.setString(6, searchValue);
                pstmt.setString(7, searchValue);
                pstmt.setString(8, searchValue);
                pstmt.setString(9, searchValue);
                pstmt.setString(10, searchValue);
                pstmt.setString(11, searchValue);
                pstmt.setString(12, searchValue);
                pstmt.setString(13, searchValue);
                pstmt.setString(14, searchValue);
            } else {
                query = "SELECT * FROM employee";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String employeeID = resultSet.getString("employeeID");
                String permissionID = resultSet.getString("permissionID");
                String employeeName = resultSet.getString("employeeName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                Timestamp DOB = resultSet.getTimestamp("DOB");
                Timestamp hiredate = resultSet.getTimestamp("hiredate");
                int salary = resultSet.getInt("salary");
                int OTP = resultSet.getInt("OTP");
                String status = resultSet.getString("status");
                list.add(new EmployeeDTO(employeeID, permissionID, employeeName, hiredate, gender, phone, email, password, status, DOB, salary, OTP));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void insert(EmployeeDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "INSERT INTO employee (employeeID, employeeName, hireDate, gender, DOB, phone, salary, email, password, permissionID, OTP, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getEmployeeID());
            pstmt.setString(2, epDTO.getEmployeeName());
            if (epDTO.getHireDate() != null) {
                pstmt.setTimestamp(3, new java.sql.Timestamp(epDTO.getHireDate().getTime()));
            }
            pstmt.setString(4, epDTO.getGender());
            java.util.Date dobUtilDate = epDTO.getDOB();
            if (dobUtilDate != null) {
                pstmt.setDate(5, new java.sql.Date(dobUtilDate.getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }

            pstmt.setString(6, epDTO.getPhone());
            pstmt.setInt(7, epDTO.getSalary());
            pstmt.setString(8, epDTO.getEmail());
            pstmt.setString(9, epDTO.getPassword());
            pstmt.setString(10, epDTO.getPermissionID());
            pstmt.setInt(11, epDTO.getOTP());
            pstmt.setString(12, epDTO.getStatus());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(EmployeeDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE employee SET employeeName = ?, hireDate = ?, gender = ?, DOB = ?, phone = ?, salary = ?, email = ?, password = ?, permissionID = ?, OTP = ?, status = ? WHERE employeeID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getEmployeeName());
            pstmt.setTimestamp(2, epDTO.getHireDate());
            pstmt.setString(3, epDTO.getGender());
            java.util.Date dobUtilDate = epDTO.getDOB();
            pstmt.setDate(4, new java.sql.Date(dobUtilDate.getTime()));
            pstmt.setString(5, epDTO.getPhone());
            pstmt.setInt(6, epDTO.getSalary());
            pstmt.setString(7, epDTO.getEmail());
            pstmt.setString(8, epDTO.getPassword());
            pstmt.setString(9, epDTO.getPermissionID());
            pstmt.setInt(10, epDTO.getOTP());
            pstmt.setString(11, epDTO.getStatus());
            pstmt.setString(12, epDTO.getEmployeeID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePassword(String email, String password) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE employee SET password = ? WHERE email = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, password);
            pstmt.setString(2, email);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendOTP(String email, int OTP) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = Database.getConnection();
            String sql = "UPDATE employee SET OTP = ? WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, OTP);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkOTP(String email, int OTP) {
        boolean check = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = Database.getConnection();
            String sql = "SELECT * FROM employee where email = ? and OTP = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setInt(2, OTP);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }
}
