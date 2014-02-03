package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
            if(purchase.getPurchaseID().equals(id))
            {
                return purchase;
            }
        }
        return null;
    }

}

