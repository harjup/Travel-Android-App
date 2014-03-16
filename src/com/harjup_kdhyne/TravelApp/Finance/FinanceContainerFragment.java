package com.harjup_kdhyne.TravelApp.Finance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

import java.util.List;

/**
 * Created by Kyle 2.1 on 2/9/14.
 * A container fragment for all of the financial information and functionality
 */
public class FinanceContainerFragment extends Fragment
{
    public static final String TRIP_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.TripSettings.TRIP";

    private List<TripSettings> tripSettingsList;
    private TripSettings currentTrip;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        fillTripSettingsList();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentTrip = (TripSettings) bundle.getSerializable(TRIP_SERIALIZABLE_ID);
            Log.d("Got Bundle", "Bleep bloop");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.finance_activity_container, container, false);

        FragmentManager fm = getFragmentManager();

        Fragment financeSummaryFragment = fm.findFragmentById(R.id.financeSummaryContainer);
        ListFragment financePurchaseListFragment = (ListFragment) fm.findFragmentById(R.id.financePurchaseListContainer);

        if (financeSummaryFragment == null && financePurchaseListFragment == null)
        {
            financeSummaryFragment = new SummaryFragment();
            financePurchaseListFragment = new PurchaseListFragment();

            //Bundle the trip for both fragments
            Bundle args = new Bundle();
            args.putSerializable(TRIP_SERIALIZABLE_ID, currentTrip);
            financeSummaryFragment.setArguments(args);
            financePurchaseListFragment.setArguments(args);

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.financePurchaseListContainer,financePurchaseListFragment);
            transaction.replace(R.id.financeSummaryContainer,financeSummaryFragment);

            transaction.commit();
        }
        return myView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        //Bundle for LCM
        outState.putSerializable(TRIP_SERIALIZABLE_ID, currentTrip);
        super.onSaveInstanceState(outState);
    }

    //Open a connection to the purchasesDatabase and use it to fill the tripSettingsList
    private void fillTripSettingsList()
    {
        FinanceDataSource financeDataSource = FinanceDataSource.openDbConnection(getActivity());

        tripSettingsList = financeDataSource.getAllTripSettings();
    }
}
