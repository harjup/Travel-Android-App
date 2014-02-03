package com.harjup_kdhyne.TravelApp.Finance;

import java.util.Date;

/**
 * Created by Kyle 2.1 on 1/29/14.
 */
public class FinanceSettings
{
    private Date startDate;
    private Date endDate;

    private double totalBudget;
    private double spentBudget;

    //Getters/Setters
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getSpentBudget() {
        return spentBudget;
    }

    public void setSpentBudget(double spentBudget) {
        this.spentBudget = spentBudget;
    }


}
