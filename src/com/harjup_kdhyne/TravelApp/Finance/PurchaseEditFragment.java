package com.harjup_kdhyne.TravelApp.Finance;

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
import android.widget.*;
import com.harjup_kdhyne.TravelApp.R;
import org.openexchangerates.oerjava.Currency;

import java.text.NumberFormat;

import java.sql.SQLException;
import java.util.Date;

import static android.view.View.OnClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by Kyle 2.1 on 2/2/14.
 * Manages the fragment for editing purchase information
 */
public class PurchaseEditFragment extends Fragment
{
    private FinanceDataSource financeDataSource;
    private Purchase currentPurchase;

    //Keys for ID and date
    public static final String PURCHASE_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.Purchases.PURCHASE";
    public static final String PURCHASE_DATE = "com.harjup_kdhyne.TravelApp.Finance.purchase_date";

    //Used to track when we are performing an action on purchase date
    public static final int REQUEST_PURCHASE_DATE = 0;

    private EditText purchaseNameEditText;
    private EditText purchaseDateEditText;
    private EditText purchasePriceEditText;
    private EditText purchaseNotesEditText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentPurchase = (Purchase) bundle.getSerializable(PURCHASE_SERIALIZABLE_ID);
        }

        super.onCreate(savedInstanceState);
    }

    void openDbConnection()
    {
        financeDataSource = new FinanceDataSource(getActivity());

        try
        {
            financeDataSource.open();
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
        View myView = inflater.inflate(R.layout.finance_purchase_edit, container, false);

        //Open a connection to the database where purchase info is stored
        openDbConnection();

        //If the view isn't null, grab all of the textBox, button, and spinner objects
        if(myView != null) {
            //TextBoxes
            purchaseNameEditText = (EditText) myView.findViewById(R.id.purchaseNameEditText);
            purchaseDateEditText = (EditText) myView.findViewById(R.id.purchaseDateEditText);
            purchasePriceEditText = (EditText) myView.findViewById(R.id.purchasePriceEditText);
            purchaseNotesEditText = (EditText) myView.findViewById(R.id.purchaseNotesEditText);

            //Fill TextBoxes
            if(purchaseNameEditText != null)
                purchaseNameEditText.setText(currentPurchase.getPurchaseName());

            if(purchaseDateEditText != null)
                purchaseDateEditText.setText(currentPurchase.getDateString());

            if(purchasePriceEditText != null)
                purchasePriceEditText.setText(currentPurchase.getPurchasePrice());

            if(purchaseNotesEditText != null)
                purchaseNotesEditText.setText(currentPurchase.getPurchaseNotes());

            //TextWatcher used by all of the textBoxes
            TextWatcher editTextWatcher = new TextWatcher() {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                private String current = "";

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
                        //TODO: There's some funky stuff going on with the price entry. Fix it!
                        if (!arg0.toString().equals(current))
                        {
                            purchasePriceEditText.removeTextChangedListener(this);
                            double price;
                            int selection = purchasePriceEditText.getSelectionStart();

                            // Strip away the currency symbol
                            String replaceable = String.format("[%s,\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                            String cleanString = arg0.toString().replaceAll(replaceable, "");

                            // Parse the string as a double
                            try
                            {
                                price = Double.parseDouble(cleanString);
                            }
                            catch (NumberFormatException e)
                            {
                                price = 0;
                            }

                            // If we don't see a decimal, then the user must have deleted it.
                            // In that case, the number must be divided by 100, otherwise 1
                            int shrink = 1;
                            if (!arg0.toString().contains("."))
                            {
                                shrink = 100;
                            }

                            // Reformat the number
                            String formatted = currencyFormat.format(price/shrink);

                            current = formatted;
                            purchasePriceEditText.setText(formatted);
                            purchasePriceEditText.setSelection(Math.min(selection, purchasePriceEditText.getText().length()));

                            purchasePriceEditText.addTextChangedListener(this);

                            currentPurchase.setPurchasePrice(arg0.toString());
                        }
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

            //Buttons
            Button backButton = (Button) myView.findViewById(R.id.backButton);
            Button clearButton = (Button) myView.findViewById(R.id.clearButton);
            Button cameraButton = (Button) myView.findViewById(R.id.cameraButton);
            Button albumButton = (Button) myView.findViewById(R.id.albumButton);
            Button saveButton = (Button) myView.findViewById(R.id.saveButton);
            //TODO: Add buttons for working with the camera and existing pictures on the device
            //TODO: come back to these when we know how to deal with them

            backButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnToPurchasesList();
                }
            });

            clearButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    purchaseNameEditText.setText("");
                    purchasePriceEditText.setText("");
                    purchaseNotesEditText.setText("");
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
                    //Check to see if the ID hasn't been set yet (isn't in the list)
                    //Else update the changes
                    if (currentPurchase.getPurchaseID() == -1)
                        financeDataSource.createPurchase(currentPurchase);
                    else
                        financeDataSource.updatePurchase(currentPurchase);

                    //return to list view
                    returnToPurchasesList();
                }
            });

            //Spinner to select currency used to purchase the item
            Spinner purchaseCurrencySpinner = (Spinner) myView.findViewById(R.id.purchaseCurrencySpinner);
            //Populate the spinner with the Currency enum
            purchaseCurrencySpinner.setAdapter(new ArrayAdapter<Currency>(this.getActivity(), android.R.layout.simple_spinner_item, Currency.values()));

            purchaseCurrencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Currency paidCurrency = (Currency) parent.getItemAtPosition(position);
                    Log.d("Currency", paidCurrency.toString());
                    currentPurchase.setPaidCurrency(paidCurrency);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        //Inflate the view
        return myView;
    }

    //Close the db connection whenever you navigate off of the this page
    @Override
    public void onDestroyView()
    {
        try
        {
            financeDataSource.close();
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
//        if(resultCode != Activity.RESULT_OK)
//            return;

        if(requestCode == REQUEST_PURCHASE_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerDialogFragment.DATE);
            currentPurchase.setPurchaseDate(date);
            purchaseDateEditText.setText(currentPurchase.getDateString());
        }
    }
}

