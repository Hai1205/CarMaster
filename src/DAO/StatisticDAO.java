/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Statistic.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

/**
 *
 */
public class StatisticDAO {

    public static HashMap<String, ArrayList<ByStockDTO>> getStock(String text, Date beginDate, Date endDate) {
        HashMap<String, ArrayList<ByStockDTO>> list = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginStr = dateFormat.format(beginDate);
        String endStr = dateFormat.format(endDate);

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = """
                            WITH tempImport AS (
                        SELECT importdetail.productID, SUM(importdetail.quantity) AS numberOfImport
                        FROM importdetail
                        JOIN import ON import.importID = importdetail.importID
                        WHERE DATE(import.creationDate) BETWEEN ? AND ?
                        GROUP BY importdetail.productID
                    ),
                    tempInvoice AS (
                        SELECT invoiceDetail.productID, SUM(invoiceDetail.quantity) AS numberOfInvoice
                        FROM invoiceDetail
                        JOIN invoice ON invoice.invoiceID = invoiceDetail.invoiceID
                        WHERE DATE(invoice.creationDate) BETWEEN ? AND ?
                        GROUP BY invoiceDetail.productID
                    ),
                    BeginImport AS (
                        SELECT importdetail.productID, SUM(importdetail.quantity) AS numberOfBeginImport
                        FROM importdetail
                        JOIN import ON import.importID = importdetail.importID
                        WHERE DATE(import.creationDate) < ?
                        GROUP BY importdetail.productID
                    ),
                    BeginInvoice AS (
                        SELECT invoiceDetail.productID, SUM(invoiceDetail.quantity) AS numberOfBeginInvoice
                        FROM invoiceDetail
                        JOIN invoice ON invoice.invoiceID = invoiceDetail.invoiceID
                        WHERE DATE(invoice.creationDate) < ?
                        GROUP BY invoiceDetail.productID
                    ),
                    Begin AS (
                        SELECT
                    		productDetail.productID,
                            COALESCE(BeginImport.numberOfBeginImport, 0) - COALESCE(BeginInvoice.numberOfBeginInvoice, 0) AS numberOfBegin
                        FROM productDetail
                        LEFT JOIN BeginImport ON productDetail.productID = BeginImport.productID
                        LEFT JOIN BeginInvoice ON productDetail.productID = BeginInvoice.productID
                    ),
                    temp_table AS (
                        SELECT product.productID, product.productName, Begin.numberOfBegin,
                               COALESCE(tempImport.numberOfImport, 0) AS importQuantity,
                               COALESCE(tempInvoice.numberOfInvoice, 0) AS invoiceQuantity,
                               (Begin.numberOfBegin + COALESCE(tempImport.numberOfImport, 0) - COALESCE(tempInvoice.numberOfInvoice, 0)) AS NumberOfEnd,
                               discount.discountPercent, seat.numberOfSeat, color.colorName
                        FROM Begin
                        JOIN tempImport ON tempImport.productID = Begin.productID
                        JOIN tempInvoice ON tempInvoice.productID = Begin.productID
                        JOIN productDetail ON productDetail.productID = Begin.productID
                        JOIN product ON productDetail.productID = product.productID
                        JOIN discount ON productDetail.discountPercent = discount.discountPercent
                        JOIN seat ON productDetail.numberOfSeat = seat.numberOfSeat
                        JOIN color ON productDetail.colorName = color.colorName
                    )
                    SELECT * FROM temp_table
                    WHERE productName LIKE ? OR productID LIKE ? OR numberOfBegin LIKE ? OR importQuantity LIKE ? OR invoiceQuantity LIKE ? OR productID LIKE ? OR NumberOfEnd LIKE ?
                    ORDER BY productID;
                            """;

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, beginStr);
            pstmt.setString(2, endStr);
            pstmt.setString(3, beginStr);
            pstmt.setString(4, endStr);
            pstmt.setString(5, endStr);
            pstmt.setString(6, endStr);
            pstmt.setString(7, "%" + text + "%");
            pstmt.setString(8, "%" + text + "%");
            pstmt.setString(9, "%" + text + "%");
            pstmt.setString(10, "%" + text + "%");
            pstmt.setString(11, "%" + text + "%");
            pstmt.setString(12, "%" + text + "%");
            pstmt.setString(13, "%" + text + "%");

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                String productName = resultSet.getString("productName");
                int numberOfBegin = resultSet.getInt("numberOfBegin");
                int importQuantity = resultSet.getInt("importQuantity");
                int invoiceQuantity = resultSet.getInt("invoiceQuantity");
                int NumberOfEnd = resultSet.getInt("NumberOfEnd");
                int DiscounPercent = resultSet.getInt("discountPercent");
                int numberOfSeat = resultSet.getInt("numberOfSeat");
                String colorName = resultSet.getString("colorName");
                ByStockDTO p = new ByStockDTO(productID, productName, DiscounPercent,
                        numberOfSeat, colorName,
                        numberOfBegin, importQuantity, invoiceQuantity, NumberOfEnd);
                list.computeIfAbsent(productID, k -> new ArrayList<>()).add(p);
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

    public static ArrayList<ByIncomeAndExpenseDTO> getIncomeAndExpensePerYear(int beginYear, int endYear) {
        ArrayList<ByIncomeAndExpenseDTO> list = new ArrayList<>();
    
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
    
        try {
            connection = Database.getConnection();
            
            String setBeginYearSql = "SET @beginYear = ?;";
            pstmt = connection.prepareStatement(setBeginYearSql);
            pstmt.setInt(1, beginYear);
            pstmt.execute();
            String setEndYearSql = "SET @endYear = ?;";
            pstmt = connection.prepareStatement(setEndYearSql);
            pstmt.setInt(1, endYear);
            pstmt.execute();
    
            String sql = """
                    WITH RECURSIVE years(year) AS (
                        SELECT @beginYear
                        UNION ALL
                        SELECT year + 1
                        FROM years
                        WHERE year < @endYear
                    )
                    SELECT
                        years.year AS Year,
                        COALESCE(SUM(importdetail.cost), 0) AS Expense,
                        COALESCE(SUM(invoicedetail.cost), 0) AS Income
                    FROM years
                    LEFT JOIN invoice ON YEAR(invoice.creationDate) = years.year
                    LEFT JOIN invoicedetail ON invoice.invoiceID = invoicedetail.invoiceID
                    LEFT JOIN import ON YEAR(import.creationDate) = years.year
                    LEFT JOIN importdetail ON import.importID = importdetail.importID
                    GROUP BY years.year
                    ORDER BY years.year;
                    """;
    
            pstmt = connection.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int year = resultSet.getInt("Year");
                long expense = resultSet.getLong("Expense");
                long income = resultSet.getLong("Income");
                ByIncomeAndExpenseDTO dto = new ByIncomeAndExpenseDTO(year, expense, income, income - expense);
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    

    public static ArrayList<ByCustomerDTO> getByCustomer(String text, Date beginDate, Date endDate) {
        ArrayList<ByCustomerDTO> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginStr = dateFormat.format(beginDate);
        String endStr = dateFormat.format(endDate);

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = """
                                         WITH CTM AS (
                        SELECT customer.customerID, customer.customerName, COUNT(invoice.invoiceID) AS invoiceTotal,
                               SUM(invoice.totalCost) AS TotalCost
                        FROM customer
                        JOIN invoice ON customer.customerID = invoice.customerID
                       WHERE invoice.creationDate BETWEEN ? AND ?
                        GROUP BY customer.customerID, customer.customerName
                    )
                    SELECT customerID, customerName, COALESCE(invoiceTotal, 0) AS quantity,
                           COALESCE(TotalCost, 0) AS total
                    FROM CTM
                    WHERE customerName LIKE ? OR customerID LIKE ? OR COALESCE(invoiceTotal, 0) LIKE ? OR COALESCE(TotalCost, 0) LIKE ?;""";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, beginStr);
            pstmt.setString(2, endStr);
            pstmt.setString(3, "%" + text + "%");
            pstmt.setString(4, "%" + text + "%");
            pstmt.setString(5, "%" + text + "%");
            pstmt.setString(6, "%" + text + "%");

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String customerID = resultSet.getString("customerID");
                String customerName = resultSet.getString("customerName");
                int quantity = resultSet.getInt("quantity");
                long totalCost = resultSet.getLong("total");
                ByCustomerDTO x = new ByCustomerDTO(customerID, customerName, quantity, totalCost);
                list.add(x);
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

    public static ArrayList<BySupplierDTO> getBySupplier(String text, Date beginDate, Date endDate) {
        ArrayList<BySupplierDTO> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginStr = dateFormat.format(beginDate);
        String endStr = dateFormat.format(endDate);

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = """
                                         WITH SP AS (
                        SELECT supplier.supplierID, supplier.supplierName
                         , COUNT(import.importID) AS importTotal,
                              SUM(import.totalCost) AS TotalCost
                        FROM supplier
                        JOIN import ON supplier.supplierID = import.supplierID
                        WHERE import.creationDate BETWEEN ? AND ?
                        GROUP BY supplier.supplierID, supplier.supplierName
                    )
                    SELECT
                    supplierID,
                    supplierName
                    , COALESCE(importTotal, 0) AS quantity,
                           COALESCE(TotalCost, 0) AS total
                    FROM SP
                    WHERE supplierName LIKE ? OR supplierID LIKE ? OR COALESCE(importTotal, 0) LIKE ? OR COALESCE(TotalCost, 0) LIKE ?;
                    """;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, beginStr);
            pstmt.setString(2, endStr);
            pstmt.setString(3, "%" + text + "%");
            pstmt.setString(4, "%" + text + "%");
            pstmt.setString(5, "%" + text + "%");
            pstmt.setString(6, "%" + text + "%");

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String supplierID = resultSet.getString("supplierID");
                String supplierName = resultSet.getString("supplierName");
                int quantity = resultSet.getInt("quantity");
                long totalCost = resultSet.getLong("total");
                BySupplierDTO x = new BySupplierDTO(supplierID, supplierName, quantity, totalCost);
                list.add(x);
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

    public static ArrayList<ByMonthOfYearDTO> getByMonthOfYear(int Year) {
        ArrayList<ByMonthOfYearDTO> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT months.month AS Month, \n"
                    + "       COALESCE(SUM(importdetail.price), 0) AS Expense,\n"
                    + "       COALESCE(SUM(invoicedetail.price), 0) AS Income\n"
                    + "FROM (\n"
                    + "       SELECT 1 AS month\n"
                    + "       UNION ALL SELECT 2\n"
                    + "       UNION ALL SELECT 3\n"
                    + "       UNION ALL SELECT 4\n"
                    + "       UNION ALL SELECT 5\n"
                    + "       UNION ALL SELECT 6\n"
                    + "       UNION ALL SELECT 7\n"
                    + "       UNION ALL SELECT 8\n"
                    + "       UNION ALL SELECT 9\n"
                    + "       UNION ALL SELECT 10\n"
                    + "       UNION ALL SELECT 11\n"
                    + "       UNION ALL SELECT 12\n"
                    + "     ) AS months\n"
                    + "LEFT JOIN invoice ON MONTH(invoice.creationDate) = months.month AND YEAR(invoice.creationDate) = ?\n"
                    + "LEFT JOIN invoicedetail ON invoice.invoiceID = invoicedetail.invoiceID\n"
                    + "LEFT JOIN import ON MONTH(import.creationDate) = months.month AND YEAR(import.creationDate) = ?\n"
                    + "LEFT JOIN importdetail ON import.importID = importdetail.importID\n"
                    + "WHERE months.month <= MONTH(CURDATE())\n"
                    + "GROUP BY months.month\n"
                    + "ORDER BY months.month;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Year);
            pstmt.setInt(2, Year);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int Month = resultSet.getInt("Month");
                long Expense = resultSet.getLong("Expense");
                long Income = resultSet.getLong("Income");
                ByMonthOfYearDTO thongke = new ByMonthOfYearDTO(Month, Expense, Income, Income - Expense);
                list.add(thongke);
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

    public static ArrayList<ByPerDateInMonthDTO> getByPerDateInMonth(int month, int year) {
    ArrayList<ByPerDateInMonthDTO> list = new ArrayList<>();
    String beginDateStr = year + "-" + month + "-01";
    String endDateStr;
    LocalDate currentDate = LocalDate.now();
    LocalDate endDate;
    if (year == currentDate.getYear() && month == currentDate.getMonthValue()) {
        endDate = currentDate;
    } else {
        endDate = YearMonth.of(year, month).atEndOfMonth();
    }
    endDateStr = endDate.toString();

    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet resultSet = null;

    try {
        connection = Database.getConnection();
        String sql = "SELECT \n"
                   + "  dates.date AS date, \n"
                   + "  COALESCE(SUM(importdetail.price), 0) AS Expense, \n"
                   + "  COALESCE(SUM(invoicedetail.price), 0) AS Income\n"
                   + "FROM (\n"
                   + "  SELECT DATE_ADD(?, INTERVAL c.number DAY) AS date\n"
                   + "  FROM (\n"
                   + "    SELECT a.number + b.number * 31 AS number\n"
                   + "    FROM (\n"
                   + "      SELECT 0 AS number\n"
                   + "      UNION ALL SELECT 1\n"
                   + "      UNION ALL SELECT 2\n"
                   + "      UNION ALL SELECT 3\n"
                   + "      UNION ALL SELECT 4\n"
                   + "      UNION ALL SELECT 5\n"
                   + "      UNION ALL SELECT 6\n"
                   + "      UNION ALL SELECT 7\n"
                   + "      UNION ALL SELECT 8\n"
                   + "      UNION ALL SELECT 9\n"
                   + "      UNION ALL SELECT 10\n"
                   + "      UNION ALL SELECT 11\n"
                   + "      UNION ALL SELECT 12\n"
                   + "      UNION ALL SELECT 13\n"
                   + "      UNION ALL SELECT 14\n"
                   + "      UNION ALL SELECT 15\n"
                   + "      UNION ALL SELECT 16\n"
                   + "      UNION ALL SELECT 17\n"
                   + "      UNION ALL SELECT 18\n"
                   + "      UNION ALL SELECT 19\n"
                   + "      UNION ALL SELECT 20\n"
                   + "      UNION ALL SELECT 21\n"
                   + "      UNION ALL SELECT 22\n"
                   + "      UNION ALL SELECT 23\n"
                   + "      UNION ALL SELECT 24\n"
                   + "      UNION ALL SELECT 25\n"
                   + "      UNION ALL SELECT 26\n"
                   + "      UNION ALL SELECT 27\n"
                   + "      UNION ALL SELECT 28\n"
                   + "      UNION ALL SELECT 29\n"
                   + "      UNION ALL SELECT 30\n"
                   + "  ) AS a\n"
                   + "  CROSS JOIN (\n"
                   + "    SELECT 0 AS number\n"
                   + "    UNION ALL SELECT 1\n"
                   + "    UNION ALL SELECT 2\n"
                   + "    UNION ALL SELECT 3\n"
                   + "    UNION ALL SELECT 4\n"
                   + "    UNION ALL SELECT 5\n"
                   + "    UNION ALL SELECT 6\n"
                   + "    UNION ALL SELECT 7\n"
                   + "    UNION ALL SELECT 8\n"
                   + "    UNION ALL SELECT 9\n"
                   + "    UNION ALL SELECT 10\n"
                   + "  ) AS b\n"
                   + ") AS c\n"
                   + "WHERE DATE_ADD(?, INTERVAL c.number DAY) <= ?\n"
                   + ") AS dates\n"
                   + "LEFT JOIN invoice ON DATE(invoice.creationDate) = dates.date\n"
                   + "LEFT JOIN invoicedetail ON invoice.invoiceID = invoicedetail.invoiceID\n"
                   + "LEFT JOIN import ON DATE(import.creationDate) = dates.date\n"
                   + "LEFT JOIN importdetail ON import.importID = importdetail.importID\n"
                   + "GROUP BY dates.date\n"
                   + "ORDER BY dates.date;";

        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, beginDateStr);
        pstmt.setString(2, beginDateStr);
        pstmt.setString(3, endDateStr);

        resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            Date date = resultSet.getDate("date");
            long expense = resultSet.getLong("Expense");
            long income = resultSet.getLong("Income");
            ByPerDateInMonthDTO dto = new ByPerDateInMonthDTO(date, expense, income, income - expense);
            list.add(dto);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null)
                resultSet.close();
            if (pstmt != null)
                pstmt.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}


    public static ArrayList<ByPerDateInMonthDTO> getLast7Days() {
        ArrayList<ByPerDateInMonthDTO> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String sql = """
                    WITH RECURSIVE dates(date) AS (
                      SELECT DATE_SUB(CURDATE(), INTERVAL 6 DAY)
                      UNION ALL
                      SELECT DATE_ADD(date, INTERVAL 1 DAY)
                      FROM dates
                      WHERE date < CURDATE()
                    )
                    SELECT
                      dates.date AS date,
                      COALESCE(SUM(invoicedetail.cost), 0) AS Income,
                      COALESCE(SUM(importdetail.cost), 0) AS Expense
                    FROM dates
                    LEFT JOIN invoice ON DATE(invoice.creationDate) = dates.date
                    LEFT JOIN import ON DATE(import.creationDate) = dates.date
                    LEFT JOIN invoicedetail ON invoice.invoiceID = invoicedetail.invoiceID
                    LEFT JOIN importdetail ON import.importID = importdetail.importID
                    GROUP BY dates.date
                    ORDER BY dates.date;""";

            pstmt = connection.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                long Expense = resultSet.getLong("Expense");
                long Income = resultSet.getLong("Income");
                ByPerDateInMonthDTO tn = new ByPerDateInMonthDTO(date, Expense, Income, Income - Expense);
                list.add(tn);
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

    public static ArrayList<ByPerDateInMonthDTO> getDateToDate(String beginDate, String endDate) {
        ArrayList<ByPerDateInMonthDTO> list = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            
            String sql = "SELECT dates.date AS date,\n"
                       + "COALESCE(SUM(importdetail.price), 0) AS Expense,\n"
                       + "COALESCE(SUM(invoicedetail.price), 0) AS Income\n"
                       + "FROM (\n"
                       + "  SELECT DATE_ADD(?, INTERVAL c.number DAY) AS date\n"
                       + "  FROM (\n"
                       + "    SELECT a.number + b.number * 31 AS number\n"
                       + "    FROM (SELECT 0 AS number UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4\n"
                       + "          UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                       + "          UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14\n"
                       + "          UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19\n"
                       + "          UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24\n"
                       + "          UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29\n"
                       + "          UNION ALL SELECT 30) AS a\n"
                       + "    CROSS JOIN (SELECT 0 AS number UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4\n"
                       + "               UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                       + "               UNION ALL SELECT 10) AS b\n"
                       + "  ) AS c\n"
                       + "  WHERE DATE_ADD(?, INTERVAL c.number DAY) <= ?\n"
                       + ") AS dates\n"
                       + "LEFT JOIN invoice ON DATE(invoice.creationDate) = dates.date\n"
                       + "LEFT JOIN invoicedetail ON invoice.invoiceID = invoicedetail.invoiceID\n"
                       + "LEFT JOIN import ON DATE(import.creationDate) = dates.date\n"
                       + "LEFT JOIN importdetail ON import.importID = importdetail.importID\n"
                       + "GROUP BY dates.date\n"
                       + "ORDER BY dates.date;";
            
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, beginDate);
            pstmt.setString(2, beginDate);
            pstmt.setString(3, endDate);
            
            resultSet = pstmt.executeQuery();
            
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                long Expense = resultSet.getLong("Expense");
                long Income = resultSet.getLong("Income");
                ByPerDateInMonthDTO tn = new ByPerDateInMonthDTO(date, Expense, Income, Income - Expense);
                list.add(tn);
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
