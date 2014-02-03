package com.harjup_kdhyne.TravelApp.Notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paul on 2/3/14.
 * TODO: Write short summary of class
 */
public class MySQLiteHelper extends SQLiteOpenHelper
{

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String DATABASE_NAME = "TravelApp.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    //TODO: Have a creation statement that includes all columns
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null,"
            + COLUMN_CONTENT + " text,"
            + COLUMN_TIMESTAMP + " text"
            + ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(database);
    }
}
