package com.harjup_kdhyne.TravelApp.Finance;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //getActivity().setTitle("");
        purchasesList = FinanceAllPurchases.get(getActivity()).getPurchasesList();

        FinancePurchaseAdapter purchaseAdapter = new FinancePurchaseAdapter(getActivity(), R.layout.finance_purchase_item, purchasesList);

        setListAdapter(purchaseAdapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        FinancePurchase clickedPurchase = ((FinancePurchaseAdapter) getListAdapter()).getItem(position);

        Intent newIntent = new Intent(getActivity(), FinancePurchase.class);

        newIntent.putExtra(FinanceEditPurchaseFragment.PURCHASE_ID, clickedPurchase.getPurchaseID());

        startActivity(newIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
