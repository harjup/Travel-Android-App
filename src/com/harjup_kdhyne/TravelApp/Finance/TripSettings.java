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
    private long tripID = -1;
    private String name;

    private Date startDate;
    private Date endDate;

    private double totalBudget;
    private double totalExpenses;

    private String currency;
    private double currentExchangeRate;
    private Date exchangeRateTimeStamp;
    private FrequencySettings refreshFrequency;

    //Options for how often the exchange rate is automatically refreshed
    public enum FrequencySettings
    {
        THREE_DAYS("Every three days"),
        EVERY_OTHER("Every other day"),
        DAILY("Daily"),
        HOURLY("Hourly");

        private String stringValue;
        private FrequencySettings(String toString)
        {
            stringValue = toString;
        }

        @Override
        public String toString()
        {
            return stringValue;
        }
    }

    public TripSettings()
    {
        //Define default values
        name = "";
        startDate = new Date();
        endDate = new Date();
        totalBudget = 0.00;
        totalExpenses = 0.00;
        currency = "";
        currentExchangeRate = 0.00;
        exchangeRateTimeStamp = new Date();
        refreshFrequency = FrequencySettings.DAILY;
    }

    //Getters and Setters
    public long getTripID() {
        return tripID;
    }

    public void setTripID(long tripID) {
        this.tripID = tripID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getExchangeRateTimeStamp() {
        return exchangeRateTimeStamp;
    }

    public void setExchangeRateTimeStamp(Date exchangeRateTimeStamp) {
        this.exchangeRateTimeStamp = exchangeRateTimeStamp;
    }

    public FrequencySettings getRefreshFrequency() {
        return refreshFrequency;
    }

    public void setRefreshFrequency(FrequencySettings refreshFrequency) {
        this.refreshFrequency = refreshFrequency;
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

        try {setStartDate(dateFormat.parse(dateString));}
        catch (ParseException e) {e.printStackTrace();}
    }

    public void setEndDateFromString(String dateString){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {setEndDate(dateFormat.parse(dateString));}
        catch (ParseException e) {e.printStackTrace();}
    }
}
