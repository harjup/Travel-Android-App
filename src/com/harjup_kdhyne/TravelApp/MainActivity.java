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
        setContentView(R.layout.finance_activity_container);

        //Create a fragment manager to manage fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        //For some reason I'm just grabbing the parent node of the activity page??
        Fragment myFragment = fragmentManager.findFragmentById(R.id.notesActivityContainer);
        if (myFragment == null)
        {
            //reassign a new fragment activity to fill the parent node
            //myFragment = new NotesListFragment();
            myFragment = new FinanceSummary();

            //Add the fragment activity to the parent node(?) and commit changes
            fragmentManager.beginTransaction()
                    .add(R.id.financeActivityContainer, myFragment)
                    .commit();

        }
    }
}
