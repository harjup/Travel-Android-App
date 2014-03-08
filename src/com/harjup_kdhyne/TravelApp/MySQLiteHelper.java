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
    private static final int DATABASE_VERSION = 17;

    //Finances table constants
    public static final String PURCHASES_TABLE = "purchases";
    public static final String PURCHASES_COLUMN_ID = "_id";
    public static final String PURCHASES_COLUMN_NAME = "name";
    public static final String PURCHASES_COLUMN_DATE = "date";
    public static final String PURCHASES_COLUMN_PRICE = "price";
    public static final String PURCHASES_COLUMN_CURRENCY = "currency";
    public static final String PURCHASES_COLUMN_EXCHANGE_RATE = "exchangeRate";
    public static final String PURCHASES_COLUMN_NOTES = "notes";
    public static final String PURCHASES_COLUMN_IMAGEURI = "imageUri";

    //Finances Trip Settings constants
    public static final String TRIP_TABLE = "trip_settings";
    public static final String TRIP_COLUMN_ID = "_id";
    public static final String TRIP_COLUMN_NAME = "name";
    public static final String TRIP_COLUMN_START_DATE = "start_date";
    public static final String TRIP_COLUMN_END_DATE = "end_date";
    public static final String TRIP_COLUMN_TOTAL_BUDGET = "total_budget";
    public static final String TRIP_COLUMN_TOTAL_EXPENSES = "total_expenses";
    public static final String TRIP_COLUMN_CURRENCY = "currency";
    public static final String TRIP_COLUMN_EXCHANGE_RATE = "exchange_rate";
    public static final String TRIP_COLUMN_TIMESTAMP = "timestamp";
    public static final String TRIP_COLUMN_REFRESH_FREQUENCY = "refresh_frequency";

    //Translation table constants
    public static final String TRANSLATIONS_TABLE = "translations";
    public static final String TRANSLATIONS_COLUMN_ID = "_id";
    public static final String TRANSLATIONS_COLUMN_HOMEPHRASE = "homePhrase";
    public static final String TRANSLATIONS_COLUMN_HOMELANGUAGE = "homeLanguage";
    public static final String TRANSLATIONS_COLUMN_IMAGEURI = "imageUri";

    //Translation Phrase table constants
    public static final String PHRASE_TABLE = "phrases";
    public static final String PHRASE_COLUMN_ID = "_id";
    public static final String PHRASE_COLUMN_TRANSLATION_ID = "translationId";
    public static final String PHRASE_COLUMN_LANGUAGE = "language";
    public static final String PHRASE_COLUMN_CONTENT = "content";

    //Translation Category table constants
    public static final String CATEGORY_TABLE = "categories";
    public static final String CATEGORY_COLUMN_ID = "_id";
    public static final String CATEGORY_COLUMN_NAME = "name";

    //Translation to Category mapping table
    public static final String TRANSLATION_TO_CATEGORY_TABLE = "translation_category_ownership";
    public static final String TRANSLATIONS_TO_CATEGORY_COLUMN_ID = "_id";
    public static final String TRANSLATIONS_TO_CATEGORY_COLUMN_TRANSLATION_ID = "translationId";
    public static final String TRANSLATIONS_TO_CATEGORY_COLUMN_CATEGORY_ID = "categoryId";


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
            + PURCHASES_COLUMN_PRICE + " text,"
            + PURCHASES_COLUMN_CURRENCY + " text,"
            + PURCHASES_COLUMN_EXCHANGE_RATE + " real,"
            + PURCHASES_COLUMN_NOTES + " text,"
            + PURCHASES_COLUMN_IMAGEURI + " text"
            + ");";

    //Trip Settings table creation statement
    private static final String TRIP_TABLE_CREATE = "create table "
            + TRIP_TABLE
            + "("
            + TRIP_COLUMN_ID + " integer primary key autoincrement, "
            + TRIP_COLUMN_NAME + " text,"
            + TRIP_COLUMN_START_DATE + " text,"
            + TRIP_COLUMN_END_DATE + " text,"
            + TRIP_COLUMN_TOTAL_BUDGET + " real,"
            + TRIP_COLUMN_TOTAL_EXPENSES + " real,"
            + TRIP_COLUMN_CURRENCY + " text,"
            + TRIP_COLUMN_EXCHANGE_RATE + " real,"
            + TRIP_COLUMN_TIMESTAMP + " text,"
            + TRIP_COLUMN_REFRESH_FREQUENCY + " text"
            + ");";

    //Translation table creation statement
    private static final String TRANSLATIONS_TABLE_CREATE = "create table "
            + TRANSLATIONS_TABLE
            + "("
            + TRANSLATIONS_COLUMN_ID + " integer primary key autoincrement, "
            + TRANSLATIONS_COLUMN_HOMEPHRASE + " text,"    //A comma separated list of IDs for phrases it contains. Fairly hackish but whatever
            + TRANSLATIONS_COLUMN_HOMELANGUAGE + " text," //A comma separated list of IDs for categories it contains
            + TRANSLATIONS_COLUMN_IMAGEURI + " text"
            + ");";

    //Translation phrase table creation statement
    private static final String PHRASE_TABLE_CREATE = "create table "
            + PHRASE_TABLE
            + "("
            + PHRASE_COLUMN_ID + " integer primary key autoincrement, "
            + PHRASE_COLUMN_TRANSLATION_ID + " integer not null,"
            + PHRASE_COLUMN_LANGUAGE + " text not null,"
            + PHRASE_COLUMN_CONTENT + " text not null"
            + ");";
    //Translation category table creation statement
    private static final String TRANSLATION_TO_CATEGORY_TABLE_CREATE = "create table "
            + TRANSLATION_TO_CATEGORY_TABLE
            + "("
            + TRANSLATIONS_TO_CATEGORY_COLUMN_ID + " integer primary key autoincrement, "
            + TRANSLATIONS_TO_CATEGORY_COLUMN_TRANSLATION_ID + " integer not null,"
            + TRANSLATIONS_TO_CATEGORY_COLUMN_CATEGORY_ID + " integer not null"
            + ");";

    //Translation category table creation statement
    private static final String CATEGORY_TABLE_CREATE = "create table "
            + CATEGORY_TABLE
            + "("
            + CATEGORY_COLUMN_ID + " integer primary key autoincrement, "
            + CATEGORY_COLUMN_NAME + " text not null"
            + ");";


    // Notes table creation statement
    private static final String NOTES_TABLE_CREATE = "create table "
            + NOTES_TABLE
            + "("
            + NOTES_COLUMN_ID + " integer primary key autoincrement, "
            + NOTES_COLUMN_TITLE + " text not null,"
            + NOTES_COLUMN_CONTENT + " text,"
            + NOTES_COLUMN_TIMESTAMP + " text,"
            + NOTES_COLUMN_IMAGEURI + "text"
            + ");";

    //

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PURCHASES_TABLE_CREATE);
        database.execSQL(TRIP_TABLE_CREATE);
        database.execSQL(TRANSLATIONS_TABLE_CREATE);
        database.execSQL(PHRASE_TABLE_CREATE);
        database.execSQL(CATEGORY_TABLE_CREATE);
        database.execSQL(TRANSLATION_TO_CATEGORY_TABLE_CREATE);
        database.execSQL(NOTES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {
        database.execSQL("DROP TABLE IF EXISTS " + PURCHASES_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TRANSLATIONS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + PHRASE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TRANSLATION_TO_CATEGORY_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(database);
    }
}
