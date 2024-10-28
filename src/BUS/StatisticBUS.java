package BUS;

import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.ProductDAO;
import DAO.StatisticDAO;
import DAO.SupplierDAO;
import DTO.Statistic.ByCustomerDTO;
import DTO.Statistic.ByIncomeAndExpenseDTO;
import DTO.Statistic.ByMonthOfYearDTO;
import DTO.Statistic.ByPerDateInMonthDTO;
import DTO.Statistic.ByStockDTO;
import DTO.Statistic.BySupplierDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 */
public class StatisticBUS {

    private ArrayList<ByCustomerDTO> ctmList;
    private ArrayList<BySupplierDTO> spList;
    private HashMap<String, ArrayList<ByStockDTO>> stockList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public StatisticBUS() {
        try {
            Date beginDate = sdf.parse("2018-12-16");
            LocalDate localDate = LocalDate.now();
            Date endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            stockList = StatisticDAO.getStock("", beginDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ByCustomerDTO> getAllCustomer() {
        try {
            Date beginDate = sdf.parse("2018-12-16");
            LocalDate localDate = LocalDate.now();
            Date endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            ctmList = StatisticDAO.getByCustomer("", beginDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ctmList;
    }

    public ArrayList<ByCustomerDTO> FilterCustomer(String text, Date beginDate, Date endDate) {
        this.ctmList = StatisticDAO.getByCustomer(text, beginDate, endDate);
        return this.ctmList;
    }

    public ArrayList<BySupplierDTO> getAllSupplier() {
        try {
            Date beginDate = sdf.parse("2018-12-16");
            LocalDate localDate = LocalDate.now();
            Date endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            spList = StatisticDAO.getBySupplier("", beginDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spList;
    }

    public ArrayList<BySupplierDTO> FilterSupplier(String text, Date beginDate, Date endDate) {
        this.spList = StatisticDAO.getBySupplier(text, beginDate, endDate);
        return this.spList;
    }

    public HashMap<String, ArrayList<ByStockDTO>> getStock() {
        return this.stockList;
    }

    public HashMap<String, ArrayList<ByStockDTO>> filterStock(String text, Date beginDate, Date endDate) {
        HashMap<String, ArrayList<ByStockDTO>> result = StatisticDAO.getStock(text, beginDate, endDate);
        return result;
    }

    public int[] getQuantity(ArrayList<ByStockDTO> list) {
        int[] result = { 0, 0, 0, 0 };
        for (int i = 0; i < list.size(); i++) {
            result[0] += list.get(i).getNumberOfBegin();
            result[1] += list.get(i).getNumberOfImport();
            result[2] += list.get(i).getNumberOfInvoice();
            result[3] += list.get(i).getNumberOfEnd();
        }
        return result;
    }

    public ArrayList<ByIncomeAndExpenseDTO> getIncomeAndExpensePerYear(int beginYear, int endYear) {
        return StatisticDAO.getIncomeAndExpensePerYear(beginYear, endYear);
    }

    public ArrayList<ByMonthOfYearDTO> getByMonthOfYear(int year) {
        return StatisticDAO.getByMonthOfYear(year);
    }

    public ArrayList<ByPerDateInMonthDTO> getByPerDateInMonth(int month, int year) {
        return StatisticDAO.getByPerDateInMonth(month, year);
    }

    public ArrayList<ByPerDateInMonthDTO> getDateToDate(String beginDate, String endDate) {
        return StatisticDAO.getDateToDate(beginDate, endDate);
    }

    public ArrayList<ByPerDateInMonthDTO> getLast7Days() {
        return StatisticDAO.getLast7Days();
    }

    public int getProductQuantity() {
        return ProductDAO.getList("").size();
    }

    public int getCustomerQuantity() {
        return CustomerDAO.getList().size();
    }

    public int getSupplierQuantity() {
        return SupplierDAO.getList().size();
    }

    public int getEmployeeQuantity() {
        return EmployeeDAO.getList("Hoạt động").size();
    }
}
