/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ProductDAO {

    public static ArrayList<ProductDTO> getList() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM product";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                String supplierID = resultSet.getString("supplierID");
                String productName = resultSet.getString("productName");
                String productImg = resultSet.getString("productImg");
                String status = resultSet.getString("status");
                int quantity = resultSet.getInt("quantity");
                long basicPrice = resultSet.getLong("basicPrice");
                long sellPrice = resultSet.getLong("sellPrice");
                
                list.add(new ProductDTO(productID, supplierID, productName, productImg, status, quantity, basicPrice, sellPrice));
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

    public static ArrayList<ProductDTO> getListBySupplierID(String supplierID) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM product WHERE supplierID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supplierID);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                String productName = resultSet.getString("productName");
                String productImg = resultSet.getString("productImg");
                String status = resultSet.getString("status");
                int quantity = resultSet.getInt("quantity");
                long basicPrice = resultSet.getLong("basicPrice");
                long sellPrice = resultSet.getLong("sellPrice");
                
                list.add(new ProductDTO(productID, supplierID, productName, productImg, status, quantity, basicPrice, sellPrice));
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
    
    public static ProductDTO getProductByID(String productID) {
        ProductDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM product WHERE productID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, productID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String supplierID = resultSet.getString("supplierID");
                String productName = resultSet.getString("productName");
                String productImg = resultSet.getString("productImg");
                String status = resultSet.getString("status");
                int quantity = resultSet.getInt("quantity");
                long basicPrice = resultSet.getLong("basicPrice");
                long sellPrice = resultSet.getLong("sellPrice");
                
                return new ProductDTO(productID, supplierID, productName, productImg, status, quantity, basicPrice, sellPrice);
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
    
    public static void update(ProductDTO pdDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE product SET productName = ?, sellPrice = ?, status = ?, productImg = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, pdDTO.getProductName());
            pstmt.setLong(2, pdDTO.getSellPrice());
            pstmt.setString(3, pdDTO.getStatus());
            pstmt.setString(4, pdDTO.getProductImg());

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
    
    public static ArrayList<ProductDTO> search(String info) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT * FROM product JOIN supplier ON product.supplierID = supplier.supplierID WHERE supplierName LIKE ? OR productID LIKE ? OR productName LIKE ? OR quantity LIKE ? OR basicPrice LIKE ? OR sellPrice LIKE ? OR status LIKE ?";
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
                pstmt.setString(5, searchValue);
                pstmt.setString(6, searchValue);
                pstmt.setString(7, searchValue);
            } else {
                query = "SELECT * FROM product";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                String supplierID = resultSet.getString("supplierID");
                String productName = resultSet.getString("productName");
                String productImg = resultSet.getString("productImg");
                String status = resultSet.getString("status");
                int quantity = resultSet.getInt("quantity");
                long basicPrice = resultSet.getLong("basicPrice");
                long sellPrice = resultSet.getLong("sellPrice");
                
                list.add(new ProductDTO(productID, supplierID, productName, productImg, status, quantity, basicPrice, sellPrice));
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

    public static void updateQuantity(String productID, int quantity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE employee SET quantity = ? WHERE productID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, quantity);
            pstmt.setString(2, productID);

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
    
    public static void insert(ProductDTO pdDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "INSERT INTO product (productID, supplierID, productName, status, productImg) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, pdDTO.getProductID());
            pstmt.setString(2, pdDTO.getSupplierID());
            pstmt.setString(3, pdDTO.getProductName());
            pstmt.setString(4, pdDTO.getStatus());
            pstmt.setString(5, pdDTO.getProductImg());

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
