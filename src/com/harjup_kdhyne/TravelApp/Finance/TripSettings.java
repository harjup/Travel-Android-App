package com.harjup_kdhyne.TravelApp.Finance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle 2.1 on 1/29/14
 * Tracks various metrics for each trip including days, money, and default currency
 */
//TODO: There is a potential for there to be more than one trip planned on the app. For now we're sticking to one.
public class TripSettings
{
    private long tripID;

    private Date startDate;
    private Date endDate;

    private double totalBudget;
    private double totalExpenses;

    private String currency;

    private double currentExchangeRate;

    //Getters and Setters
    public long getTripID() {
        return tripID;
    }

    public void setTripID(long tripID) {
        this.tripID = tripID;
    }

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

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCurrentExchangeRate() {
        return currentExchangeRate;
    }

    public void setCurrentExchangeRate(double currentExchangeRate) {
        this.currentExchangeRate = currentExchangeRate;
    }

    public String getStartDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(startDate);
    }

    public String getEndDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(endDate);
    }

    public void setStartDateFromString(String dateString){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {this.startDate = dateFormat.parse(dateString);}
        catch (ParseException e) {e.printStackTrace();}
    }

    public void setEndDateFromString(String dateString){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {this.endDate = dateFormat.parse(dateString);}
        catch (ParseException e) {e.printStackTrace();}
    }
}
