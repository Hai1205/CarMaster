/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PermissionDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PermissionDetailDAO {

    public static void insert(ArrayList<PermissionDetailDTO> t) {
        Connection con = null;
        PreparedStatement pstmt = null;
        for (int i = 0; i < t.size(); i++) {
            try {
                con = Database.getConnection();
                String sql = "INSERT INTO permissiondetail (permissionID, functionID, action) VALUES (?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setString(1, t.get(i).getPermissionID());
                pstmt.setString(2, t.get(i).getFunctionID());
                pstmt.setString(3, t.get(i).getAction());
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
    }

    public static void delete(String permissionID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = Database.getConnection();

            String sql = "DELETE FROM permissiondetail WHERE permissionID = ?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, permissionID);

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

    public static ArrayList<PermissionDetailDTO> getList(String t) {
        ArrayList<PermissionDetailDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = Database.getConnection();
            String sql = "SELECT * FROM permissiondetail WHERE permissionID = ?";
            pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setString(1, t);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                String permissionID = rs.getString("permissionID");
                String functionID = rs.getString("functionID");
                String action = rs.getString("action");
                result.add(new PermissionDetailDTO(permissionID, functionID, action));
            }
        } catch (SQLException e) {
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

}
