/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS.Property;

import DAO.Property.SeatDAO;
import DTO.Property.SeatDTO;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class SeatBUS {
    public SeatBUS() {
    }

    public ArrayList<SeatDTO> getList() {
        return SeatDAO.getList();
    }

    public void add(SeatDTO sDTO) {
        SeatDAO.insert(sDTO);
    }

    public void update(SeatDTO sDTO) {
        SeatDAO.update(sDTO);
    }

    public String checkNum(int numberOfSeat) {
        return SeatDAO.getIDByNum(numberOfSeat);
    }

    public int getNumByID(String seatID) {
        return SeatDAO.getNumByID(seatID);
    }

    public String[] getListNumberOfSeeting() {
        return SeatDAO.getListNumberOfSeat();
    }
}
