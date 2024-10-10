package DAO.Property;

import DAO.Database;
import DTO.Property.DiscountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountDAO {
    public static void insert(DiscountDTO dcDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO discount (discountID, discountPercent) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dcDTO.getDiscountID());
            pstmt.setInt(2, dcDTO.getDiscountPercent());
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

    public static void update(DiscountDTO dcDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE discount SET discountPercent = ? WHERE discountID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dcDTO.getDiscountPercent());
            pstmt.setString(2, dcDTO.getDiscountID());
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

    public static ArrayList<DiscountDTO> getList() {
        ArrayList<DiscountDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM discount";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String discountID = resultSet.getString("discountID");
                int discountPercent = resultSet.getInt("discountPercent");
                list.add(new DiscountDTO(discountID, discountPercent));
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

    public static String[] getListDiscountPercent() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT discountPercent FROM discount";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("discountPercent") + "%");
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

    public static String getIDByNum(int discountPercent) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String discountID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT discountID FROM discount WHERE discountPercent = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, discountPercent);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                discountID = resultSet.getString("discountID");
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
        return discountID;
    }

    public static int getNumByID(String discountID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int discountPercent = 0;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT discountPercent FROM discount WHERE discountID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, discountID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                discountPercent = resultSet.getInt("discountPercent");
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
        return discountPercent;
    }
}
