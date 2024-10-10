/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class DiscountDTO {
    private String discountID;
    private int discountPercent;

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountID() {
        return discountID;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public DiscountDTO(String discountID, int discountPercent) {
        this.discountID = discountID;
        this.discountPercent = discountPercent;
    }
}
