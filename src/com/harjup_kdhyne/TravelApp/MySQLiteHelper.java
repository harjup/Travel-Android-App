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
    //...
    //...

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
    //...
    //...

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
        //database.execSQL(FINANCES_TABLE_CREATE);
        //database.execSQL(TRANSLATION_TABLE_CREATE);
        database.execSQL(NOTES_TABLE_CREATE);
    }

    //TODO: Run table drops for other data
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {
        //database.execSQL("DROP TABLE IF EXISTS " + FINANCES_TABLE);
        //database.execSQL("DROP TABLE IF EXISTS " + TRANSLATION_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(database);
    }
}
