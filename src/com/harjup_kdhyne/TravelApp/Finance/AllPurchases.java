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
        sandwich.setPurchasePrice(5.00);
        purchasesList.add(sandwich);

        Purchase Toast = new Purchase();
        Toast.setPurchaseName("Toast");
        //sandwich.setPurchaseTimeStamp();
        Toast.setPurchasePrice(2.50);
        purchasesList.add(Toast);

        Purchase Butter = new Purchase();
        Butter.setPurchaseName("Butter");
        //sandwich.setPurchaseTimeStamp();
        Butter.setPurchasePrice(5.00);
        purchasesList.add(Butter);

        Purchase Squash = new Purchase();
        Squash.setPurchaseName("Squash");
        //sandwich.setPurchaseTimeStamp();
        Squash.setPurchasePrice(2.50);
        purchasesList.add(Squash);

        Purchase Pretzels = new Purchase();
        Pretzels.setPurchaseName("Pretzels");
        //sandwich.setPurchaseTimeStamp();
        Pretzels.setPurchasePrice(5.00);
        purchasesList.add(Pretzels);

        Purchase Bagels = new Purchase();
        Bagels.setPurchaseName("Bagels");
        //sandwich.setPurchaseTimeStamp();
        Bagels.setPurchasePrice(2.50);
        purchasesList.add(Bagels);

        Purchase Oil = new Purchase();
        Oil.setPurchaseName("Oil");
        //sandwich.setPurchaseTimeStamp();
        Oil.setPurchasePrice(5.00);
        purchasesList.add(Oil);

        Purchase Gas = new Purchase();
        Gas.setPurchaseName("Gas");
        //sandwich.setPurchaseTimeStamp();
        Gas.setPurchasePrice(2.50);
        purchasesList.add(Gas);

        Purchase Pretzels2 = new Purchase();
        Pretzels2.setPurchaseName("Pretzels2");
        //sandwich.setPurchaseTimeStamp();
        Pretzels2.setPurchasePrice(5.00);
        purchasesList.add(Pretzels2);

        Purchase Bagels2 = new Purchase();
        Bagels2.setPurchaseName("Bagels2");
        //sandwich.setPurchaseTimeStamp();
        Bagels2.setPurchasePrice(2.50);
        purchasesList.add(Bagels2);

        Purchase Oil2 = new Purchase();
        Oil2.setPurchaseName("Oil2");
        //sandwich.setPurchaseTimeStamp();
        Oil2.setPurchasePrice(5.00);
        purchasesList.add(Oil2);

        Purchase Gas2 = new Purchase();
        Gas2.setPurchaseName("Gas2");
        //sandwich.setPurchaseTimeStamp();
        Gas2.setPurchasePrice(2.50);
        purchasesList.add(Gas2);

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

