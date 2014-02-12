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
 * Created by Kyle 2.1 on 2/9/14.
 */
public class PurchasesDataSource
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.PURCHASES_COLUMN_ID,
            MySQLiteHelper.PURCHASES_COLUMN_NAME,
            MySQLiteHelper.PURCHASES_COLUMN_DATE,
            MySQLiteHelper.PURCHASES_COLUMN_PRICE,
            MySQLiteHelper.PURCHASES_COLUMN_CURRENCY,
            MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE,
            MySQLiteHelper.PURCHASES_COLUMN_NOTES
    };

    public PurchasesDataSource(Context context)
    {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public Purchase createPurchase(Purchase purchase)
    {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.PURCHASES_COLUMN_NAME, purchase.getPurchaseName());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_DATE, purchase.getPurchaseDateAsString());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_PRICE, purchase.getPurchasePrice());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_CURRENCY, purchase.getPaidCurrency());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE, purchase.getPurchaseExchangeRate());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_NOTES, purchase.getPurchaseNotes());

        long insertID = database.insert(MySQLiteHelper.PURCHASES_TABLE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.PURCHASES_TABLE,
                allColumns,
                MySQLiteHelper.PURCHASES_COLUMN_ID + " = " + insertID,
                null, null, null, null);

        cursor.moveToFirst();
        Purchase newPurchase = cursorToPurchase(cursor);
        cursor.close();
        return newPurchase;
    }

    public void updatePurchase(Purchase purchase)
    {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.PURCHASES_COLUMN_NAME, purchase.getPurchaseName());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_DATE, purchase.getPurchaseDateAsString());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_PRICE, purchase.getPurchasePrice());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_CURRENCY, purchase.getPaidCurrency());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_EXCHANGE_RATE, purchase.getPurchaseExchangeRate());
        values.put(MySQLiteHelper.PURCHASES_COLUMN_NOTES, purchase.getPurchaseNotes());

        database.update(MySQLiteHelper.PURCHASES_TABLE,
                values,
                MySQLiteHelper.PURCHASES_COLUMN_ID + "=" + purchase.getPurchaseID(),
                null);

    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<Purchase>();

        Cursor cursor = database.query(MySQLiteHelper.PURCHASES_TABLE,
                allColumns, null, null, null, null, null);

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

    public void deletePurchase(Purchase purchase){
        long id = purchase.getPurchaseID();
        System.out.println("Purchase deleted with id: " + id);
        database.delete(MySQLiteHelper.PURCHASES_TABLE, MySQLiteHelper.PURCHASES_COLUMN_ID
                + " = " + id, null);

    }

    private Purchase cursorToPurchase(Cursor cursor) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseID(cursor.getLong(0));
        purchase.setPurchaseName(cursor.getString(1));
        //purchase.setPurchaseDate(cursor.getString(2));
        purchase.setPurchasePrice(cursor.getDouble(3));
        purchase.setPurchaseExchangeRate(cursor.getDouble(4));
        purchase.setPurchaseNotes(cursor.getString(5));
        //TODO: Set image url
        return purchase;
    }


}
