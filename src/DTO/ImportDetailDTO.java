/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class ImportDetailDTO {
    private String importID, productID;
    private long price, cost;
    private int quantity;

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getImportID() {
        return importID;
    }

    public String getProductID() {
        return productID;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getCost() {
        return cost;
    }

    public ImportDetailDTO(String importID, String productID, long price, int quantity, long cost) {
        this.importID = importID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.cost = cost;
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
        final ImportDetailDTO other = (ImportDetailDTO) obj;
        if (!this.importID.equals(other.importID)) {
            return false;
        }
        if (!this.productID.equals(other.productID)) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        return this.cost == other.cost;
    }

    @Override
    public String toString() {
        return "ImportDetailDTO{" + "importID=" + importID + ", productID=" + productID + ", quantity=" + quantity + ", price=" + price + ", productID=" + productID + '}';
    }
}
