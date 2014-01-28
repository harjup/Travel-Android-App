package com.harjup_kdhyne.TravelApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity_container);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //For some reason I'm just grabbing the parent node of the activity page??
        Fragment myFragment = fragmentManager.findFragmentById(R.id.notesActivityContainer);

        if (myFragment == null)
        {
            myFragment = new NotesListFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.notesActivityContainer, myFragment)
                    .commit();

        }
    }
}
