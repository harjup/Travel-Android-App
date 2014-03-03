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
        fillTripSettingsList();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentTrip = (TripSettings) bundle.getSerializable(TRIP_SERIALIZABLE_ID);
            Log.d("Got Bundle","Bleep bloop");
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

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.finance_summary, container, false);

        //Access the purchaseListFragment in order to calculate total expenses
        PurchaseListFragment purchaseListFragment = (PurchaseListFragment) getFragmentManager().findFragmentById(R.id.financePurchaseListContainer);

        if (myView != null)
        {
            ProgressBar travelTimeProgressBar = (ProgressBar) myView.findViewById(R.id.travelTimeProgressBar);
            ProgressBar budgetSpentProgressBar = (ProgressBar) myView.findViewById(R.id.budgetSpentProgressBar);

            if (currentTrip != null)
                travelTimeProgressBar.setProgress(getTripProgress(currentTrip.getStartDate(), currentTrip.getEndDate()));

            if (purchaseListFragment != null)
            {
                double expenses = purchaseListFragment.calculateTotalExpenses();

                Log.d("Expenses calculated", String.valueOf(expenses));

                budgetSpentProgressBar.setProgress(getBudgetProgress(expenses));
            }
            else
            {
                Log.d("No purchaseListFragment", "blah");
            }

            //Listens for when the user taps on the summary fragment
            myView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //This is for debug. Gets the first entry in the Trip settings table
                    //Create a new entry if it doesn't exist
                    //TripSettings first = tripSettingsList.get(0);

                    try {viewTripSettings(currentTrip);}
                    catch (Exception e) {Log.e("Exception", "Can't get trip");}
                }
            });
        }
        //Inflate the view and place it on the screen
        return  myView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        //Bundle for LCM
        outState.putSerializable(TRIP_SERIALIZABLE_ID, currentTrip);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView()
    {
        try { dataSource.close(); }
        catch (Exception e) { e.printStackTrace(); }
        super.onDestroyView();
    }

    //Open a connection to the purchasesDatabase and use it to fill the tripSettingsList
    private void fillTripSettingsList()
    {
        dataSource = new FinanceDataSource(getActivity());

        try {dataSource.open();}
        catch (SQLException e) {e.printStackTrace();}

        tripSettingsList = dataSource.getAllTripSettings();
    }

    public void viewTripSettings (TripSettings tripSettings)
    {
        //Replace the Summary with the trip settings edit fragment
        TripSettingsEditFragment settingsEditFragment = new TripSettingsEditFragment();

        //Bundle the trip
        Bundle args = new Bundle();
        args.putSerializable(TRIP_SERIALIZABLE_ID, tripSettings);
        settingsEditFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.financeSummaryContainer, settingsEditFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Returns the progression between start and end date as an int between 0 and 100
     * @param startDate date the trip started
     * @param endDate date the trip will end
     * @return progress as an int between 0 and 100
     */
    public int getTripProgress(DateTime startDate, DateTime endDate)
    {
        int daysBetween = Days.daysBetween(startDate, endDate).getDays();
        int daysTraveled = Days.daysBetween(startDate, new DateTime()).getDays();
        return (int) Math.ceil(((double) daysTraveled / (double) daysBetween) * 100);
    }

    public  int getBudgetProgress(double sumTotal)
    {
        return (int) Math.ceil((sumTotal / currentTrip.getTotalBudget()) * 100);
    }

}
