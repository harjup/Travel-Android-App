package com.harjup_kdhyne.TravelApp.Finance;

import android.util.Log;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.openexchangerates.oerjava.Currency;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle 2.1 on 1/29/14
 * Tracks various metrics for each trip including days, money, and default targetCurrency.
 * There is a potential for there to be more than one trip planned on the app. For now we're sticking to one
 */
public class TripSettings implements Serializable
{
    private long tripID = -1;
    private String name;

    private DateTime startDate;
    private DateTime endDate;

    private double totalBudget;
    private double totalExpenses;

    private Currency targetCurrency;
    private double currentExchangeRate;
    private DateTime exchangeRateTimeStamp;
    private FrequencySettings refreshFrequency;

    private DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");

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

        public static FrequencySettings fromString(String text)
        {
            if (text != null)
            {
                for(FrequencySettings frequency : FrequencySettings.values())
                {
                    if (text.equalsIgnoreCase(frequency.stringValue))
                        return frequency;
                }
            }
            return null;
        }
    }

    public TripSettings()
    {
        //Define default values
        name = "";
        startDate = new DateTime();
        endDate = new DateTime().plusDays(90);
        totalBudget = 0.00;
        totalExpenses = 0.00;
        targetCurrency = Currency.EUR;
        currentExchangeRate = 0.00;
        exchangeRateTimeStamp = new DateTime();
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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
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

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getCurrentExchangeRate() {
        return currentExchangeRate;
    }

    public void setCurrentExchangeRate(double currentExchangeRate) {
        this.currentExchangeRate = currentExchangeRate;
    }

    public DateTime getExchangeRateTimeStamp() {
        return exchangeRateTimeStamp;
    }

    public void setExchangeRateTimeStamp(DateTime exchangeRateTimeStamp) {
        this.exchangeRateTimeStamp = exchangeRateTimeStamp;
    }

    public FrequencySettings getRefreshFrequency() {
        return refreshFrequency;
    }

    public void setRefreshFrequency(FrequencySettings refreshFrequency) {
        this.refreshFrequency = refreshFrequency;
    }

    public String getStartDateAsString()
    {
        return fmt.print(startDate);
    }

    public String getEndDateAsString()
    {
        return fmt.print(endDate);
    }

    public String getExchangeRateTimeStampAsString()
    {
        return fmt.print(exchangeRateTimeStamp);
    }

    public void setStartDateFromString(String dateString)
    {
        try {setStartDate(fmt.parseDateTime(dateString));}
        catch (IllegalArgumentException e) {e.printStackTrace();}
    }

    public void setEndDateFromString(String dateString)
    {
        try {setEndDate(fmt.parseDateTime(dateString));}
        catch (IllegalArgumentException e) {e.printStackTrace();}
    }

    public void setExchangeRateTimeStampFromString(String dateString)
    {
        try {setExchangeRateTimeStamp(fmt.parseDateTime(dateString));}
        catch (IllegalArgumentException e) {e.printStackTrace();}
    }
}
