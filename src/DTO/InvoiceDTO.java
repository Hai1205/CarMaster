/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class InvoiceDTO {

    private String invoiceID, customerID, employeeID;
    private Timestamp creationDate;
    private long totalCost;

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public InvoiceDTO(String invoiceID, String customerID, String employeeID, Timestamp creationDate, long totalCost) {
        this.invoiceID = invoiceID;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.creationDate = creationDate;
        this.totalCost = totalCost;
    }

}
