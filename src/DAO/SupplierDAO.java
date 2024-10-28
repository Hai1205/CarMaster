/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class SupplierDAO {

    public static ArrayList<SupplierDTO> getList() {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM supplier ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String supplierID = resultSet.getString("supplierID");
                String supplierName = resultSet.getString("supplierName");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                list.add(new SupplierDTO(supplierID, supplierName, address, email, phone));
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

    public static String[] getListSupplierName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT supplierName FROM supplier ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("supplierName"));
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

        return list.toArray(new String[0]);
    }

    public static String getIDByName(String supplierName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT supplierID FROM supplier WHERE supplierName = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supplierName);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("supplierID");
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
        return null;
    }

    public static SupplierDTO getSupplierByID(String supplierID) {
        SupplierDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM supplier WHERE supplierID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supplierID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String supplierName = resultSet.getString("supplierName");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                return new SupplierDTO(supplierID, supplierName, email, address, phone);
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

    public static ArrayList<SupplierDTO> search(String info) {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT * FROM supplier WHERE supplierID LIKE ? OR supplierName LIKE ? OR phone LIKE ? OR email LIKE ? OR address LIKE ? ORDER BY creationDate DESC";
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
                pstmt.setString(5, searchValue);
            } else {
                query = "SELECT * FROM supplier ORDER BY creationDate DESC";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String supplierID = resultSet.getString("supplierID");
                String supplierName = resultSet.getString("supplierName");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                list.add(new SupplierDTO(supplierID, supplierName, address, email, phone));
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

    public static void insert(SupplierDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "INSERT INTO supplier (supplierID, supplierName, email, address, phone) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getSupplierID());
            pstmt.setString(2, epDTO.getSupplierName());
            pstmt.setString(3, epDTO.getEmail());
            pstmt.setString(4, epDTO.getAddress());
            pstmt.setString(5, epDTO.getPhone());

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

    public static void update(SupplierDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE supplier SET supplierName = ?, address = ?, phone = ?, email = ? WHERE supplierID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getSupplierName());
            pstmt.setString(2, epDTO.getAddress());
            pstmt.setString(3, epDTO.getPhone());
            pstmt.setString(4, epDTO.getEmail());
            pstmt.setString(5, epDTO.getSupplierID());

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
}