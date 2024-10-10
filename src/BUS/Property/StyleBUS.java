package BUS.Property;

import DAO.Property.StyleDAO;
import DTO.Property.StyleDTO;

import java.util.ArrayList;

public class StyleBUS {
    public StyleBUS() {
    }

    public ArrayList<StyleDTO> getList() {
        return StyleDAO.getList();
    }

    public void add(StyleDTO stDTO) {
        StyleDAO.insert(stDTO);
    }

    public void update(StyleDTO stDTO) {
        StyleDAO.update(stDTO);
    }

    public String checkName(String styleName) {
        return StyleDAO.getIDByName(styleName);
    }

    public String getNameByID(String styleID) {
        return StyleDAO.getNameByID(styleID);
    }

    public String[] getListStyleName() {
        return StyleDAO.getListStyleName();
    }
}
