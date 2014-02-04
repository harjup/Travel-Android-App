package com.harjup_kdhyne.TravelApp.Finance;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinancePurchaseListFragment extends ListFragment
{
    // Stores the list of Contacts

    private ArrayList<FinancePurchase> purchasesList;

    public FinancePurchaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.finance_summary_title);

        //getActivity().setTitle("");
        purchasesList = FinanceAllPurchases.get(getActivity()).getPurchasesList();

        FinancePurchaseAdapter purchaseAdapter = new FinancePurchaseAdapter(getActivity(), R.layout.finance_purchase_item, purchasesList);

        setListAdapter(purchaseAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Log.e("Clicked", "Clicked on something");

//        AlertDialog alert = new AlertDialog.Builder(this.getActivity().getApplicationContext()).create();
//        String message = "row clicked!";
//        alert.setMessage(message);
//        alert.show();

        FinanceEditPurchaseFragment editPurchaseFragment = new FinanceEditPurchaseFragment();
        editPurchaseFragment.setCurrentPurchase(purchasesList.get(position));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //make the back button return to the finance summary screen
        //pass the tag to the backStack
        transaction.addToBackStack("Finance Summary");
        //add this
        transaction.replace(R.id.financeActivityContainer, editPurchaseFragment);
        transaction.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        ((FinancePurchaseAdapter) getListAdapter()).notifyDataSetChanged();
    }

}
