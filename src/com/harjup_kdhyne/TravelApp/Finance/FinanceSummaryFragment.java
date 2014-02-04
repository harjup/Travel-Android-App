package com.harjup_kdhyne.TravelApp.Finance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Date;

/**
 * Created by Kyle 2.1 on 1/29/14.
 */
public class FinanceSummaryFragment extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //TODO: Make this work without crashing
        ListFragment purchaseListFragment = new FinancePurchaseListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(getId(), purchaseListFragment);
        transaction.commit();

        //Inflate the view and
        //Place it on the screen
        return  inflater.inflate(R.layout.finance_summary, container, false);
    }

    /**
     *  Returns a string that describes the number of days
     *  between dateOne and dateTwo.
     */
    //TODO: move this somewhere more appropriate?
    public String getDateDiffString(Date dateOne, Date dateTwo)
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
    }

}
