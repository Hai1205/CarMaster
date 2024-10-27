/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PermissionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PermissionDAO {

    public static int insert(PermissionDTO pmsDTO) {
        int result = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO permission (permissionID, permissionName, slot) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pmsDTO.getPermissionID());
            pstmt.setString(2, pmsDTO.getPermissionName());
            pstmt.setInt(3, pmsDTO.getSlot());
            result = pstmt.executeUpdate();
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
        return result;
    }

    public static void update(PermissionDTO pmsDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE permission SET permissionName = ?, slot = ? WHERE permissionID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pmsDTO.getPermissionName());
            pstmt.setInt(2, pmsDTO.getSlot());
            pstmt.setString(3, pmsDTO.getPermissionID());
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

    public static ArrayList<PermissionDTO> search(String info) {
        ArrayList<PermissionDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT * FROM permission WHERE permissionName LIKE ? OR permissionID LIKE ? OR slot LIKE ?";
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
            } else {
                query = "SELECT * FROM permission";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String permissionName = resultSet.getString("permissionName");
                String permissionID = resultSet.getString("permissionID");
                int slot = resultSet.getInt("slot");

                list.add(new PermissionDTO(permissionID, permissionName, slot));
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

    public static ArrayList<PermissionDTO> getList() {
        ArrayList<PermissionDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM permission";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String permissionID = resultSet.getString("permissionID");
                String permissionName = resultSet.getString("permissionName");
                int slot = resultSet.getInt("slot");

                list.add(new PermissionDTO(permissionID, permissionName, slot));
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

    public static PermissionDTO getByID(String permissionID) {
        PermissionDTO pmsDTO = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT * FROM permission WHERE permissionID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, permissionID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                permissionID = resultSet.getString("permissionID");
                String permissionName = resultSet.getString("permissionName");
                int slot = resultSet.getInt("slot");
                pmsDTO = new PermissionDTO(permissionID, permissionName, slot);
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
        return pmsDTO;
    }

    public static String getIDByName(String permissionName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String permissionID = "";
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT permissionID FROM permission WHERE permissionName = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, permissionName);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                permissionID = resultSet.getString("permissionID");
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
        return permissionID;
    }

    public static String getNameByID(String permissionID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String permissionName = "";
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT permissionName FROM permission WHERE permissionID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, permissionID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                permissionName = resultSet.getString("permissionName");
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
        return permissionName;
    }

    public static String[] getListPermissionName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT permission.permissionName\n"
                    + "FROM permission\n"
                    + "LEFT JOIN employee ON permission.permissionID = employee.permissionID\n"
                    + "GROUP BY permission.permissionName, permission.slot\n"
                    + "HAVING COUNT(employee.permissionID) < permission.slot;";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("permissionName"));
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
}
