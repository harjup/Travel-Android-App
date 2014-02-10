package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinanceAllPurchases
{

    // This class will only have one instance that will
    // contain an arrayList with all purchases.
    // Singleton

    private static FinanceAllPurchases financeAllPurchases;

    // By creating a Context you gain access to the
    // current state of the complete application.
    // With it you can get information about all the Activities
    // in the app among other things.

    // By accessing the Context you control every part of
    // the application along with everything that app
    // is allowed to access on the device.

    private Context applicationContext;

    // This ArrayList will hold all the purchased items

    private ArrayList<FinancePurchase> purchasesList;

    private FinanceAllPurchases(Context applicationContext)
    {

        this.applicationContext = applicationContext;

        purchasesList = new ArrayList<FinancePurchase>();

        // TODO: Get rid of this before release
        FinancePurchase sandwich = new FinancePurchase();
        sandwich.setPurchaseName("Sandwich");
        //sandwich.setPurchaseTimeStamp();
        sandwich.setPurchasePrice(5.00);
        purchasesList.add(sandwich);

        FinancePurchase Toast = new FinancePurchase();
        Toast.setPurchaseName("Toast");
        //sandwich.setPurchaseTimeStamp();
        Toast.setPurchasePrice(2.50);
        purchasesList.add(Toast);

        FinancePurchase Butter = new FinancePurchase();
        Butter.setPurchaseName("Butter");
        //sandwich.setPurchaseTimeStamp();
        Butter.setPurchasePrice(5.00);
        purchasesList.add(Butter);

        FinancePurchase Squash = new FinancePurchase();
        Squash.setPurchaseName("Squash");
        //sandwich.setPurchaseTimeStamp();
        Squash.setPurchasePrice(2.50);
        purchasesList.add(Squash);

        FinancePurchase Pretzels = new FinancePurchase();
        Pretzels.setPurchaseName("Pretzels");
        //sandwich.setPurchaseTimeStamp();
        Pretzels.setPurchasePrice(5.00);
        purchasesList.add(Pretzels);

        FinancePurchase Bagels = new FinancePurchase();
        Bagels.setPurchaseName("Bagels");
        //sandwich.setPurchaseTimeStamp();
        Bagels.setPurchasePrice(2.50);
        purchasesList.add(Bagels);

        FinancePurchase Oil = new FinancePurchase();
        Oil.setPurchaseName("Oil");
        //sandwich.setPurchaseTimeStamp();
        Oil.setPurchasePrice(5.00);
        purchasesList.add(Oil);

        FinancePurchase Gas = new FinancePurchase();
        Gas.setPurchaseName("Gas");
        //sandwich.setPurchaseTimeStamp();
        Gas.setPurchasePrice(2.50);
        purchasesList.add(Gas);

        FinancePurchase Pretzels2 = new FinancePurchase();
        Pretzels2.setPurchaseName("Pretzels2");
        //sandwich.setPurchaseTimeStamp();
        Pretzels2.setPurchasePrice(5.00);
        purchasesList.add(Pretzels2);

        FinancePurchase Bagels2 = new FinancePurchase();
        Bagels2.setPurchaseName("Bagels2");
        //sandwich.setPurchaseTimeStamp();
        Bagels2.setPurchasePrice(2.50);
        purchasesList.add(Bagels2);

        FinancePurchase Oil2 = new FinancePurchase();
        Oil2.setPurchaseName("Oil2");
        //sandwich.setPurchaseTimeStamp();
        Oil2.setPurchasePrice(5.00);
        purchasesList.add(Oil2);

        FinancePurchase Gas2 = new FinancePurchase();
        Gas2.setPurchaseName("Gas2");
        //sandwich.setPurchaseTimeStamp();
        Gas2.setPurchasePrice(2.50);
        purchasesList.add(Gas2);

    }

    // Checks if an instance of FinanceAllPurchases exists. If it does
    // the one instance is returned. Otherwise the instance is
    // created.

    public static FinanceAllPurchases get(Context context)
    {
        if(financeAllPurchases == null)
        {
            // getApplicationContext returns the global Application object
            // This Context is global to every part of the application
            financeAllPurchases = new FinanceAllPurchases(context.getApplicationContext());
        }
        return financeAllPurchases;
    }

    //Retrieves the entire list of purchases
    public ArrayList<FinancePurchase> getPurchasesList()
    {
        return purchasesList;
    }

    //Loop through the list and find a specific purchase given its ID
    public FinancePurchase getContact(UUID id)
    {
        for(FinancePurchase purchase : purchasesList)
        {
            if(purchase.getPurchaseUUID().equals(id))
            {
                return purchase;
            }
        }
        return null;
    }

}

