package com.harjup_kdhyne.TravelApp.Finance;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static android.view.View.OnClickListener;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class PurchaseEditFragment extends Fragment
{
    private PurchasesDataSource purchasesDataSource;
    private Purchase currentPurchase;

    //Keys for ID and date
    public static final String PURCHASE_ID = "com.harjup_kdhyne.TravelApp.Finance.purchase_id";
    public static final String PURCHASE_DATE = "com.harjup_kdhyne.TravelApp.Finance.purchase_date";

    //Used to track when we are performing an action on purchase date
    public static final int REQUEST_PURCHASE_DATE = 0;

    private EditText purchaseNameEditText;
    private EditText purchaseDateEditText;
    private EditText purchasePriceEditText;
    private EditText purchaseNotesEditText;

    private Button backButton;
    private Button clearButton;
    private Button deleteButton;
    private Button saveButton;
    private Button cameraButton;    //TODO: These buttons are for working with the camera and existing pictures on the device
    private Button albumButton;    //TODO: come back to these when we know how to deal with them

    private Spinner purchaseCurrencySpinner;

    //Create a new EditPurchase fragment with the passed data
    public static PurchaseEditFragment newEditPurchaseFragment(UUID purchaseID)
    {
        //Create a bundle to pass info to new activities
        Bundle passedData = new Bundle();

        //Put the purchaseID in the bundle and return the purchaseFragment
        passedData.putSerializable(PURCHASE_ID, purchaseID);
        PurchaseEditFragment editPurchaseFragment = new PurchaseEditFragment();
        editPurchaseFragment.setArguments(passedData);
        return editPurchaseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void setCurrentPurchase(Purchase purchase){
        currentPurchase = purchase;
    }

    void openDbConnection()
    {
        purchasesDataSource = new PurchasesDataSource(getActivity());

        try
        {
            purchasesDataSource.open();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Show the fragment on the screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //TODO: Instantiate the date picker dialog somewhere
        View myView = inflater.inflate(R.layout.finance_purchase_edit, container, false);

        //Open a connection to the database where purchase info is stored
        openDbConnection();

        //If the view isn't null, grab all of the textBox, button, and spinner objects
        if(myView != null)
        {
            //TextBoxes
            purchaseNameEditText = (EditText) myView.findViewById(R.id.purchaseNameEditText);
            purchaseDateEditText = (EditText) myView.findViewById(R.id.purchaseDateEditText);
            purchasePriceEditText = (EditText) myView.findViewById(R.id.purchasePriceEditText);
            purchaseNotesEditText = (EditText) myView.findViewById(R.id.purchaseNotesEditText);

            //Fill TextBoxes
            if(purchaseNameEditText != null)
                purchaseNameEditText.setText(currentPurchase.getPurchaseName());

            //TODO this line could probably be better
            if(purchaseDateEditText != null)
                purchaseDateEditText.setText(currentPurchase.getDateString());

            if(purchasePriceEditText != null)
                purchasePriceEditText.setText(currentPurchase.getPurchasePrice().toString());

            if(purchaseNotesEditText != null)
                purchaseNotesEditText.setText(currentPurchase.getPurchaseNotes());

            //Buttons
            backButton = (Button) myView.findViewById(R.id.backButton);
            clearButton = (Button) myView.findViewById(R.id.clearButton);
            deleteButton = (Button) myView.findViewById(R.id.deleteButton);
            cameraButton = (Button) myView.findViewById(R.id.cameraButton);
            albumButton = (Button) myView.findViewById(R.id.albumButton);
            saveButton = (Button) myView.findViewById(R.id.saveButton);

            //Spinner for currency used to purchase the item
            purchaseCurrencySpinner = (Spinner) myView.findViewById(R.id.purchaseCurrencySpinner);
        }

        //TextWatcher used by all of the textBoxes
        TextWatcher editTextWatcher = new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
                Log.d("TEXT WAS CHANGED", arg0.toString());

                if (purchaseNameEditText.hasFocus())
                {
                    currentPurchase.setPurchaseName(arg0.toString());
                }
                else if (purchasePriceEditText.hasFocus())
                {
                    currentPurchase.setPurchasePrice(arg0.toString());
                }
                else if (purchaseNotesEditText.hasFocus())
                {
                    currentPurchase.setPurchaseNotes(arg0.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
            }
        };

        //Add the textWatcher to all the textBoxes
        purchaseNameEditText.addTextChangedListener(editTextWatcher);
        purchasePriceEditText.addTextChangedListener(editTextWatcher);
        purchaseNotesEditText.addTextChangedListener(editTextWatcher);

        //Setup OnClickListener for the date textBox and prompt a date picker
        purchaseDateEditText.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragManager = getFragmentManager();

                DatePickerDialogFragment dateDialog = DatePickerDialogFragment.newInstance(currentPurchase.getPurchaseDate());

                dateDialog.setTargetFragment(PurchaseEditFragment.this, REQUEST_PURCHASE_DATE);

                dateDialog.show(fragManager, PURCHASE_DATE);

            }
        });


        //TODO: look for ways to simplify these listeners like the textBox listeners above

        backButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                returnToPurchasesList();
            }
        });

        clearButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: clear the purchase info from all textBoxes and the spinner (and stay on the page)
            }
        });

        deleteButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Delete the purchase info from the db

                //return to list view
                returnToPurchasesList();
            }
        });

        cameraButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: make this open the device's camera if it has one
                //if there's no camera, show an error dialog
            }
        });

        albumButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: have this access the device's photo album
                //If that doesn't exist either, then what the hell are you using?? (show an error dialog)
            }
        });

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save the purchase details

                //Check to see if the ID hasn't been set yet (isn't in the list)
                //Else update the changes
                if (currentPurchase.getPurchaseID() == -1)
                {
                    purchasesDataSource.createPurchase(currentPurchase);
                }
                else
                {
                    purchasesDataSource.updatePurchase(currentPurchase);
                }

                //return to list view
                returnToPurchasesList();
            }
        });

        //TODO: Add a listener to the spinner


        //Inflate the view
        return myView;
    }

    //Close the db connection whenever you navigate off of the this page
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

    //Return to the purchases list
    public void returnToPurchasesList()
    {
        SummaryFragment summaryFragment = new SummaryFragment();
        PurchaseListFragment purchaseListFragment = new PurchaseListFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.add(R.id.financeSummaryContainer, summaryFragment);
        fragmentTransaction.add(R.id.financePurchaseListContainer, purchaseListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    //Need this for the date picker dialog. Called when the OK button is clicked
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // If this wasn't called by the date dialog exit

        if(resultCode != Activity.RESULT_OK) return;

        // If it was get the date and save it to the Contact and
        // update the date EditText box

        if(requestCode == REQUEST_PURCHASE_DATE){

            Date date = (Date) data
                    .getSerializableExtra(DatePickerDialogFragment.DATE);

            currentPurchase.setPurchaseDate(date);

            purchaseDateEditText.setText(currentPurchase.getDateString());

        }
    }
}
