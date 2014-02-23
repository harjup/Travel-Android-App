package com.harjup_kdhyne.TravelApp.Finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.harjup_kdhyne.TravelApp.MySQLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle 2.1 on 2/9/14
 * Retrieves information from the database related to trip settings and purchases
 */
public class PurchasesDataSource
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    //All column names for trip settings table
    private String[] tripSettingsColumns = {
            MySQLiteHelper.TRIP_COLUMN_START_DATE,
            MySQLiteHelper.TRIP_COLUMN_END_DATE,
            MySQLiteHelper.TRIP_COLUMN_TOTAL_BUDGET,
            MySQLiteHelper.TRIP_COLUMN_TOTAL_EXPENSES,
            MySQLiteHelper.TRIP_COLUMN_CURRENCY
    };

    //All column names for purchases table
    private String[] allPurchasesColumns = {
            MySQLiteHelper.PURCHASES_COLUMN_ID,
            MySQLiteHelper.PURCHASES_COLUMN_NAME,
            MySQLiteHelper.PURCHASES_COLUMN_DATE,
            MySQLiteHelper.PURCHASES_COLUMN_PRICE,
            MySQLiteHelper.PURCHASES_COLUMN_CURRENCY,
            MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE,
            MySQLiteHelper.PURCHASES_COLUMN_NOTES
    };

    public PurchasesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Purchase createPurchase(Purchase purchase) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.PURCHASES_COLUMN_NAME, purchase.getPurchaseName());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_DATE, purchase.getDateString());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_PRICE, purchase.getPurchasePrice());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_CURRENCY, purchase.getPaidCurrency());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE, purchase.getPurchaseExchangeRate());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_NOTES, purchase.getPurchaseNotes());

        long insertID = database.insert(MySQLiteHelper.PURCHASES_TABLE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.PURCHASES_TABLE,
                allPurchasesColumns,
                MySQLiteHelper.PURCHASES_COLUMN_ID + " = " + insertID,
                null, null, null, null);

        cursor.moveToFirst();
        Purchase newPurchase = cursorToPurchase(cursor);
        cursor.close();
        return newPurchase;
    }

    public TripSettings createTripSettings(TripSettings tripSettings) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.TRIP_COLUMN_START_DATE, tripSettings.getStartDateAsString());
        values.put(MySQLiteHelper.TRIP_COLUMN_END_DATE, tripSettings.getEndDateAsString());
        values.put(MySQLiteHelper.TRIP_COLUMN_TOTAL_BUDGET, tripSettings.getTotalBudget());
        values.put(MySQLiteHelper.TRIP_COLUMN_TOTAL_EXPENSES, tripSettings.getTotalExpenses());
        values.put(MySQLiteHelper.TRIP_COLUMN_CURRENCY, tripSettings.getCurrency());

        long insertID = database.insert(MySQLiteHelper.TRIP_TABLE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TRIP_TABLE,
                tripSettingsColumns,
                MySQLiteHelper.TRIP_COLUMN_ID + " = " + insertID,
                null, null, null, null);

        cursor.moveToFirst();
        TripSettings newTripSettings = cursorToTripSettings(cursor);
        cursor.close();
        return newTripSettings;
    }

    public void updatePurchase(Purchase purchase) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.PURCHASES_COLUMN_NAME, purchase.getPurchaseName());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_DATE, purchase.getDateString());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_PRICE, purchase.getPurchasePrice());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_CURRENCY, purchase.getPaidCurrency());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE, purchase.getPurchaseExchangeRate());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_NOTES, purchase.getPurchaseNotes());

        database.update(MySQLiteHelper.PURCHASES_TABLE,
                values,
                MySQLiteHelper.PURCHASES_COLUMN_ID + "=" + purchase.getPurchaseID(),
                null);
    }

    public void updateTripSettings(TripSettings tripSettings) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.TRIP_COLUMN_START_DATE, tripSettings.getStartDateAsString());
        values.put(MySQLiteHelper.TRIP_COLUMN_END_DATE, tripSettings.getEndDateAsString());
        values.put(MySQLiteHelper.TRIP_COLUMN_TOTAL_BUDGET, tripSettings.getTotalBudget());
        values.put(MySQLiteHelper.TRIP_COLUMN_TOTAL_EXPENSES, tripSettings.getTotalExpenses());
        values.put(MySQLiteHelper.TRIP_COLUMN_CURRENCY, tripSettings.getCurrency());

        database.update(MySQLiteHelper.TRIP_TABLE,
                values,
                MySQLiteHelper.TRIP_COLUMN_ID + "=" + tripSettings.getTripID(),
                null);
    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<Purchase>();

        Cursor cursor = database.query(MySQLiteHelper.PURCHASES_TABLE,
                allPurchasesColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Purchase purchase = cursorToPurchase(cursor);
            purchases.add(purchase);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return purchases;
    }

    public List<TripSettings> getAllTripSettings() {
        List<TripSettings> tripSettings = new ArrayList<TripSettings>();

        Cursor cursor = database.query(MySQLiteHelper.TRIP_TABLE,
                tripSettingsColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TripSettings setting = cursorToTripSettings(cursor);
            tripSettings.add(setting);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tripSettings;
    }

    public void deletePurchase(Purchase purchase){
        long id = purchase.getPurchaseID();
        System.out.println("Purchase deleted with id: " + id);
        database.delete(MySQLiteHelper.PURCHASES_TABLE, MySQLiteHelper.PURCHASES_COLUMN_ID + " = " + id, null);
    }

    public void deleteTripSettings(TripSettings settings){
        long id = settings.getTripID();
        System.out.println("Trip Settings deleted with id: " + id);
        database.delete(MySQLiteHelper.TRIP_TABLE, MySQLiteHelper.TRIP_COLUMN_ID + " = " + id, null);
    }

    private Purchase cursorToPurchase(Cursor cursor) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseID(cursor.getLong(0));
        purchase.setPurchaseName(cursor.getString(1));
        purchase.setDateFromString(cursor.getString(2));
        purchase.setPurchasePrice(cursor.getString(3));
        purchase.setPaidCurrency(cursor.getString(4));
        purchase.setPurchaseExchangeRate(cursor.getDouble(5));
        purchase.setPurchaseNotes(cursor.getString(6));
        //TODO: Set image url
        return purchase;
    }

    private TripSettings cursorToTripSettings(Cursor cursor) {
        TripSettings settings = new TripSettings();
        settings.setTripID(cursor.getLong(0));
        settings.setStartDateFromString(cursor.getString(1));
        settings.setEndDateFromString(cursor.getString(2));
        settings.setTotalBudget(cursor.getDouble(3));
        settings.setTotalExpenses(cursor.getDouble(4));
        settings.setCurrency(cursor.getString(5));
        settings.setCurrentExchangeRate(cursor.getDouble(6));
        return settings;
    }


}