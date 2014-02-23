package com.harjup_kdhyne.TravelApp.Finance;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle 2.1 on 2/2/14.
 * A list to show all of the purchases created and saved in the database
 * Clicking on an item in this list will show details of the transaction
 * Clicking "Add Purchase" will show a new editPurchaseFragment
 * Clicking "Delete" will cause the next tapped item to be deleted from the list and the database
 * Clicking "Delete" again will exit delete mode
 */
public class PurchaseListFragment extends ListFragment
{
    // Stores the list of Contacts
    private PurchasesDataSource purchasesDataSource;
    private List<Purchase> purchasesList;
    private PurchaseAdapter purchaseAdapter;

    //Buttons
    private Button addPurchaseButton;
    private Button deletePurchaseButton;

    //Operations
    private boolean deleteMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //Open a connection to the purchasesDatabase and use it to fill the purchasesList,
    //then insert the purchasesList into the purchaseAdapter so the view can use it
    private void fillPurchasesList()
    {
        purchasesDataSource = new PurchasesDataSource(getActivity());

        try { purchasesDataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        purchasesList = purchasesDataSource.getAllPurchases();

        purchaseAdapter = new PurchaseAdapter(getActivity(), R.layout.finance_purchase_item, (ArrayList<Purchase>) purchasesList);
        setListAdapter(purchaseAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getActivity().setTitle(R.string.finance_summary_title);

        fillPurchasesList();

        View myView = inflater.inflate(R.layout.finance_purchase_list,container,false);

        if(myView != null)
        {
            addPurchaseButton = (Button) myView.findViewById(R.id.addPurchaseButton);
            deletePurchaseButton = (Button) myView.findViewById(R.id.deletePurchaseButton);
        }

        //ClickListeners
        addPurchaseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Go to the purchase details page and create a new purchase
                viewPurchaseDetails(new Purchase());
            }
        });

        deletePurchaseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Fix the toggling colors of the delete button
                if(deleteMode)
                {
                    deleteMode = false;
                    deletePurchaseButton.setBackgroundColor(Color.parseColor("#0A0A0A"));
                }
                else
                {
                    deleteMode = true;
                    deletePurchaseButton.setBackgroundColor(Color.parseColor("#0F0F0F"));
                }
            }
        });

        return myView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        //Delete object if deleteMode is enabled, otherwise show purchase details
        if(deleteMode)
        {
            purchasesDataSource.deletePurchase(purchasesList.get(position));
            purchaseAdapter.remove(purchasesList.get(position));
        }
        else
        {
            viewPurchaseDetails(purchasesList.get(position));
        }
    }

    //When the user navigates to a different fragment,
    //close the purchasesList database connection
    @Override
    public void onDestroyView()
    {
        try
        {
            purchasesDataSource.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        super.onDestroyView();
    }

    public void viewPurchaseDetails (Purchase currentPurchase)
    {
        PurchaseEditFragment editPurchaseFragment = new PurchaseEditFragment();
        Fragment summaryFragment = getFragmentManager().findFragmentById(R.id.financeSummaryContainer);

        editPurchaseFragment.setCurrentPurchase(currentPurchase);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.remove(summaryFragment);
        transaction.add(R.id.financeActivityContainer, editPurchaseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
