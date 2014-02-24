package com.harjup_kdhyne.TravelApp.Finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kyle 2.1 on 2/16/14.
 * Controls the trip settings fragment, where a user can change the travel dates and maximum budget
 */
public class TripSettingsEditFragment extends Fragment
{
    private PurchasesDataSource dataSource;
    private TripSettings currentTrip;

    //Keys for start/end date
    public static final String START_DATE = "com.harjup_kdhyne.TravelApp.Finance.start_date";
    public static final String END_DATE = "com.harjup_kdhyne.TravelApp.Finance.end_date";

    //Used to track when we are performing an action on start/end date
    public static final int REQUEST_START_DATE = 0;
    public static final int REQUEST_END_DATE = 0;

    private EditText startDateEditText;
    private EditText endDateEditText;

    private TextView currentExchangeTextView;

    private Button backButton;
    private Button saveButton;
    private Button updateExchangeButton;

    //TODO: add reference to singleton TripSettings
    //TODO: add reference to DB

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //TODO: Persist Purchase object between oncreate and on destroy when rotating device
    public void setCurrentTrip(TripSettings tripSettings)
    {
        currentTrip = tripSettings;
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

            currentExchangeTextView = (TextView)myView.findViewById(R.id.currentExchangeTextView);

            //Retrieve buttons
            backButton = (Button)myView.findViewById(R.id.backButton);
            saveButton = (Button)myView.findViewById(R.id.saveButton);
            updateExchangeButton = (Button)myView.findViewById(R.id.updateExchangeButton);


            //Fill editTexts if they aren't null
            if (startDateEditText != null)
                startDateEditText.setText(currentTrip.getStartDateAsString());

            if (endDateEditText != null)
                endDateEditText.setText(currentTrip.getEndDateAsString());

            if (currentExchangeTextView != null)
            {
                //TODO: Call the openExchange API to get exchange rate
                //TODO: Need to determine when is most appropriate
                String currentExchange = "1 USD = 5 DHY";

                currentExchangeTextView.setText(currentExchange);
            }

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

                if (currentTrip != null)
                {
                    Log.d("Trip is here", String.valueOf(currentTrip.getTripID()));
                }
                //Check to see if the ID hasn't been set yet (isn't in the list)
                //Else update the changes
                if (currentTrip.getTripID() == -1)
                {
                    dataSource.createTripSettings(currentTrip);
                }
                else
                {
                    dataSource.updateTripSettings(currentTrip);
                }

                //Return after saving
                returnToSummary();
            }
        });

        updateExchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Call the exchange API to get the latest exchange rate between two currencies

            }
        });

        //Setup OnClickListener for the date textBox and prompt a date picker
        startDateEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragManager = getFragmentManager();
                DatePickerDialogFragment dateDialog = DatePickerDialogFragment.newInstance(currentTrip.getStartDate());
                dateDialog.setTargetFragment(TripSettingsEditFragment.this, REQUEST_START_DATE);
                dateDialog.show(fragManager, START_DATE);
            }
        });

        //Setup OnClickListener for the date textBox and prompt a date picker
        endDateEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragManager = getFragmentManager();
                DatePickerDialogFragment dateDialog = DatePickerDialogFragment.newInstance(currentTrip.getEndDate());
                dateDialog.setTargetFragment(TripSettingsEditFragment.this, REQUEST_END_DATE);
                dateDialog.show(fragManager, END_DATE);
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
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Need this for the date picker dialog. Called when the OK button is clicked
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // If this wasn't called by the date dialog exit
        if(resultCode != Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_START_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerDialogFragment.DATE);
            currentTrip.setStartDate(date);
            startDateEditText.setText(currentTrip.getStartDateAsString());
        }
        else if(requestCode == REQUEST_END_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerDialogFragment.DATE);
            currentTrip.setEndDate(date);
            endDateEditText.setText(currentTrip.getEndDateAsString());
        }
    }
}
