/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS.Property;

import DAO.Property.BrandDAO;
import DTO.Property.BrandDTO;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class BrandBUS {
    public BrandBUS() {
    }

    public ArrayList<BrandDTO> getList() {
        return BrandDAO.getList();
    }

    public void add(BrandDTO bDTO) {
        BrandDAO.insert(bDTO);
    }

    public void update(BrandDTO bDTO) {
        BrandDAO.update(bDTO);
    }

    public String checkBrandName(String brandName){
        return BrandDAO.getIDByName(brandName);
    }
    
    public String getNameByID(String brandID) {
        return BrandDAO.getNameByID(brandID);
    }
    
   public String[] getListBrandName() {
       return BrandDAO.getListBrandName();
   }
}
