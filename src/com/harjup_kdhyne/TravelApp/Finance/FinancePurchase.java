package com.harjup_kdhyne.TravelApp.Finance;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinancePurchase
{
    private UUID purchaseID;

    private String purchaseName;            //Name/short description of purchase
    private Double purchasePrice;           //Price of purchase
    private Date purchaseTimeStamp;         //Time the purchase was made
    private String paidCurrency;            //Currency used to pay for purchase
    private Double purchaseExchangeRate;    //Exchange rate at time of purchase

    private String imageId;     //TODO: Determine how to store picture(s), probably pointing to them somehow

    public FinancePurchase()
    {
        //Create a new UUID for this purchase
        //Used to reference this purchase at any given point in time
        purchaseID = UUID.randomUUID();

        //Define a default date
        purchaseTimeStamp = new Date();

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

    public Date getPurchaseTimeStamp() {
        return purchaseTimeStamp;
    }

    public void setPurchaseTimeStamp(Date purchaseTimeStamp) {
        this.purchaseTimeStamp = purchaseTimeStamp;
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

    public UUID getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(UUID purchaseID) {
        this.purchaseID = purchaseID;
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
