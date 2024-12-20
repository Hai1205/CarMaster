package BUS;

import DTO.CustomerDTO;
import helper.Tool;

import java.util.ArrayList;
import DAO.CustomerDAO;

public class CustomerBUS {

    public CustomerBUS() {
    }

    public String createID(){
        String ID;
        do{
            ID = "CTM"+Tool.randomID();
        }while(getCustomerByID(ID) != null);
        return ID;
    }

    public ArrayList<CustomerDTO> getList() {
        return CustomerDAO.getList();
    }

    public void add(CustomerDTO ctmDTO) {
        CustomerDAO.insert(ctmDTO);
    }

    public void update(CustomerDTO ctmDTO) {
        CustomerDAO.update(ctmDTO);
    }

    public ArrayList<CustomerDTO> search(String text) {
        return CustomerDAO.search(text);
    }

    public String getAddressByID(String customerID) {
        CustomerDTO ctmDTO = CustomerDAO.getCustomerByID(customerID);
        return ctmDTO.getAddress();
    }

    public String getIDByName(String customerName){
        return CustomerDAO.getIDByName(customerName);
    }

    public String[] getListCustomerName() {
        return CustomerDAO.getListCustomerName();
    }

    public String getNameByID(String customerID) {
        CustomerDTO ctmDTO = CustomerDAO.getCustomerByID(customerID);
        return ctmDTO.getCustomerName();
    }

    public String[] getListCutomerName() {
        return CustomerDAO.getListCustomerName();
    }

    public CustomerDTO getCustomerByID(String customerID) {
        return CustomerDAO.getCustomerByID(customerID);
    }
}
