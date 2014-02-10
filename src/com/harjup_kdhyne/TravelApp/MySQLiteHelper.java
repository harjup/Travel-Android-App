package com.harjup_kdhyne.TravelApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paul on 2/3/14.
 * Contains helper functions and constants for creating and querying the SQLite database
 */
public class MySQLiteHelper extends SQLiteOpenHelper
{
    //Database constants
    private static final String DATABASE_NAME = "TravelApp.db";
    private static final int DATABASE_VERSION = 1;

    //Finances table constants
    public static final String PURCHASES_TABLE = "purchases";
    public static final String PURCHASES_COLUMN_ID = "_id";
    public static final String PURCHASES_COLUMN_NAME = "name";
    public static final String PURCHASES_COLUMN_DATE = "date";
    public static final String PURCHASES_COLUMN_PRICE = "content";
    public static final String PURCHASES_COLUMN_CURRENCY = "currency";
    public static final String PURCHASES_COLUMN_EXCHANGE_RATE = "exchangeRate";
    public static final String PURCHASES_COLUMN_NOTES = "notes";
    public static final String PURCHASES_COLUMN_IMAGEURI = "imageUri";

    //Translation table constants
    //...
    //...

    //Notes table constants
    public static final String NOTES_TABLE = "notes";
    public static final String NOTES_COLUMN_ID = "_id";
    public static final String NOTES_COLUMN_TITLE = "title";
    public static final String NOTES_COLUMN_CONTENT = "content";
    public static final String NOTES_COLUMN_TIMESTAMP = "timestamp";
    public static final String NOTES_COLUMN_IMAGEURI = "imageUri";

    //Finances table creation statement
    private static final String PURCHASES_TABLE_CREATE = "create table "
            + PURCHASES_TABLE
            + "("
            + PURCHASES_COLUMN_ID + " integer primary key autoincrement, "
            + PURCHASES_COLUMN_NAME + " text not null,"
            + PURCHASES_COLUMN_DATE + " text,"
            + PURCHASES_COLUMN_PRICE + " real,"
            + PURCHASES_COLUMN_CURRENCY + " text,"
            + PURCHASES_COLUMN_EXCHANGE_RATE + " real,"
            + PURCHASES_COLUMN_NOTES + " text"
            + ");";

    //Translation table creation statement
    //...
    //...

    // Notes table creation statement
    //TODO: Have a creation statement that includes image uri
    private static final String NOTES_TABLE_CREATE = "create table "
            + NOTES_TABLE
            + "("
            + NOTES_COLUMN_ID + " integer primary key autoincrement, "
            + NOTES_COLUMN_TITLE + " text not null,"
            + NOTES_COLUMN_CONTENT + " text,"
            + NOTES_COLUMN_TIMESTAMP + " text"
            + ");";

    //

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //TODO: Create tables for other data
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PURCHASES_TABLE_CREATE);
        //database.execSQL(TRANSLATION_TABLE_CREATE);
        database.execSQL(NOTES_TABLE_CREATE);
    }

    //TODO: Run table drops for other data
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {
        database.execSQL("DROP TABLE IF EXISTS " + PURCHASES_TABLE);
        //database.execSQL("DROP TABLE IF EXISTS " + TRANSLATION_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(database);
    }
}
