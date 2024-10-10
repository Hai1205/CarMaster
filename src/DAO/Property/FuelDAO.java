/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Property;

import DAO.Database;
import DTO.Property.FuelDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class FuelDAO {
     public static void insert(FuelDTO fDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO fuel (fuelID, fuelType) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fDTO.getFuelID());
            pstmt.setString(2, fDTO.getFuelType());
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

    public static void update(FuelDTO fDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE fuel SET fuelType = ? WHERE fuelID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fDTO.getFuelType());
            pstmt.setString(2, fDTO.getFuelID());
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

    public static ArrayList<FuelDTO> getList() {
        ArrayList<FuelDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM fuel";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String fuelID = resultSet.getString("fuelID");
                String fuelType = resultSet.getString("fuelType");
                list.add(new FuelDTO(fuelID, fuelType));
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
    
    public static String[] getListFuelType() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT fuelType FROM fuel";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
    
            while (resultSet.next()) {
                list.add(resultSet.getString("fuelType"));
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
    

    public static String getIDByType(String fuelType) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String fuelID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT fuelID FROM fuel WHERE fuelType = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuelType);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                fuelID = resultSet.getString("fuelID");
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
        return fuelID;
    }
    
    public static String getTypeByID(String fuelID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String fuelType = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT fuelType FROM fuel WHERE fuelID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuelID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                fuelType = resultSet.getString("fuelType");
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
        return fuelType;
    }
}
