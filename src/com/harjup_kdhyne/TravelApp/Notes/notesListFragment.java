package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Paul on 1/28/14.
 * This fragment contains a list of all the current notes for the user to peruse
 */
public class NotesListFragment extends ListFragment
{
   private ArrayList<Note> noteArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }


}
