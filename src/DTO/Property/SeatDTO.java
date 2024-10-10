/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class SeatDTO {
    private String seatID;
    private int numberOfSeat;

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public String getSeatID() {
        return seatID;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public SeatDTO(String seatID, int numberOfSeat) {
        this.seatID = seatID;
        this.numberOfSeat = numberOfSeat;
    }
}
