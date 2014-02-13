package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class AllPurchases
{

    // This class will only have one instance that will
    // contain an arrayList with all purchases.
    // Singleton

    private static AllPurchases allPurchases;

    // By creating a Context you gain access to the
    // current state of the complete application.
    // With it you can get information about all the Activities
    // in the app among other things.

    // By accessing the Context you control every part of
    // the application along with everything that app
    // is allowed to access on the device.

    private Context applicationContext;

    // This ArrayList will hold all the purchased items

    private ArrayList<Purchase> purchasesList;

    private AllPurchases(Context applicationContext)
    {

        this.applicationContext = applicationContext;

        purchasesList = new ArrayList<Purchase>();

        // TODO: Get rid of this before release
        Purchase sandwich = new Purchase();
        sandwich.setPurchaseName("Sandwich");
        //sandwich.setPurchaseTimeStamp();
        sandwich.setPurchasePrice("5.00");

    }

    // Checks if an instance of AllPurchases exists. If it does
    // the one instance is returned. Otherwise the instance is
    // created.

    public static AllPurchases get(Context context)
    {
        if(allPurchases == null)
        {
            // getApplicationContext returns the global Application object
            // This Context is global to every part of the application
            allPurchases = new AllPurchases(context.getApplicationContext());
        }
        return allPurchases;
    }

    //Retrieves the entire list of purchases
    public ArrayList<Purchase> getPurchasesList()
    {
        return purchasesList;
    }

    //Loop through the list and find a specific purchase given its ID
    public Purchase getContact(UUID id)
    {
        for(Purchase purchase : purchasesList)
        {
            if(purchase.getPurchaseUUID().equals(id))
            {
                return purchase;
            }
        }
        return null;
    }

}

