/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Statistic;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ByPerDateInMonthDTO {
    private Date date;
    private long expense, income,profits;

    public void setDate(Date date) {
        this.date = date;
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

    public Date getDate() {
        return date;
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

    public ByPerDateInMonthDTO(Date date, long expense, long income, long profits) {
        this.date = date;
        this.expense = expense;
        this.income = income;
        this.profits = profits;
    }
}
