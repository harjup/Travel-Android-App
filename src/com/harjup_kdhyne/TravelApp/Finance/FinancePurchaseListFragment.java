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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinancePurchaseListFragment extends ListFragment
{
    // Stores the list of Contacts
    private FinancePurchasesDataSource purchasesDataSource;
    private List<FinancePurchase> purchasesList;
    private FinancePurchaseAdapter purchaseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //Open a connection to the purchasesDatabase and use it to fill the purchasesList,
    //then insert the purchasesList into the purchaseAdapter so the view can use it
    private void fillPurchasesList()
    {
        purchasesDataSource = new FinancePurchasesDataSource(getActivity());

        try { purchasesDataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        purchasesList = purchasesDataSource.getAllPurchases();

        purchaseAdapter = new FinancePurchaseAdapter(getActivity(), R.layout.finance_purchase_item, (ArrayList<FinancePurchase>) purchasesList);
        setListAdapter(purchaseAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fillPurchasesList();

        View myView = inflater.inflate(R.layout.finance_list_layout,container,false);

        getActivity().setTitle(R.string.finance_summary_title);

        return myView;
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

        viewPurchaseDetails(purchasesList.get(position));

    }

    //When the user navigates to a different fragment,
    //close the purchasesList database connection
    @Override
    public void onDestroyView() {

        try { purchasesDataSource.close();}
        catch (Exception e) { e.printStackTrace(); }

        super.onDestroyView();
    }

    public void viewPurchaseDetails (FinancePurchase currentPurchase)
    {
        FinanceEditPurchaseFragment editPurchaseFragment = new FinanceEditPurchaseFragment();

        editPurchaseFragment.setCurrentPurchase(currentPurchase);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.financeActivityContainer, editPurchaseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
