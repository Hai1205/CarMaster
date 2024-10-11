package BUS;

import java.util.ArrayList;
import DAO.InvoiceDAO;
import DAO.InvoiceDetailDAO;
import DTO.InvoiceDTO;
import DTO.InvoiceDetailDTO;

/**
 *
 */
public class InvoiceBUS {

    public InvoiceBUS() {
    }

    public ArrayList<InvoiceDTO> getList() {
        return InvoiceDAO.getList();
    }

    public ArrayList<InvoiceDTO> search(String info){
        return InvoiceDAO.search(info);
    }

    public ArrayList<InvoiceDetailDTO> getListDetailByID(String invoiceID) {
        return InvoiceDetailDAO.getListByID(invoiceID);
    }

    public void add(InvoiceDTO ivDTO, ArrayList<InvoiceDetailDTO> ivdList) {
        InvoiceDAO.insert(ivDTO);

        InvoiceDetailDAO.insert(ivdList);
    }

    public long getTotalCost(ArrayList<InvoiceDetailDTO> temp) {
        long result = 0;
        for (InvoiceDetailDTO ipdDTO : temp) {
            result += ipdDTO.getCost();
        }
        return result;
    }

    public int findIndexByProductID(ArrayList<InvoiceDetailDTO> ivdList, String productID) {
        for(int i=0; i<ivdList.size(); i++) {
            if(ivdList.get(i).getProductID().equals(productID)){
                return i;
            }
        }
        return 0;
    }

    public String getCustomerNameByID(String customerID){
        CustomerBUS ctmBUS = new CustomerBUS();
        return ctmBUS.getNameByID(customerID);
    }

    public String getSupplierAddressByID(String customerID){
        CustomerBUS ctmBUS = new CustomerBUS();
        return ctmBUS.getAddressByID(customerID);
    }

    public String getEmployeeNameByID(String employeeID){
        EmployeeBUS epBUS = new EmployeeBUS();
        return epBUS.getNameByID(employeeID);
    }

    public InvoiceDTO getInvoiceByID(String invoiceID){
        return InvoiceDAO.getInvoiceByID(invoiceID);
    }

    public String getAddressByID(String customerID) {
        CustomerBUS spBUS = new CustomerBUS();
        return spBUS.getAddressByID(customerID);
    }
}
