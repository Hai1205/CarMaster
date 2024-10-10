package BUS.Property;

import DAO.Property.DiscountDAO;
import DTO.Property.DiscountDTO;

import java.util.ArrayList;

public class DiscountBUS {
    public DiscountBUS() {
    }

    public ArrayList<DiscountDTO> getList() {
        return DiscountDAO.getList();
    }

    public void add(DiscountDTO dcDTO) {
        DiscountDAO.insert(dcDTO);
    }

    public void update(DiscountDTO dcDTO) {
        DiscountDAO.update(dcDTO);
    }

    public String checkNum(int discountPercent){
        return DiscountDAO.getIDByNum(discountPercent);
    }
    
    public int getNumByID(String discountID) {
        return DiscountDAO.getNumByID(discountID);
    }

    public String[] getListDiscountPercent(){
        return DiscountDAO.getListDiscountPercent();
    }
}
