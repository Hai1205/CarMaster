/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Property;

import DAO.Database;
import DTO.Property.BrandDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class BrandDAO {
    public static void insert(BrandDTO pmsDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO brand (brandID, brandName) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pmsDTO.getBrandID());
            pstmt.setString(2, pmsDTO.getBrandName());
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

    public static void update(BrandDTO bDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE brand SET brandName = ? WHERE brandID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bDTO.getBrandName());
            pstmt.setString(2, bDTO.getBrandID());
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

    public static ArrayList<BrandDTO> getList() {
        ArrayList<BrandDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM brand ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String permissionID = resultSet.getString("brandID");
                String brandName = resultSet.getString("brandName");
                list.add(new BrandDTO(permissionID, brandName));
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

    public static String[] getListBrandName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT brandName FROM brand ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
    
            while (resultSet.next()) {
                list.add(resultSet.getString("brandName"));
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

    public static String getIDByName(String brandName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String brandID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT brandID FROM brand WHERE brandName = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, brandName);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                brandID = resultSet.getString("brandID");
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
        return brandID;
    }
    
    public static String getNameByID(String brandID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String brandName = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT brandName FROM brand WHERE brandID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, brandID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                brandName = resultSet.getString("brandName");
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
        return brandName;
    }
}
