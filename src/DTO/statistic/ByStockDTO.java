package DTO.Statistic;

import java.util.Objects;

/**
 *
 */
public class ByStockDTO {
    private String productID, productName, brandName, colorName, fuelType, StyleName;

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getColorName() {
        return colorName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getStyleName() {
        return StyleName;
    }

    public int getNumberOfBegin() {
        return numberOfBegin;
    }

    public int getNumberOfEnd() {
        return numberOfEnd;
    }

    public int getNumberOfImport() {
        return numberOfImport;
    }

    public int getNumberOfInvoice() {
        return numberOfInvoice;
    }

    public int getDiscounPercent() {
        return DiscounPercent;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setStyleName(String StyleName) {
        this.StyleName = StyleName;
    }

    public void setNumberOfBegin(int numberOfBegin) {
        this.numberOfBegin = numberOfBegin;
    }

    public void setNumberOfEnd(int numberOfEnd) {
        this.numberOfEnd = numberOfEnd;
    }

    public void setNumberOfImport(int numberOfImport) {
        this.numberOfImport = numberOfImport;
    }

    public void setNumberOfInvoice(int numberOfInvoice) {
        this.numberOfInvoice = numberOfInvoice;
    }

    public void setDiscounPercent(int DiscounPercent) {
        this.DiscounPercent = DiscounPercent;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }
    private int numberOfBegin, numberOfEnd, numberOfImport, numberOfInvoice, DiscounPercent, numberOfSeat; 

    public ByStockDTO() {
    }

    public ByStockDTO(String productID, String productName, int DiscounPercent, int numberOfSeat, String colorName, int numberOfBegin, int numberOfImport, int numberOfInvoice, int numberOfEnd) {
        this.productID = productID;
        this.productName = productName;
        this.DiscounPercent = DiscounPercent;
        this.numberOfSeat = numberOfSeat;
        this.colorName = colorName;
        this.numberOfBegin = numberOfBegin;
        this.numberOfImport = numberOfImport;
        this.numberOfInvoice = numberOfInvoice;
        this.numberOfEnd = numberOfEnd;
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
        final ByStockDTO other = (ByStockDTO) obj;
        if (this.productID != other.productID) {
            return false;
        }
        if (this.DiscounPercent != other.DiscounPercent) {
            return false;
        }
        if (this.numberOfSeat != other.numberOfSeat) {
            return false;
        }
        if (this.numberOfBegin != other.numberOfBegin) {
            return false;
        }
        if (this.numberOfImport != other.numberOfImport) {
            return false;
        }
        if (this.numberOfInvoice != other.numberOfInvoice) {
            return false;
        }
        if (this.numberOfEnd != other.numberOfEnd) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        return Objects.equals(this.colorName, other.colorName);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.productID);
        hash = 29 * hash + Objects.hashCode(this.productName);
        hash = 29 * hash + this.DiscounPercent;
        hash = 29 * hash + this.numberOfSeat;
        hash = 29 * hash + Objects.hashCode(this.colorName);
        hash = 29 * hash + this.numberOfBegin;
        hash = 29 * hash + this.numberOfImport;
        hash = 29 * hash + this.numberOfInvoice;
        hash = 29 * hash + this.numberOfEnd;
        return hash;
    }

    @Override
    public String toString() {
        return "ByStockDTO{" + "productID=" + productID + ", productName=" + productName + ", DiscounPercent=" + DiscounPercent + ", numberOfSeat=" + numberOfSeat + ", colorName=" + colorName + ", numberOfBegin=" + numberOfBegin + ", numberOfImport=" + numberOfImport + ", numberOfInvoice=" + numberOfInvoice + ", numberOfEnd=" + numberOfEnd + '}';
    }
}
