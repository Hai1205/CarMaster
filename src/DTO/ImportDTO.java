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
public class ImportDTO {
    private String importID, supplierID, employeeID;
    private Timestamp creationDate;
    private long totalCost;

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
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

    public String getImportID() {
        return importID;
    }

    public String getSupplierID() {
        return supplierID;
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

    public ImportDTO(String importID, String supplierID, String employeeID, Timestamp creationDate, long totalCost) {
        this.importID = importID;
        this.supplierID = supplierID;
        this.employeeID = employeeID;
        this.creationDate = creationDate;
        this.totalCost = totalCost;
    }

    public ImportDTO(String importID, String supplierID, String employeeID, long totalCost) {
        this.importID = importID;
        this.supplierID = supplierID;
        this.employeeID = employeeID;
        this.totalCost = totalCost;
    }
}
