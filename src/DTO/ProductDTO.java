/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class ProductDTO {
    private String productID, supplierID, productImg, productName, status;
    private int quantity;
    private long basicPrice, sellPrice;

    public String getProductID() {
        return productID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getProductImg() {
        return productImg;
    }

    public String getProductName() {
        return productName;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getBasicPrice() {
        return basicPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBasicPrice(long basicPrice) {
        this.basicPrice = basicPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public ProductDTO(String productID, String supplierID, String productName, String productImg, String status,
            int quantity,
            long basicPrice, long sellPrice) {
        this.productID = productID;
        this.supplierID = supplierID;
        this.productName = productName;
        this.status = status;
        this.quantity = quantity;
        this.basicPrice = basicPrice;
        this.sellPrice = sellPrice;
        this.productImg = productImg;
    }

    public ProductDTO(String productID, String supplierID, String productName, String productImg, String status, long sellPrice) {
        this.productID = productID;
        this.supplierID = supplierID;
        this.productName = productName;
        this.status = status;
        this.sellPrice = sellPrice;
        this.productImg = productImg;
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
        final ProductDTO other = (ProductDTO) obj;
        if (!this.productID.equals(other.productID)) {
            return false;
        }
        if (!this.productName.equals(other.productName)) {
            return false;
        }
        if (!this.status.equals(other.status)) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (this.basicPrice != other.basicPrice) {
            return false;
        }
        return this.sellPrice != other.sellPrice;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "productID=" + productID + ", productName=" + productName + ", quantity=" + quantity
                + ", basicPrice=" + basicPrice + ", sellPrice=" + sellPrice + ", status=" + status + '}';
    }
}
