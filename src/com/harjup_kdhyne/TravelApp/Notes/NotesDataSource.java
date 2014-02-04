package com.harjup_kdhyne.TravelApp.Notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/2/14.
 * TODO: Write short summary of class
 */
public class NotesDataSource
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE,
            MySQLiteHelper.COLUMN_CONTENT,
            MySQLiteHelper.COLUMN_TIMESTAMP
    };

    public NotesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Note createNote(Note note)
    {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_TITLE, note.getTitle());
        values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, note.getTimeStampAsString());
        /*
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_CONTENT, content);
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timeStamp);
        */
        long insertID = database.insert(MySQLiteHelper.TABLE_NOTES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertID,
                null, null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void updateNote(Note note)
    {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, note.getTitle());
        values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, note.getTimeStampAsString());

        database.update(MySQLiteHelper.TABLE_NOTES,
                values,
                MySQLiteHelper.COLUMN_ID + "=" + note.getId(),
                null);

    }



    public List<Note> getAllNotes() {
        List<Note> comments = new ArrayList<Note>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            comments.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public void deleteNote(Note note){
        long id = note.getId();
        System.out.println("Note deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NOTES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);

    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setContent(cursor.getString(2));
        //set date
        return note;
    }


}
