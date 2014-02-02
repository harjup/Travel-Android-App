package com.harjup_kdhyne.TravelApp.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Paul on 1/28/14.
 * This fragment contains a list of all the current notes for the user to peruse
 */
public class NotesListFragment extends ListFragment
{
    private ArrayList<Note> noteArrayList = new ArrayList<Note>()
    {{
      add(new Note(
              "Note 1", new Date()));
      add(new Note(
              "Note 2", new Date()));
      add(new Note(
              "Note 3", new Date()));
    }};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.notes_list_item, noteArrayList);
        setListAdapter(noteAdapter);
    }

    //Do something when you click on a note
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Hackish method of getting data into the detail view because i don't want to wrap my head
        //around the correct method right now
        Bundle myBundle = new Bundle();
        myBundle.putString("_TITLE",noteArrayList.get(position).getTitle());
        myBundle.putString("_CONTENT",noteArrayList.get(position).getContent());
        myBundle.putString("_DATE", noteArrayList.get(position).getTimeStampAsString());
 
        
        NoteDetailsFragment myNoteDetails = new NoteDetailsFragment();
        //myNoteDetails.setArguments(myBundle);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, myNoteDetails);
        ft.addToBackStack(null);
        ft.commit();

    }


}
