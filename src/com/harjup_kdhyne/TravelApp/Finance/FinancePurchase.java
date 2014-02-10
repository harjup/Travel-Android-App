package com.harjup_kdhyne.TravelApp.Finance;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinancePurchase
{
    private UUID purchaseUUID;
    private long purchaseID = -1;           //id for insertion in the database
    private String purchaseName;            //Name/short description of purchase
    private Double purchasePrice;           //Price of purchase
    private Date purchaseDate;              //Time the purchase was made
    private String paidCurrency;            //Currency used to pay for purchase
    private Double purchaseExchangeRate;    //Exchange rate at time of purchase
    private String purchaseNotes;           //Notes to describe purchase
    private String imageId;     //TODO: Determine how to store picture(s), probably pointing to them somehow

    public FinancePurchase()
    {
        //Create a new UUID for this purchase
        //Used to reference this purchase at any given point in time
        purchaseUUID = UUID.randomUUID();

        //Define a default date
        purchaseDate = new Date();
    }



    public UUID getPurchaseUUID() {
        return purchaseUUID;
    }

    public void setPurchaseUUID(UUID purchaseUUID) {
        this.purchaseUUID = purchaseUUID;
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPaidCurrency() {
        return paidCurrency;
    }

    public void setPaidCurrency(String paidCurrency) {
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    //TODO: Format purchaseTimestamp into a nice string
    public String getPurchaseTimeStampAsString()
    {
        return "1/1/2014";
    }
}
