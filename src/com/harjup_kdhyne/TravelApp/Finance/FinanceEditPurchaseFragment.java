package com.harjup_kdhyne.TravelApp.Finance;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import com.harjup_kdhyne.TravelApp.R;

import java.util.UUID;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinanceEditPurchaseFragment extends Fragment
{
    //Keys for ID and date
    public static final String PURCHASE_ID = "com.harjup_kdhyne.TravelApp.Finance.purchase_id";
    public static final String PURCHASE_DATE = "com.harjup_kdhyne.TravelApp.Finance.purchase_date";

    //Used to track when we are performing an action on purchase date
    public static final int REQUEST_PURCHASE_DATE = 0;



    private FinancePurchase purchase;

    private EditText purchaseNameEditText;
    private EditText purchaseDateEditText;
    private EditText purchasePriceEditText;

    private Spinner purchaseCurrencySpinner;

    private EditText purchaseNotesEditText;

    //Create a new EditPurchase fragment with the passed data
    public static FinanceEditPurchaseFragment newEditPurchaseFragment(UUID purchaseID)
    {
        //Create a bundle to pass info to new activities
        Bundle passedData = new Bundle();

        //Put the purchaseID in the bundle and return the purchaseFragment
        passedData.putSerializable(PURCHASE_ID, purchaseID);

        FinanceEditPurchaseFragment editPurchaseFragment = new FinanceEditPurchaseFragment();

        editPurchaseFragment.setArguments(passedData);

        return editPurchaseFragment;
    }


    //Show the fragment on the screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View theView = inflater.inflate(R.layout.finance_edit_purchase, container, false);


        //TODO: Instantiate all of the EditText boxes and textWatchers. Also the date picker dialog stuff


        return theView;
    }


    //Need this for the date picker dialog. Called when the OK button is clicked
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);



    }
}
