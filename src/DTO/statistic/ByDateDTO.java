/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.statistic;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ByDateDTO {
    private Date date;
    private long proceed, workingCapital,profits;

    public void setDate(Date date) {
        this.date = date;
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

    public Date getDate() {
        return date;
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

    public ByDateDTO(Date date, long proceed, long workingCapital, long profits) {
        this.date = date;
        this.proceed = proceed;
        this.workingCapital = workingCapital;
        this.profits = profits;
    }
}
