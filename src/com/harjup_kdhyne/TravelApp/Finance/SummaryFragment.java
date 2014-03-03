package com.harjup_kdhyne.TravelApp.Finance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.harjup_kdhyne.TravelApp.R;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kyle 2.1 on 1/29/14.
 * Displays a summary of days traveled and money spent
 */
public class SummaryFragment extends Fragment
{
    public static final String TRIP_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.TripSettings.TRIP";

    private List<TripSettings> tripSettingsList;
    private TripSettings currentTrip;
    private FinanceDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
    }

    //Open a connection to the purchasesDatabase and use it to fill the tripSettingsList
    private void fillTripSettingsList()
    {
        dataSource = new FinanceDataSource(getActivity());

        try { dataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        tripSettingsList = dataSource.getAllTripSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.finance_summary, container, false);

        fillTripSettingsList();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentTrip = (TripSettings) bundle.getSerializable(TRIP_SERIALIZABLE_ID);
            Log.d("Got Bundle","Blop bloop");
        }
        else if (tripSettingsList.size() != 0)
        {
            currentTrip = tripSettingsList.get(0);
            Log.d("Got current Trip", currentTrip.getStartDateAsString());
        }
        else
        {
            currentTrip = new TripSettings();
            Log.d("Making new Trip", currentTrip.getStartDateAsString());
        }

        if (myView != null)
        {
            ProgressBar travelTimeProgressBar = (ProgressBar) myView.findViewById(R.id.travelTimeProgressBar);
            //ProgressBar budgetSpentProgressBar = (ProgressBar) myView.findViewById(R.id.budgetSpentProgressBar);

            if (currentTrip != null)
                travelTimeProgressBar.setProgress(getTripProgress(currentTrip.getStartDate(), currentTrip.getEndDate()));

            //Listens for when the user taps on the summary fragment
            myView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    //This is for debug. Gets the first entry in the Trip settings table
                    //Create a new entry if it doesn't exist
                    //TripSettings first = tripSettingsList.get(0);

                    try
                    {
                        viewTripSettings(currentTrip);
                    }
                    catch (Exception e)
                    {
                        Log.e("Exception", "Can't get trip");
                    }
                }
            });
        }
        //Inflate the view and
        //Place it on the screen
        return  myView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(TRIP_SERIALIZABLE_ID, currentTrip);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView()
    {
        try { dataSource.close();}
        catch (Exception e) { e.printStackTrace(); }

        super.onDestroyView();
    }

    public void viewTripSettings (TripSettings tripSettings)
    {
        //Replace the Summary with the trip settings edit fragment
        TripSettingsEditFragment settingsEditFragment = new TripSettingsEditFragment();

        Bundle args = new Bundle();
        args.putSerializable(TRIP_SERIALIZABLE_ID, tripSettings);
        settingsEditFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.financeSummaryContainer, settingsEditFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Returns the number of days between start and end date
     *
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getTripProgress(DateTime startDate, DateTime endDate)
    {
        int daysBetween = Days.daysBetween(startDate, endDate).getDays();
        int daysTraveled = Days.daysBetween(startDate, new DateTime()).getDays();
        int progress = (int) Math.ceil(((double)daysTraveled / (double)daysBetween) * 100);

        Log.d("Progress", String.valueOf(progress));

        return progress;
    }

}
