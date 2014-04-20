package com.harjup_kdhyne.TravelApp.Finance;

import org.openexchangerates.oerjava.Currency;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle 2.1 on 2/2/14
 * Holds individual purchase data
 */
public class Purchase implements Serializable {
    private long purchaseID = -1;           //id for insertion in the database
    private String purchaseName;            //Name/short description of purchase
    private Date purchaseDate;              //Time the purchase was made TODO: Change to JodaTime => DateTime
    private String purchasePrice;           //Price of purchase TODO: Change from string to double!!!
    private Currency paidCurrency;            //Currency used to pay for purchase
    private Double purchaseExchangeRate;    //Exchange rate at time of purchase
    private String purchaseNotes;           //Notes to describe purchase

    public Purchase()
    {
        //Define a default date
        purchaseName = "";
        purchaseDate = new Date();
        purchasePrice = "";
        paidCurrency = Currency.USD;
        purchaseExchangeRate = 1.00;
        purchaseNotes = "";
    }

    public long getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(long purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String title) {
        this.purchaseName = title;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Currency getPaidCurrency() {
        return paidCurrency;
    }

    public void setPaidCurrency(Currency paidCurrency) {
        this.paidCurrency = paidCurrency;
    }

    public Double getPurchaseExchangeRate() {
        return purchaseExchangeRate;
    }

    public void setPurchaseExchangeRate(Double purchaseExchangeRate) {
        this.purchaseExchangeRate = purchaseExchangeRate;
    }

    public String getPurchaseNotes() {
        return purchaseNotes;
    }

    public void setPurchaseNotes(String purchaseNotes) {
        this.purchaseNotes = purchaseNotes;
    }

    // Used to return date in the format mm/dd/yyyy
    // as a string
    public String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(purchaseDate);
    }

    public void setDateFromString(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {this.purchaseDate = dateFormat.parse(dateString);}
        catch (ParseException e) {e.printStackTrace();}
    }
}
