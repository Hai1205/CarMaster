package BUS;

import java.util.ArrayList;
import DAO.ImportDAO;
import DAO.ImportDetailDAO;
import DTO.ImportDTO;
import DTO.ImportDetailDTO;

/**
 *
 */
public class ImportBUS {

    public ImportBUS() {
    }

    public ArrayList<ImportDTO> getList() {
        return ImportDAO.getList();
    }

    public ArrayList<ImportDTO> search(String info){
        return ImportDAO.search(info);
    }

    public ArrayList<ImportDetailDTO> getListDetailByID(String importID) {
        return ImportDetailDAO.getListByID(importID);
    }

    public void add(ImportDTO ipDTO, ArrayList<ImportDetailDTO> ipdList) {
        ImportDAO.insert(ipDTO);

        ImportDetailDAO.insert(ipdList);
    }

    public long getTotalCost(ArrayList<ImportDetailDTO> temp) {
        long result = 0;
        for (ImportDetailDTO ipdDTO : temp) {
            result += ipdDTO.getCost();
        }
        return result;
    }

    public int findIndexByProductID(ArrayList<ImportDetailDTO> ipdList, String productID) {
        for(int i=0; i<ipdList.size(); i++) {
            if(ipdList.get(i).getProductID().equals(productID)){
                return i;
            }
        }
        return 0;
    }

    public String getSupplierNameByID(String supplierID){
        SupplierBUS spBUS = new SupplierBUS();
        return spBUS.getNameByID(supplierID);
    }

    public String getSupplierAddressByID(String supplierID){
        SupplierBUS spBUS = new SupplierBUS();
        return spBUS.getAddressByID(supplierID);
    }

    public String getEmployeeNameByID(String employeeID){
        EmployeeBUS epBUS = new EmployeeBUS();
        return epBUS.getNameByID(employeeID);
    }

    public ImportDTO getImportByID(String importID){
        return ImportDAO.getImportByID(importID);
    }
}
