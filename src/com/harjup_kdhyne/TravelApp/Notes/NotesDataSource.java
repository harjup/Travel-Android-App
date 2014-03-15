package com.harjup_kdhyne.TravelApp.Notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.harjup_kdhyne.TravelApp.MySQLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/2/14.
 * Manages calls to the SQLite database for saved Notes
 */
public class NotesDataSource
{
    private static NotesDataSource instance = null;
    public static NotesDataSource getInstance(Context context){
        if (instance == null){
            instance = new NotesDataSource(context.getApplicationContext());
        }
        return instance;
    }

    protected NotesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.NOTES_COLUMN_ID,
            MySQLiteHelper.NOTES_COLUMN_TITLE,
            MySQLiteHelper.NOTES_COLUMN_CONTENT,
            MySQLiteHelper.NOTES_COLUMN_TIMESTAMP
    };

    /*public NotesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }*/

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

        values.put(MySQLiteHelper.NOTES_COLUMN_TITLE, note.getTitle());
        values.put(MySQLiteHelper.NOTES_COLUMN_CONTENT, note.getContent());
        values.put(MySQLiteHelper.NOTES_COLUMN_TIMESTAMP, note.getTimeStampAsString());

        long insertID = database.insert(MySQLiteHelper.NOTES_TABLE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.NOTES_TABLE,
                allColumns,
                MySQLiteHelper.NOTES_COLUMN_ID + " = " + insertID,
                null, null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void updateNote(Note note)
    {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.NOTES_COLUMN_TITLE, note.getTitle());
        values.put(MySQLiteHelper.NOTES_COLUMN_CONTENT, note.getContent());
        values.put(MySQLiteHelper.NOTES_COLUMN_TIMESTAMP, note.getTimeStampAsString());

        database.update(MySQLiteHelper.NOTES_TABLE,
                values,
                MySQLiteHelper.NOTES_COLUMN_ID + "=" + note.getId(),
                null);

    }



    public List<Note> getAllNotes() {
        List<Note> comments = new ArrayList<Note>();

        Cursor cursor = database.query(MySQLiteHelper.NOTES_TABLE,
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
        database.delete(MySQLiteHelper.NOTES_TABLE, MySQLiteHelper.NOTES_COLUMN_ID
                + " = " + id, null);

    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setContent(cursor.getString(2));
        note.setTimeStampFromString(cursor.getString(3));
        //TODO: Set image url
        return note;
    }


}
