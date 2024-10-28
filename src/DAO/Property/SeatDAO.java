/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Property;

import DAO.Database;
import DTO.Property.SeatDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class SeatDAO {
    public static void insert(SeatDTO sDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO seat (seatID, numberOfseat) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, sDTO.getSeatID());
            pstmt.setInt(2, sDTO.getNumberOfSeat());
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

    public static void update(SeatDTO sDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE seat SET numberOfseat = ? WHERE seatID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sDTO.getNumberOfSeat());
            pstmt.setString(2, sDTO.getSeatID());
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

    public static ArrayList<SeatDTO> getList() {
        ArrayList<SeatDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM seat ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String seatID = resultSet.getString("seatID");
                int numberOfseat = resultSet.getInt("numberOfseat");
                list.add(new SeatDTO(seatID, numberOfseat));
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
        
    public static String[] getListNumberOfSeat() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT numberOfSeat FROM seat ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
    
            while (resultSet.next()) {
                list.add(resultSet.getString("numberOfSeat"));
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

    public static String getIDByNum(int numberOfseat) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String seatID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT seatID FROM seat WHERE numberOfseat = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, numberOfseat);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                seatID = resultSet.getString("seatID");
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
        return seatID;
    }
    
    public static int getNumByID(String seatID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int numberOfSeat = 0;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT numberOfseat FROM seat WHERE seatID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, seatID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                numberOfSeat = resultSet.getInt("numberOfseat");
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
        return numberOfSeat;
    }
}
