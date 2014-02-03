package com.harjup_kdhyne.TravelApp.Finance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Inflate the view
        View theView = inflater.inflate(R.layout.finance_summary, container, false);

        //Place it on the screen
        return theView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        //TODO: Create a reference to FinanceAllPurchases to get the count of the purchaseArrayList.
        //TODO:Use this to loop through and add a ListFragment for each list item
        //Fragment purchaseListFragment = new FinancePurchaseListFragment();
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.purchasesScrollView, purchaseListFragment, "purchaseList").commit();
    }

    /**
     *  Returns a string that describes the number of days
     *  between dateOne and dateTwo.
     */

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
