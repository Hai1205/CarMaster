/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.statistic;

/**
 *
 * @author ASUS
 */
public class ByMonthDTO {
    private int month;
    private long proceed, workingCapital,profits;

    public void setMonth(int month) {
        this.month = month;
    }

    public void setProceed(long proceed) {
        this.proceed = proceed;
    }

    public void setWorkingCapital(long workingCapital) {
        this.workingCapital = workingCapital;
    }

    public void setProfits(long profits) {
        this.profits = profits;
    }

    public int getMonth() {
        return month;
    }

    public long getProceed() {
        return proceed;
    }

    public long getWorkingCapital() {
        return workingCapital;
    }

    public long getProfits() {
        return profits;
    }

    public ByMonthDTO(int month, long proceed, long workingCapital, long profits) {
        this.month = month;
        this.proceed = proceed;
        this.workingCapital = workingCapital;
        this.profits = profits;
    }
}
