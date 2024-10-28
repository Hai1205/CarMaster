package DAO.Property;

import DAO.Database;
import DTO.Property.ColorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColorDAO {
    public static void insert(ColorDTO clDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO color (colorID, colorName) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, clDTO.getColorID());
            pstmt.setString(2, clDTO.getColorName());
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

    public static void update(ColorDTO bDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE color SET colorName = ? WHERE colorID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bDTO.getColorName());
            pstmt.setString(2, bDTO.getColorID());
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

    public static ArrayList<ColorDTO> getList() {
        ArrayList<ColorDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM color ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String permissionID = resultSet.getString("colorID");
                String colorName = resultSet.getString("colorName");
                list.add(new ColorDTO(permissionID, colorName));
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
    
    public static String[] getListColorName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT colorName FROM color ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
    
            while (resultSet.next()) {
                list.add(resultSet.getString("colorName"));
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

    public static String getIDByName(String colorName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String colorID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT colorID FROM color WHERE colorName = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, colorName);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                colorID = resultSet.getString("colorID");
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
        return colorID;
    }
    
    public static String getNameByID(String colorID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String colorName = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT colorName FROM color WHERE colorID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, colorID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                colorName = resultSet.getString("colorName");
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
        return colorName;
    }
}
