/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.statistic;

/**
 *
 * @author ASUS
 */
public class BySupplierDTO {
    private String supplierID, supplierName;
    private int importQuantity;
    private long totalCost;

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public BySupplierDTO(String supplierID, String supplierName, int importQuantity, long totalCost) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.importQuantity = importQuantity;
        this.totalCost = totalCost;
    }
}
