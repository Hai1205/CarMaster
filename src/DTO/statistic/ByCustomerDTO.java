/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Statistic;

/**
 *
 * @author ASUS
 */
public class ByCustomerDTO {
    private String customerID, customerName;
    private int invoiceQuantity;
    private long totalCost;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvoiceQuantity(int invoiceQuantity) {
        this.invoiceQuantity = invoiceQuantity;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getInvoiceQuantity() {
        return invoiceQuantity;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public ByCustomerDTO(String customerID, String customerName, int invoiceQuantity, long totalCost) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.invoiceQuantity = invoiceQuantity;
        this.totalCost = totalCost;
    }
    
}
