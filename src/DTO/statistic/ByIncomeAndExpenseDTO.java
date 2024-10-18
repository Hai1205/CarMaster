/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Statistic;

/**
 *
 * @author ASUS
 */
public class ByIncomeAndExpenseDTO {
    private int date;
    private long income, expense, profits;

    public void setTime(int date) {
        this.date = date;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public void setProfits(long profits) {
        this.profits = profits;
    }

    public int getDate() {
        return date;
    }

    public long getIncome() {
        return income;
    }

    public long getExpense() {
        return expense;
    }

    public long getProfits() {
        return profits;
    }

    public ByIncomeAndExpenseDTO(int date, long expense, long income, long profits) {
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.profits = profits;
    }
            
}
