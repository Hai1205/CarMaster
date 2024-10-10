/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class BrandDTO {
    private String brandID, brandName;

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandID() {
        return brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public BrandDTO(String brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }
    
}
