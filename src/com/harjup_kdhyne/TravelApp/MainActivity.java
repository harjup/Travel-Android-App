package com.harjup_kdhyne.TravelApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.harjup_kdhyne.TravelApp.Finance.FinanceSummary;
import com.harjup_kdhyne.TravelApp.Notes.NoteDetailsFragment;
import com.harjup_kdhyne.TravelApp.Notes.NotesListFragment;

public class MainActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the activity content from a layout resource
        setContentView(R.layout.notes_activity_container);

        //Create a fragment manager to find and create fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Check if any fragment exist in target parent node notesActivityContainer
        Fragment myNotesFragment = fragmentManager.findFragmentById(R.id.notesActivityContainer);
        if (myNotesFragment == null)
        {
            //If not, then create a new instance of the fragment's activity and bind it to the
            //specified parent node

            myNotesFragment = new NotesListFragment(); // new FinanceSummary();

            fragmentManager.beginTransaction()
                    .add(R.id.notesActivityContainer, myNotesFragment)
                    .commit();

        }
    }
}
