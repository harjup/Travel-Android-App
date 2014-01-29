package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
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


    private NoteAdapter noteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteAdapter = new NoteAdapter(getActivity(), R.layout.notes_list_item, noteArrayList);
        setListAdapter(noteAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }


}
