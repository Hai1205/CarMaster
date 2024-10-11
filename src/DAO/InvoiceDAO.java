package DAO;

import DTO.InvoiceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 */
public class InvoiceDAO {
    
    public static void insert(InvoiceDTO ivDTO) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        try {
            connection = Database.getConnection();
            String sql = "INSERT INTO invoice(creationDate, customerID, totalCost, employeeID, invoiceID) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setTimestamp(1, ivDTO.getCreationDate());
            pstmt.setString(2, ivDTO.getCustomerID());
            pstmt.setLong(3, ivDTO.getTotalCost());
            pstmt.setString(4, ivDTO.getEmployeeID());
            pstmt.setString(5, ivDTO.getInvoiceID());
            pstmt.executeUpdate();
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

    public static void update(InvoiceDTO ivDTO) {
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = Database.getConnection();
            String sql = "UPDATE invoice SET creationDate = ?, customerID = ?, totalCost = ?, employeeID = ? WHERE invoiceID=?";
            pstmt = connection.prepareStatement(sql);

            pstmt.setTimestamp(1, ivDTO.getCreationDate());
            pstmt.setString(2, ivDTO.getCustomerID());
            pstmt.setLong(3, ivDTO.getTotalCost());
            pstmt.setString(4, ivDTO.getEmployeeID());
            pstmt.setString(5, ivDTO.getInvoiceID());
            pstmt.executeUpdate();
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

    public static ArrayList<InvoiceDTO> getList() {
        ArrayList<InvoiceDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM invoice ORDER BY creationDate DESC";
            pstmt = connection.prepareStatement(sql);
            resultSet =  pstmt.executeQuery();

            while(resultSet.next()){
                String invoiceID = resultSet.getString("invoiceID");
                String customerID = resultSet.getString("customerID");
                String employeeID = resultSet.getString("employeeID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                long totalCost = resultSet.getLong("totalCost");

                list.add(new InvoiceDTO(invoiceID, customerID, employeeID, creationDate, totalCost));
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

    public static InvoiceDTO getInvoiceByID(String invoiceID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM invoice WHERE invoiceID=?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, invoiceID);
            resultSet = pstmt.executeQuery();

            while(resultSet.next()){
                String customerID = resultSet.getString("customerID");
                String employeeID = resultSet.getString("employeeID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                long totalCost = resultSet.getLong("totalCost");

                return new InvoiceDTO(invoiceID, customerID, employeeID, creationDate, totalCost);
            }
        } catch (Exception e) {
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
        return null;
    }
    
    public static ArrayList<InvoiceDTO> getListByCustomerID(String customerID) {
        ArrayList<InvoiceDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM invoice WHERE customerID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, customerID);
            resultSet = pstmt.executeQuery();

            while(resultSet.next()){
                String invoiceID = resultSet.getString("invoiceID");
                String employeeID = resultSet.getString("employeeID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                long totalCost = resultSet.getLong("totalCost");

                list.add(new InvoiceDTO(invoiceID, customerID, employeeID, creationDate, totalCost));
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

    public static ArrayList<InvoiceDTO> search(String info) {
        ArrayList<InvoiceDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query;

            if (info != null && !info.isEmpty()) {
                query = "SELECT invocie.invoiceID, customer.customerID, employee.employeeID, invocie.creationDate, invocie.totalCost " +
               "FROM invocie " +
               "JOIN customer ON invocie.customerID = customer.customerID " +
               "JOIN employee ON employee.employeeID = invocie.employeeID " +
               "WHERE invocie.invoiceID LIKE ? " +
               "OR customer.customerName LIKE ? " +
               "OR employee.employeeName LIKE ? " +
               "OR invocie.creationDate LIKE ? " +
               "OR invocie.totalCost LIKE ?";
               
                pstmt = connection.prepareStatement(query);
                String searchValue = "%" + info + "%";
                pstmt.setString(1, searchValue);
                pstmt.setString(2, searchValue);
                pstmt.setString(3, searchValue);
                pstmt.setString(4, searchValue);
                pstmt.setString(5, searchValue);
            } else {
                query = "SELECT * FROM invoice";
                pstmt = connection.prepareStatement(query);
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String invoiceID = resultSet.getString("invoiceID");
                String customerID = resultSet.getString("customerID");
                String employeeID = resultSet.getString("employeeID");
                Timestamp creationDate = resultSet.getTimestamp("creationDate");
                Long totalCost = resultSet.getLong("totalCost");

                list.add(new InvoiceDTO(invoiceID, customerID, employeeID, creationDate, totalCost));
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
