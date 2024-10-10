/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class FuelDTO {
    private String fuelID, fuelType;

    public void setFuelID(String fuelID) {
        this.fuelID = fuelID;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelID() {
        return fuelID;
    }

    public String getFuelType() {
        return fuelType;
    }

    public FuelDTO(String fuelID, String fuelType) {
        this.fuelID = fuelID;
        this.fuelType = fuelType;
    }
}
