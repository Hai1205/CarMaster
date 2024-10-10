/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class ColorDTO {
    private String colorID, colorName;

    public void setColorID(String colorID) {
        this.colorID = colorID;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorID() {
        return colorID;
    }

    public String getColorName() {
        return colorName;
    }

    public ColorDTO(String colorID, String colorName) {
        this.colorID = colorID;
        this.colorName = colorName;
    }
}
