/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS.Property;

import DAO.Property.FuelDAO;
import DTO.Property.FuelDTO;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class FuelBUS {
    public FuelBUS() {
    }

    public ArrayList<FuelDTO> getList() {
        return FuelDAO.getList();
    }

    public void add(FuelDTO fDTO) {
        FuelDAO.insert(fDTO);
    }

    public void update(FuelDTO fDTO) {
        FuelDAO.update(fDTO);
    }

    public String checkFuleType(String fuelType) {
        return FuelDAO.getIDByType(fuelType);
    }

    public String getTypeByID(String fuelID) {
        return FuelDAO.getTypeByID(fuelID);
    }

    public String[] getListFuelType() {
        return FuelDAO.getListFuelType();
    }
}
