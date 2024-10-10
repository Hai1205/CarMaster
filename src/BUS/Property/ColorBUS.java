package BUS.Property;

import DAO.Property.ColorDAO;
import DTO.Property.ColorDTO;
import java.util.ArrayList;

public class ColorBUS {
    public ColorBUS() {
    }

    public ArrayList<ColorDTO> getList() {
        return ColorDAO.getList();
    }

    public void add(ColorDTO clDTO) {
        ColorDAO.insert(clDTO);
    }

    public void update(ColorDTO clDTO) {
        ColorDAO.update(clDTO);
    }

    public String checkName(String colorName){
        return ColorDAO.getIDByName(colorName);
    }
    
    public String getNameByID(String colorID) {
        return ColorDAO.getNameByID(colorID);
    }

    public String[] getListColorName(){
        return ColorDAO.getListColorName();
    }
}
