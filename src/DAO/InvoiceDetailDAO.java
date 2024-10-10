package DAO;

import DTO.InvoiceDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Tran Nhat Sinh
 */
public class InvoiceDetailDAO {

    public static void insert(ArrayList<InvoiceDetailDTO> list) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            for (InvoiceDetailDTO ivDTO : list) {
                connection = Database.getConnection();
                String sql = "INSERT INTO invoicedetail(invoiceID, productID, quantity, price, cost) VALUES (?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, ivDTO.getInvoiceID());
                pstmt.setString(2, ivDTO.getProductID());
                pstmt.setInt(3, ivDTO.getQuantity());
                pstmt.setLong(4, ivDTO.getPrice());
                pstmt.setLong(5, ivDTO.getCost());
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

    public static ArrayList<InvoiceDetailDTO> getListByID(String invoiceID) {
        ArrayList<InvoiceDetailDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM invoicedetail WHERE invoiceID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, invoiceID);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                long price = resultSet.getLong("price");
                int quantity = resultSet.getInt("quantity");
                Long cost = resultSet.getLong("cost");

                list.add(new InvoiceDetailDTO(invoiceID, productID, price, quantity, cost));
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
