package com.harjup_kdhyne.TravelApp.Finance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Kyle 2.1 on 2/9/14.
 * A container fragment for all of the financial information and functionality
 */
public class FinanceContainerFragment extends Fragment
{
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

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.financePurchaseListContainer,financePurchaseListFragment);
            transaction.replace(R.id.financeSummaryContainer,financeSummaryFragment);

            transaction.commit();
        }
        return myView;
    }
}
