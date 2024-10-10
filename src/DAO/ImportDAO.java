/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ImportDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Tran Nhat Sinh
 */
public class ImportDAO {

    public static void insert(ImportDTO ipDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();
            String sql = "INSERT INTO import(importID, supplierID, employeeID, totalCost) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, ipDTO.getImportID());
            pstmt.setString(2, ipDTO.getImportID());
            pstmt.setString(3, ipDTO.getSupplierID());
            pstmt.setLong(4, ipDTO.getTotalCost());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public static ArrayList<ImportDTO> getList() {
        ArrayList<ImportDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM import ORDER BY importID DESC";
            pstmt = connection.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String importID = resultSet.getString("importID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                String supplierID = resultSet.getString("supplierID");
                String employeeID = resultSet.getString("employeeID");
                long totalCost = resultSet.getLong("totalCost");

                list.add(new ImportDTO(importID, supplierID, employeeID, creationDate, totalCost));
            }
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
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ImportDTO getImportByID(String importID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM import WHERE importID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, importID);
            resultSet = (ResultSet) pstmt.executeQuery();
            while (resultSet.next()) {
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                String supplierID = resultSet.getString("supplierID");
                String employeeID = resultSet.getString("employeeID");
                long totalCost = resultSet.getLong("totalCost");
                return new ImportDTO(importID, supplierID, employeeID, creationDate, totalCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<ImportDTO> statistical(long min, long max) {
        ArrayList<ImportDTO> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM import WHERE totalCost BETWEEN ? AND ?";
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setLong(1, min);
            pstmt.setLong(2, max);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String importID = resultSet.getString("importID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                String supplierID = resultSet.getString("supplierID");
                String employeeID = resultSet.getString("employeeID");
                long totalCost = resultSet.getLong("totalCost");

                result.add(new ImportDTO(supplierID, importID, employeeID, creationDate, totalCost));
            }
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
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ArrayList<ImportDTO> search(String info) {
        ArrayList<ImportDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT import.importID, supplier.supplierID, employee.employeeID, import.creationDate, import.totalCost " +
               "FROM import " +
               "JOIN supplier ON import.supplierID = supplier.supplierID " +
               "JOIN employee ON employee.employeeID = import.employeeID " +
               "WHERE import.importID LIKE ? " +
               "OR supplier.supplierName LIKE ? " +
               "OR employee.employeeName LIKE ? " +
               "OR import.creationDate LIKE ? " +
               "OR import.totalCost LIKE ?";

                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
                pstmt.setString(5, searchValue);
            } else {
                query = "SELECT * FROM import";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String importID = resultSet.getString("importID");
                String supplierID = resultSet.getString("supplierID");
                String employeeID = resultSet.getString("employeeID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                Long totalCost = resultSet.getLong("totalCost");

                list.add(new ImportDTO(importID, supplierID, employeeID, creationDate, totalCost));
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
}
