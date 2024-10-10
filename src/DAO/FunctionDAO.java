/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.FunctionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class FunctionDAO {

    public static ArrayList<FunctionDTO> getList() {
        ArrayList<FunctionDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = Database.getConnection();
            String sql = "SELECT * FROM functional";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            
            while (rs.next()) {
                String functionID = rs.getString("functionID");
                String functionName = rs.getString("functionName");
                FunctionDTO ftDTO = new FunctionDTO(functionID, functionName);
                result.add(ftDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
