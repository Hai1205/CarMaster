/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ProductDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */

public class ProductDetailDAO {
    public static ProductDetailDTO getProductDetailByID(String productID) {
        ProductDetailDTO epDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM productdetail WHERE productID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, productID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String brandName = resultSet.getString("brandName");
                String styleName = resultSet.getString("styleName");
                String fuelType = resultSet.getString("fuelType");
                String colorName = resultSet.getString("colorName");
                String gearBox = resultSet.getString("gearBox");
                int yearOfManufacture = resultSet.getInt("yearOfManufacture");
                int numberOfSeating = resultSet.getInt("numberOfSeat");
                int discountPercent = resultSet.getInt("discountPercent");
                
                return new ProductDetailDTO(productID, brandName, styleName, fuelType, colorName, gearBox, yearOfManufacture, numberOfSeating, discountPercent);
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
    
    public static void update(ProductDetailDTO pdDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "UPDATE productdetail SET brandName = ?, styleName = ?, fuelType = ?, colorName = ?, gearBox = ?, yearOfManufacture = ?, numberOfSeat = ?, discountPercent = ? WHERE productID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, pdDTO.getBrandName());
            pstmt.setString(2, pdDTO.getStyleName());
            pstmt.setString(3, pdDTO.getFuelType());
            pstmt.setString(4, pdDTO.getColorName());
            pstmt.setString(5, pdDTO.getGearBox());
            pstmt.setInt(6, pdDTO.getYearOfManufacture());
            pstmt.setInt(7, pdDTO.getNumberOfSeating());
            pstmt.setInt(8, pdDTO.getDiscountPercent());
            pstmt.setString(9, pdDTO.getProductID());

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
    
    public static void insert(ProductDetailDTO pdDTO) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "INSERT INTO productdetail (brandName, styleName, fuelType, colorName, gearBox, yearOfManufacture, numberOfSeating, discountPercent, productID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, pdDTO.getBrandName());
            pstmt.setString(2, pdDTO.getStyleName());
            pstmt.setString(3, pdDTO.getFuelType());
            pstmt.setString(4, pdDTO.getColorName());
            pstmt.setString(5, pdDTO.getGearBox());
            pstmt.setInt(6, pdDTO.getYearOfManufacture());
            pstmt.setInt(7, pdDTO.getNumberOfSeating());
            pstmt.setInt(8, pdDTO.getDiscountPercent());
            pstmt.setString(9, pdDTO.getProductID());

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
