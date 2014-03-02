package com.harjup_kdhyne.TravelApp.Finance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kyle 2.1 on 1/29/14.
 * Displays a summary of days traveled and money spent
 */
public class SummaryFragment extends Fragment
{
    private List<TripSettings> tripSettingsList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //Open a connection to the purchasesDatabase and use it to fill the tripSettingsList
    private void fillTripSettingsList()
    {
        FinanceDataSource dataSource = new FinanceDataSource(getActivity());

        try { dataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        tripSettingsList = dataSource.getAllTripSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.finance_summary, container, false);

        fillTripSettingsList();

        if (myView != null)
        {
            //Listens for when the user taps on the summary fragment
            myView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    //This is for debug. Gets the first entry in the Trip settings table
                    //Create a new entry if it doesn't exist
                    //TripSettings first = tripSettingsList.get(0);

                    if (tripSettingsList.size() != 0)
                    {
                        viewTripSettings(tripSettingsList.get(0));
                    }
                    else
                    {
                        viewTripSettings(new TripSettings());
                    }
                }
            });
        }
        //Inflate the view and
        //Place it on the screen
        return  myView;
    }


    public void viewTripSettings (TripSettings tripSettings)
    {
        //Replace the Summary with the trip settings edit fragment
        TripSettingsEditFragment settingsFragment = new TripSettingsEditFragment();

        settingsFragment.setCurrentTrip(tripSettings);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.financeSummaryContainer, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     *  Returns a string that describes the number of days
     *  between dateOne and dateTwo.
     */
    //TODO: Decide where this should live
    /*public String getDateDiffString(Date dateOne, Date dateTwo)
    {
        long timeOne = dateOne.getTime();
        long timeTwo = dateTwo.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;

        if (delta > 0) {
            return "dateTwo is " + delta + " days after dateOne";
        }
        else {
            delta *= -1;
            return "dateTwo is " + delta + " days before dateOne";
        }
    }*/

}
