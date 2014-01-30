package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 1/28/14.
 * Displays a list of notes for the user to examine(?)
 * The user can also create new notes(?)
 *
 * Display the contents of a note along with title and timestamp.
 */
public class NoteDetailsFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Instantiate the view
        View myView = inflater.inflate(R.layout.note_details, container, false);

        //Init stuff

        return myView;
    }
}
