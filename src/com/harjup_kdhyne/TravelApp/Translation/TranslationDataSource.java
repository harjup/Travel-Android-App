package com.harjup_kdhyne.TravelApp.Translation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.harjup_kdhyne.TravelApp.MySQLiteHelper;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Paul on 3/1/14.
 * TODO: Write short summary of class
 */
public class TranslationDataSource
{
    private static TranslationDataSource instance = null;
    public static TranslationDataSource getInstance(Context context){
        if (instance == null){
            instance = new TranslationDataSource(context.getApplicationContext());
        }
        return instance;
    }

    protected TranslationDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
        CheckDBContents();
    }

    public void close(){
        dbHelper.close();
    }


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
                MySQLiteHelper.TRANSLATIONS_COLUMN_HOMEPHRASE + " = " + "'" + translation.getHomePhrase() + "'",
                null, null, null, null);

        //If translation exists (search by phrase), get its id
        if (cursor.moveToFirst())
        {
            database.update(MySQLiteHelper.TRANSLATIONS_TABLE,
                    values,
                    MySQLiteHelper.TRANSLATIONS_COLUMN_HOMEPHRASE + " = " + "'" + translation.getHomePhrase() + "'",
                    null);

            //Set the translation object's Id to the Id of the found translation in the DB
            translationId = cursor.getLong(cursor.getColumnIndex(translationColumns[0]));
        }
        //If translation isn't found in db, insert it into the database and get its id
        else
        {
            translationId =
                    database.insert(MySQLiteHelper.TRANSLATIONS_TABLE,
                    null,
                    values);


        }
        translation.setId(translationId);

        //Iterate over the phrase objects held by the translation
        //and store them in their respective table
        //Set<Map.Entry<String,String>> set = translation.getPhraseHashMap().entrySet();
        List<Phrase> phraseList = translation.getPhraseList();
        Iterator it = phraseList.iterator();
        while(it.hasNext())
        {
            Phrase phraseToSave = (Phrase)it.next();

            phraseToSave.setTranslationId(translationId);
            savePhrase(phraseToSave);
            it.remove();
        }

        //For each category in the list...
        List<Category> categoryList = translation.getCategories();

        //We are refreshing the translation-category map to reflect the set in the translation object,
        //So drop all the current map rows for the current translation object...
        database.delete(
                MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE,
                translationCategoryMapColumns[1] + " =?",
                new String[]{String.valueOf(translationId)}
                );

        //And then add new rows reflecting the currently selected categories
        for(Iterator<Category> iterator = categoryList.iterator(); iterator.hasNext();)
        {
            Category category = iterator.next();
            long categoryId = saveCategory(category);
            addTranslationCategoryMap(translationId, categoryId);
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

        //We are looking for a phrase that is in the given language AND is associated with the correct translation
        //By ignoring a check on content we can update the content value if it changes
        String whereStatement =
                "(" + MySQLiteHelper.PHRASE_COLUMN_LANGUAGE + " = " + "'" + phrase.getLanguage() + "') AND"
                + "(" + MySQLiteHelper.PHRASE_COLUMN_TRANSLATION_ID + " = " + phrase.getTranslationId() + ")";


        Cursor cursor = database.query(MySQLiteHelper.PHRASE_TABLE,
                phraseColumns,
                whereStatement,
                null, null, null, null);

        Boolean phraseExists = cursor.moveToFirst();
                /*checkIfExists(MySQLiteHelper.PHRASE_TABLE,
                phraseColumns,
                MySQLiteHelper.PHRASE_COLUMN_ID,
                String.valueOf(phraseId));*/

        if (phraseExists)
        {
            database.update(MySQLiteHelper.PHRASE_TABLE,
                    values,
                    whereStatement,
                    null);
        }
        else
        {
            database.insert(MySQLiteHelper.PHRASE_TABLE,
                    null,
                    values);
        }

    }

    public long saveCategory(Category category)
    {
        final String myTable =  MySQLiteHelper.CATEGORY_TABLE;
        final String myNameColumn =  MySQLiteHelper.CATEGORY_COLUMN_NAME;

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CATEGORY_COLUMN_NAME, category.getName());


        String categoryName = category.getName();
        long categoryId = category.getId();

       /* Boolean categoryExists =
                checkIfExists(myTable,
                        categoryColumns,
                        myNameColumn,
                        String.valueOf(categoryName));*/



        Cursor c = database.query(myTable,
                categoryColumns,
                myNameColumn + " = " + "'" + categoryName + "'",
                null, null, null, null);

        Boolean categoryExists = c.moveToFirst();

        if (categoryExists)
        {
            database.update(myTable,
                    values,
                    myNameColumn + "=" + "'" + categoryName + "'",
                    null);

            categoryId = c.getLong(c.getColumnIndex(categoryColumns[0]));
        }
        else
        {
            categoryId =
                    database.insert(myTable,
                    null,
                    values);
        }

        return categoryId;
    }

    public void addTranslationCategoryMap(long translationId, long categoryId)
    {
        final String myTable =  MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE;

        Boolean entryExists = database.query(myTable,
                translationCategoryMapColumns,
                "(" + translationCategoryMapColumns[1] + " = " + translationId + ") AND ("
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

    public void editTranslationCategoryMap(List<Long> translationIdList, Long categoryId){
        final String myTable =  MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE;

        database.delete(
                MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE,
                translationCategoryMapColumns[2] + " =?",
                new String[]{String.valueOf(categoryId)}
        );


        Iterator<Long> iterator = translationIdList.iterator();

        while(iterator.hasNext())
        {
            Long id = iterator.next();

            ContentValues values = new ContentValues();

            values.put(MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_TRANSLATION_ID, id);
            values.put(MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_CATEGORY_ID, categoryId);
            database.insert(myTable, null, values);
        }


    }

    //TODO: Refactor and shove in the save methods
    Boolean checkIfExists(String tableName, String[] tableColumns, String key, String value){
        //Check if the entry exists in the table
        return database.query(tableName,
                tableColumns,
                key + " = " + value,
                null, null, null, null).moveToFirst();
    }

    public Translation[] getAllTranslations(){
        //Get all the translations to display by ID
        List<Translation> translationList = new ArrayList<Translation>();
        Cursor cursor = database.query(MySQLiteHelper.TRANSLATIONS_TABLE,
                translationColumns,
                null,
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                translationList.add(cursorToTranslation(cursor));
                cursor.moveToNext();
            }
        }

        return translationList.toArray(new Translation[translationList.size()]);
    }

    public Translation getTranslationByHomePhrase(String homePhrase)
    {
        //Get all the translations to display by ID
        Cursor cursor = database.query(MySQLiteHelper.TRANSLATIONS_TABLE,
                translationColumns,
                MySQLiteHelper.TRANSLATIONS_COLUMN_HOMEPHRASE + " = '" +  homePhrase + "'",
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            return cursorToTranslation(cursor);
        }

        return null;
    }

    public List<Translation> getTranslationsByCategory(String categoryName){
        final String categoryTable =  MySQLiteHelper.CATEGORY_TABLE;
        final String categoryIdColumn =  MySQLiteHelper.CATEGORY_COLUMN_ID;
        final String categoryNameColumn =  MySQLiteHelper.CATEGORY_COLUMN_NAME;

        final long categoryId;

        Cursor cursor = database.query(categoryTable,
                categoryColumns,
                categoryNameColumn + " = " + "'" + categoryName + "'",
                null, null, null, null);

        if(cursor.moveToFirst())
        {
            categoryId = cursor.getLong(cursor.getColumnIndex(categoryColumns[0]));
        }
        else {return null;}

        //Get all the translations to display by ID
        cursor = database.query(MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE,
                translationCategoryMapColumns,
                MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_CATEGORY_ID + " = " +  categoryId,
                null, null, null, null);

        ArrayList<Long> translationIds = new ArrayList<Long>();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                translationIds.add(cursor.getLong(cursor.getColumnIndex(translationCategoryMapColumns[1])));
                cursor.moveToNext();
            }
        } else {return null;}

        //Turn the set of ids into a comma separated string
        StringBuilder ids = new StringBuilder();
        ids.append("(");
        String prefix = "";
        for (Long id : translationIds)
        {
            ids.append(prefix);
            prefix = ", ";
            ids.append(String.valueOf(id));
        }
        ids.append(")");

        cursor = database.query(MySQLiteHelper.TRANSLATIONS_TABLE,
                translationColumns,
                MySQLiteHelper.TRANSLATIONS_COLUMN_ID + " IN " + ids,
                null, null, null, null);

        List<Translation> translationList = new ArrayList<Translation>();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                //translationList

               /* Translation newTranslation = new Translation();
                newTranslation.setId(cursor.getLong(cursor.getColumnIndex(translationColumns[0])));
                newTranslation.setHomePhrase(cursor.getString(cursor.getColumnIndex(translationColumns[1])));
                newTranslation.setHomeLanguage(cursor.getString(cursor.getColumnIndex(translationColumns[2])));

                Phrase newPhrase = getPhraseByTranslation(newTranslation);
                //newTranslation.setPhrase(newPhrase.getLanguage(), newPhrase.getContent());
                newTranslation.setPhrase(newPhrase);
                newTranslation.setCategories(getCategoriesByTranslation(newTranslation));
                translationList.add(newTranslation);*/

                translationList.add(cursorToTranslation(cursor));
                cursor.moveToNext();
            }
        }



        return translationList;
    }

    public List<Category> getCategoriesByTranslation(Translation translation)
    {
        return getCategoriesByIds(getCategoryIdsByTranslationId(translation.getId()));
    }

    private List<Category> getCategoriesByIds(List<Long> categoryIdList)
    {
        String idQuery = idListToQueryString(categoryIdList);


        Cursor cursor = database.query(MySQLiteHelper.CATEGORY_TABLE,
                categoryColumns,
                MySQLiteHelper.CATEGORY_COLUMN_ID + " IN " + idQuery,
                null, null, null, null);

        List<Category> myCategoryList = new ArrayList<Category>();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                myCategoryList.add(cursorToCategory(cursor));
                cursor.moveToNext();
            }
        }

        return myCategoryList;
    }

    private List<Long> getCategoryIdsByTranslationId(Long translationId)
    {
        Cursor cursor = database.query(MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE,
                translationCategoryMapColumns,
                MySQLiteHelper.TRANSLATIONS_TO_CATEGORY_COLUMN_TRANSLATION_ID + " = " + translationId,
                null, null, null, null);

        List<Long> categoryIdList = new ArrayList<Long>();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                categoryIdList.add(cursor.getLong(cursor.getColumnIndex(translationCategoryMapColumns[2])));
                cursor.moveToNext();
            }
        }

        return categoryIdList;
    }


    //TODO: Filter this by the whatever language the user wants to see, right now it's only grabbing the first entry in the DB
    public Phrase getPhraseByTranslation(Translation translation)
    {
        Phrase newPhrase = null;

        Cursor cursor = database.query(MySQLiteHelper.PHRASE_TABLE,
                phraseColumns,
                MySQLiteHelper.PHRASE_COLUMN_ID + " = " + translation.getId(),
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            newPhrase = new Phrase(
                    cursor.getString(cursor.getColumnIndex(phraseColumns[2])),
                    cursor.getString(cursor.getColumnIndex(phraseColumns[3]))
            );
        }

        return newPhrase;
    }

    public List<Category> getAllCategories()
    {
        final String myTable =  MySQLiteHelper.CATEGORY_TABLE;
        List<Category> categoryList = new ArrayList<Category>();

        Cursor cursor = database.query(myTable,
                categoryColumns,
                null,
                null, null, null, null);


        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                categoryList.add(cursorToCategory(cursor));
                cursor.moveToNext();
            }

        }
        return categoryList;
    }


    private String idListToQueryString(List<Long> idList){
        //Turn the set of ids into a comma separated string
        StringBuilder ids = new StringBuilder();
        ids.append("(");
        String prefix = "";
        for (Long id : idList)
        {
            ids.append(prefix);
            prefix = ", ";
            ids.append(String.valueOf(id));
        }
        ids.append(")");
        return ids.toString();
    }


    private Translation cursorToTranslation(Cursor cursor)
    {
        Translation newTranslation = new Translation();
        newTranslation.setId(cursor.getLong(cursor.getColumnIndex(translationColumns[0])));
        newTranslation.setHomePhrase(cursor.getString(cursor.getColumnIndex(translationColumns[1])));
        newTranslation.setHomeLanguage(cursor.getString(cursor.getColumnIndex(translationColumns[2])));

        newTranslation.setPhrase(getPhraseByTranslation(newTranslation));
        newTranslation.setCategories(getCategoriesByTranslation(newTranslation));

        return  newTranslation;
    }

    private Category cursorToCategory(Cursor cursor)
    {
        return new Category(
            cursor.getLong(cursor.getColumnIndex(categoryColumns[0])),
            cursor.getString(cursor.getColumnIndex(categoryColumns[1])));
    }

    void CheckDBContents(){
        Log.d("database", "Checking db contents...");
        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TRANSLATIONS_TABLE, null);
        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {

                for (int i = 0; i < translationColumns.length; i++)
                {
                    String name = cursor.getString(cursor
                            .getColumnIndex(translationColumns[i]));
                    Log.d("database", "Translation column: " + translationColumns[i] + " -- "  + name);
                }
                cursor.moveToNext();
            }
        }

        cursor = database.rawQuery("select * from " + MySQLiteHelper.PHRASE_TABLE, null);
        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {

                for (int i = 0; i < phraseColumns.length; i++)
                {
                    String name = cursor.getString(cursor
                            .getColumnIndex(phraseColumns[i]));
                    Log.d("database", "Phrase column: " + phraseColumns[i] + " -- "  + name);
                }
                cursor.moveToNext();
            }
        }

        cursor = database.rawQuery("select * from " + MySQLiteHelper.CATEGORY_TABLE, null);
        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {

                for (int i = 0; i < categoryColumns.length; i++)
                {
                    String name = cursor.getString(cursor
                            .getColumnIndex(categoryColumns[i]));
                    Log.d("database", "Category column: " + categoryColumns[i] + " -- "  + name);
                }
                cursor.moveToNext();
            }
        }

        cursor = database.rawQuery("select * from " + MySQLiteHelper.TRANSLATION_TO_CATEGORY_TABLE, null);
        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {

                for (int i = 0; i < translationCategoryMapColumns.length; i++)
                {
                    String name = cursor.getString(cursor
                            .getColumnIndex(translationCategoryMapColumns[i]));
                    Log.d("database", "Translation-Category column: " + translationCategoryMapColumns[i] + " -- "  + name);
                }
                cursor.moveToNext();
            }
        }

        Log.d("database", "Finished checking db contents...");

    }
}
