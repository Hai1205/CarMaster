/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Objects;

/**
 *
 */
public class ProductDetailDTO {
    private String productID, brandName, styleName, fuelType, colorName, gearBox;
    private int yearOfManufacture, numberOfSeat, discountPercent;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getProductID() {
        return productID;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getStyleName() {
        return styleName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getColorName() {
        return colorName;
    }

    public String getGearBox() {
        return gearBox;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public ProductDetailDTO(String productID, String brandName, String styleName, String fuelType, String colorName, String gearBox, int yearOfManufacture, int numberOfSeat, int discountPercent) {
        this.productID = productID;
        this.brandName = brandName;
        this.styleName = styleName;
        this.fuelType = fuelType;
        this.colorName = colorName;
        this.gearBox = gearBox;
        this.yearOfManufacture = yearOfManufacture;
        this.numberOfSeat = numberOfSeat;
        this.discountPercent = discountPercent;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductDetailDTO other = (ProductDetailDTO) obj;
        if (!this.productID.equals(other.productID)) {
            return false;
        }
        if (!this.brandName.equals(other.brandName)) {
            return false;
        }
        if (!this.styleName.equals(other.styleName)) {
            return false;
        }
        if (!this.fuelType.equals(other.fuelType)) {
            return false;
        }
        if (!this.colorName.equals(other.colorName)) {
            return false;
        }
        if (!this.gearBox.equals(other.gearBox)) {
            return false;
        }
        if (this.yearOfManufacture != other.yearOfManufacture) {
            return false;
        }
        if (this.numberOfSeat != other.numberOfSeat) {
            return false;
        }
        return Objects.equals(this.discountPercent, other.discountPercent);
    }

    @Override
    public String toString() {
        return "ProductDetailDTO{" + "productID=" + productID + ", brandName=" + brandName + ", styleName=" + styleName + ", fuelType=" + fuelType + ", colorName=" + colorName + ", gearBox=" + gearBox + ", yearOfManufacture=" + yearOfManufacture + ", numberOfSeat=" + numberOfSeat + ", discountPercent=" + discountPercent + '}';
    }
}