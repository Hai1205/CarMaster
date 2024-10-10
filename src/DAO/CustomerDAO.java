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
public class CustomerDAO {

    public static ArrayList<CustomerDTO> getList() {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM customer";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String customerID = resultSet.getString("customerID");
                String customerName = resultSet.getString("customerName");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                list.add(new CustomerDTO(customerID, customerName, address, phone));
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

    public static String[] getListCustomerName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT customerName FROM customer";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("customerName"));
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

    public static CustomerDTO getCustomerByID(String customerID) {
        CustomerDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM customer WHERE customerID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                return new CustomerDTO(customerID, customerName, address, phone);
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

    public static ArrayList<CustomerDTO> search(String info) {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT * FROM customer WHERE customerID LIKE ? OR customerName LIKE ? OR phone LIKE ? OR address LIKE ?";
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
            } else {
                query = "SELECT * FROM customer";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String customerID = resultSet.getString("customerID");
                String customerName = resultSet.getString("customerName");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                list.add(new CustomerDTO(customerID, customerName, address, phone));
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

    public static void insert(CustomerDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "INSERT INTO customer (customerID, customerName, address, phone) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getCustomerID());
            pstmt.setString(2, epDTO.getCustomerName());
            pstmt.setString(3, epDTO.getAddress());
            pstmt.setString(4, epDTO.getPhone());

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

    public static void update(CustomerDTO epDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE customer SET customerName = ?, address = ?, phone = ? WHERE customerID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, epDTO.getCustomerName());
            pstmt.setString(2, epDTO.getAddress());
            pstmt.setString(3, epDTO.getPhone());
            pstmt.setString(4, epDTO.getCustomerID());

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
