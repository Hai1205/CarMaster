package BUS;

import DTO.SupplierDTO;
import helper.Tool;

import java.util.ArrayList;

import DAO.SupplierDAO;

public class SupplierBUS {

    public SupplierBUS() {
    }

    public ArrayList<SupplierDTO> getList() {
        return SupplierDAO.getList();
    }

    public String createID() {
        String ID;
        do {
            ID = "SP" + Tool.randomID();
        } while (getSupplierByID(ID) != null);
        return ID;
    }

    public void add(SupplierDTO spDTO) {
        SupplierDAO.insert(spDTO);
    }

    public void update(SupplierDTO spDTO) {
        SupplierDAO.update(spDTO);
    }

    public ArrayList<SupplierDTO> search(String text) {
        return SupplierDAO.search(text);
    }

    public String getNameByID(String supplierID) {
        SupplierDTO spDTO = SupplierDAO.getSupplierByID(supplierID);
        return spDTO.getSupplierName();
    }

    public String getAddressByID(String supplierID) {
        SupplierDTO spDTO = SupplierDAO.getSupplierByID(supplierID);
        return spDTO.getAddress();
    }

    public String[] getListSupplierName() {
        return SupplierDAO.getListSupplierName();
    }

    public SupplierDTO getSupplierByID(String supplierID) {
        return SupplierDAO.getSupplierByID(supplierID);
    }

    public String getIDByName(String supplierName) {
        return SupplierDAO.getIDByName(supplierName);
    }

    public String[] getListSupplierNameName() {
        return SupplierDAO.getListSupplierName();
    }
}