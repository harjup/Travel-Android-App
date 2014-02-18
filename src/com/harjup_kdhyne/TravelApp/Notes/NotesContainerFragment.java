package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/8/14.
 * Exists as a container for all note-related fragments in the Notes tab.
 * Initializes the starting fragment
 */
public class NotesContainerFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.note_activity_container, container, false);


        FragmentManager fm = getFragmentManager();

        Fragment notesActivityFragment = fm.findFragmentById(R.id.notesActivityContainer);

        if (notesActivityFragment == null)
        {
            ListFragment notesListFragment = new NotesListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.notesActivityContainer, notesListFragment);
            transaction.commit();
        }


        return myView;
    }
}
