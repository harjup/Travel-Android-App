package com.harjup_kdhyne.TravelApp.Translation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.harjup_kdhyne.TravelApp.MySQLiteHelper;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Paul on 3/1/14.
 * TODO: Write short summary of class
 */
public class TranslationDataSource
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] translationColumns = {
            MySQLiteHelper.TRANSLATIONS_COLUMN_ID,
            MySQLiteHelper.TRANSLATIONS_COLUMN_HOMEPHRASE,
            MySQLiteHelper.TRANSLATIONS_COLUMN_HOMELANGUAGE,
            MySQLiteHelper.TRANSLATIONS_COLUMN_IMAGEURI
    };

    private String[] phraseColumns = {
            MySQLiteHelper.PHRASE_COLUMN_ID,
            MySQLiteHelper.PHRASE_COLUMN_TRANSLATION_ID,
            MySQLiteHelper.PHRASE_COLUMN_LANGUAGE,
            MySQLiteHelper.PHRASE_COLUMN_CONTENT
    };

    private String[] translationCategoryMapColumns = {
            MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_ID,
            MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_TRANSLATION_ID,
            MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_CATEGORY_ID
    };

    private String[] categoryColumns = {
            MySQLiteHelper.CATEGORY_COLUMN_ID,
            MySQLiteHelper.CATEGORY_COLUMN_NAME
    };

    public TranslationDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void saveTranslation(Translation translation)
    {
        //Prepare the translation values to be saved
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TRANSLATIONS_COLUMN_HOMEPHRASE, translation.getHomePhrase());
        values.put(MySQLiteHelper.TRANSLATIONS_COLUMN_HOMELANGUAGE, translation.getHomeLanguage());
        values.put(MySQLiteHelper.TRANSLATIONS_COLUMN_IMAGEURI, translation.getImageId());

        long translationId = translation.getId();

        //Check if the translation exists in the table
        Cursor cursor = database.query(MySQLiteHelper.TRANSLATIONS_TABLE,
                translationColumns,
                MySQLiteHelper.TRANSLATIONS_COLUMN_ID + " = " + translationId,
                null, null, null, null);

        //If translation exists
        if (cursor.moveToFirst())
        {
            database.update(MySQLiteHelper.TRANSLATIONS_TABLE,
                    values,
                    MySQLiteHelper.NOTES_COLUMN_ID + "=" + translation.getId(),
                    null);
        }
        else
        {
            translationId =
                    database.insert(MySQLiteHelper.TRANSLATIONS_TABLE,
                    null,
                    values);
        }

        //Iterate over the phrase objects held by the translation
        //and store them in their respective table
        Set<Map.Entry<String,Phrase>> set = translation.getPhraseHashMap().entrySet();
        Iterator it = set.iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            savePhrase((Phrase)pair.getValue());
            it.remove();
        }

        //For each category in the list...
        List<Category> categoryList = translation.getCategories();
        for(Iterator<Category> iterator = categoryList.iterator(); iterator.hasNext();)
        {
            Category category = iterator.next();
            saveCategory(category);
            addTranslationCategoryMap(translationId, category.getId());
        }
    }

    public void savePhrase(Phrase phrase)
    {
        //Save the phrase in a similar pattern to the translation
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.PHRASE_COLUMN_TRANSLATION_ID, phrase.getTranslationId());
        values.put(MySQLiteHelper.PHRASE_COLUMN_LANGUAGE, phrase.getLanguage());
        values.put(MySQLiteHelper.PHRASE_COLUMN_CONTENT, phrase.getContent());

        long phraseId = phrase.getId();

        Boolean phraseExists =
                checkIfExists(MySQLiteHelper.PHRASE_TABLE,
                phraseColumns,
                MySQLiteHelper.PHRASE_COLUMN_ID,
                String.valueOf(phraseId));

        if (phraseExists)
        {
            database.update(MySQLiteHelper.TRANSLATIONS_TABLE,
                    values,
                    MySQLiteHelper.NOTES_COLUMN_ID + "=" + phraseId,
                    null);
        }
        else
        {
            database.insert(MySQLiteHelper.TRANSLATIONS_TABLE,
                    null,
                    values);
        }

    }

    public void saveCategory(Category category)
    {
        final String myTable =  MySQLiteHelper.CATEGORY_TABLE;
        final String myIdColumn =  MySQLiteHelper.CATEGORY_COLUMN_ID;

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CATEGORY_COLUMN_NAME, category.getName());


        long categoryId = category.getId();

        Boolean categoryExists =
                checkIfExists(myTable,
                        categoryColumns,
                        myIdColumn,
                        String.valueOf(categoryId));

        if (categoryExists)
        {
            database.update(myTable,
                    values,
                    myIdColumn + "=" + categoryId,
                    null);
        }
        else
        {
            database.insert(myTable,
                    null,
                    values);
        }
    }

    public void addTranslationCategoryMap(long translationId, long categoryId)
    {
        final String myTable =  MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE;

        Boolean entryExists = database.query(myTable,
                translationCategoryMapColumns,
                "(" + translationCategoryMapColumns[1] + " = " + translationId + ", "
                + translationCategoryMapColumns[2] + " = " + categoryId + ")",
                null, null, null, null).moveToFirst();

        if (!entryExists)
        {
            ContentValues values = new ContentValues();
            values.put(translationCategoryMapColumns[1], translationId);
            values.put(translationCategoryMapColumns[2], categoryId);

            database.insert(myTable,
                    null,
                    values);
        }
    }

    Boolean checkIfExists(String tableName, String[] tableColumns, String key, String value){
        //Check if the entry exists in the table
        return database.query(tableName,
                tableColumns,
                key + " = " + value,
                null, null, null, null).moveToFirst();
    }
}
