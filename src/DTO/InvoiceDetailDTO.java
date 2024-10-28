/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class InvoiceDetailDTO {
    private String invoiceID, productID;
    private long price, cost, basicPrice;
    private int quantity;

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
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

    public void setBasicprice(long basicPrice) {
        this.basicPrice = basicPrice;
    }

    public void setCost(int quantity) {
        this.cost = quantity * price;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public long getBasicprice() {
        return basicPrice;
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

    public InvoiceDetailDTO(String invoiceID, String productID, long price, int quantity, long cost) {
        this.invoiceID = invoiceID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.cost = cost;
    }
}
