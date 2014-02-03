package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paul on 1/28/14.
 * This fragment contains a list of all the current notes for the user to peruse
 */
public class NotesListFragment extends ListFragment
{
    private NotesDataSource dataSource;
    private List<Note> noteList;
/*
    private ArrayList<Note> noteArrayList = new ArrayList<Note>()
    {{
      add(new Note(
              "Note 1", new Date()));
      add(new Note(
              "Note 2", new Date()));
      add(new Note(
              "Note 3", new Date()));
    }};*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new NotesDataSource(getActivity());

        try { dataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        /* If there are currently no notes in the database, uncomment this and the arraylist to add some
        for(Note note : noteArrayList)
        {
            dataSource.createNote(note.getTitle(), note.getContent(), note.getTimeStampAsString());
        }*/

        noteList = dataSource.getAllNotes();

        NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.notes_list_item, (ArrayList<Note>) noteList);
        setListAdapter(noteAdapter);
    }

    //Do something when you click on a note
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Hackish method of getting data into the detail view because i don't want to wrap my head
        //around the correct method right now
        //Bundle myBundle = new Bundle();
        //myBundle.putString("harjup_kdhyne_TITLE",noteArrayList.get(position).getTitle());
        //myBundle.putString("_CONTENT",noteArrayList.get(position).getContent());
        //myBundle.putString("_DATE", noteArrayList.get(position).getTimeStampAsString());
 
        
        NoteDetailsFragment myNoteDetails = new NoteDetailsFragment();
        //myNoteDetails.setArguments(myBundle);
        //myNoteDetails.setNote(noteArrayList.get(position));

        myNoteDetails.setNote(noteList.get(position));


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, myNoteDetails);
        ft.addToBackStack(null);
        ft.commit();

    }


}
