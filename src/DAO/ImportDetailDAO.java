package DAO;

import DTO.ImportDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 */
public class ImportDetailDAO {

    public static void insert(ArrayList<ImportDetailDTO> list) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            for (ImportDetailDTO ipdDTO : list) {
                connection = Database.getConnection();
                String sql = "INSERT INTO importdetail (importID, productID, quantity, price, cost) VALUES (?,?,?,?,?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, ipdDTO.getImportID());
                pstmt.setString(2, ipdDTO.getProductID());
                pstmt.setInt(3, ipdDTO.getQuantity());
                pstmt.setLong(4, ipdDTO.getPrice());
                pstmt.setLong(5, ipdDTO.getCost());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public static ArrayList<ImportDetailDTO> getListByID(String importID) {
        ArrayList<ImportDetailDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM importdetail  WHERE importID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, importID);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                Long price = resultSet.getLong("price");
                int quantity = resultSet.getInt("quantity");
                long cost = resultSet.getLong("cost");

                list.add(new ImportDetailDTO(importID, productID, price, quantity, cost));
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

}
