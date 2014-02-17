package com.harjup_kdhyne.TravelApp.Finance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Kyle 2.1 on 2/16/14.
 * Controls the trip settings fragment, where a user can change the travel dates and maximum budget
 */
public class TripSettingsEditFragment extends Fragment
{
    private EditText startDateEditText;
    private EditText endDateEditText;

    private Button backButton;
    private Button saveButton;

    //TODO: add reference to singleton TripSettings
    //TODO: add reference to DB

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.finance_trip_settings_edit, container, false);

        if (myView != null)
        {
            //Retrieve EditTexts
            startDateEditText = (EditText)myView.findViewById(R.id.startDateEditText);
            endDateEditText = (EditText)myView.findViewById(R.id.endDateEditText);

            //TODO: Fill date fields with correct info

            //Retrieve buttons
            backButton = (Button)myView.findViewById(R.id.backButton);
            saveButton = (Button)myView.findViewById(R.id.backButton);
        }

        //TODO: Set up listeners for Date Pickers

        //TODO: Set up Listeners for Buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return after saving
                returnToSummary();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save settings to a table in the DB

                //Return after saving
                returnToSummary();
            }
        });


        return myView;
    }

    //Return to the trip summary
    public void returnToSummary()
    {
        SummaryFragment summaryFragment = new SummaryFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.financeSummaryContainer, summaryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
