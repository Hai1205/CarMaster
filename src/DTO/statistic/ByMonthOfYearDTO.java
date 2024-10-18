/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Statistic;

/**
 *
 * @author ASUS
 */
public class ByMonthOfYearDTO {
    private int month;
    private long expense, income,profits;

    public void setMonth(int month) {
        this.month = month;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public void setProfits(long profits) {
        this.profits = profits;
    }

    public int getMonth() {
        return month;
    }

    public long getExpense() {
        return expense;
    }

    public long getIncome() {
        return income;
    }

    public long getProfits() {
        return profits;
    }

    public ByMonthOfYearDTO(int month, long expense, long income, long profits) {
        this.month = month;
        this.expense = expense;
        this.income = income;
        this.profits = profits;
    }
}
