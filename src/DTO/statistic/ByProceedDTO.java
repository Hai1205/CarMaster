/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.statistic;

/**
 *
 * @author ASUS
 */
public class ByProceedDTO {
    private int time;// ngày, tháng, năm
    private long proceed, workingCapital,profits;

    public void setTime(int time) {
        this.time = time;
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

    public int getTime() {
        return time;
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

    public ByProceedDTO(int time, long proceed, long workingCapital, long profits) {
        this.time = time;
        this.proceed = proceed;
        this.workingCapital = workingCapital;
        this.profits = profits;
    }
            
}
