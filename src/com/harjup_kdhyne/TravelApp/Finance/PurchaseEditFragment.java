package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.harjup_kdhyne.TravelApp.ImageActivity;
import com.harjup_kdhyne.TravelApp.R;
import org.openexchangerates.oerjava.Currency;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.OnClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by Kyle 2.1 on 2/2/14.
 * Manages the fragment for editing purchase information
 */
public class PurchaseEditFragment extends Fragment
{
    //Keys for ID and date
    public static final String TRIP_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.TripSettings.TRIP";
    public static final String PURCHASE_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.Purchases.PURCHASE";
    public static final String PURCHASE_DATE = "com.harjup_kdhyne.TravelApp.Finance.purchase_date";

    private FinanceDataSource financeDataSource;
    private TripSettings currentTrip;
    private Purchase currentPurchase;

    //Used to track when we are performing an action on purchase date
    public static final int REQUEST_PURCHASE_DATE = 0;

    //TODO: MOVE THESE TO NOTES
    static final int CAMERA_REQUEST = 1;
    private Bitmap photo;

    private EditText purchaseNameEditText;
    private EditText purchaseDateEditText;
    private EditText purchasePriceEditText;
    private EditText purchaseNotesEditText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //Open a connection to the database where purchase info is stored
        financeDataSource = FinanceDataSource.openDbConnection(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentPurchase = (Purchase) bundle.getSerializable(PURCHASE_SERIALIZABLE_ID);
            currentTrip = (TripSettings) bundle.getSerializable(TRIP_SERIALIZABLE_ID);
        }

        super.onCreate(savedInstanceState);
    }

    //Show the fragment on the screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.finance_purchase_edit, container, false);

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
                //NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                //private String current = "";

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
                        //TODO: Make necessary changes this after converting price to double
                        /*if (!arg0.toString().equals(current))
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
                        }*/

                        Log.d("TEXT WAS CHANGED", arg0.toString());
                        try
                        {
                            currentPurchase.setPurchasePrice(arg0.toString());
                        }
                        catch (Exception e)
                        {
                            Log.e("Null string", arg0.toString());
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
                    //Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {
                    //Auto-generated method stub
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
                    DatePickerDialog dateDialog = DatePickerDialog.newInstance(currentPurchase.getPurchaseDate());
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

            if (purchaseCurrencySpinner != null)
            {
                ArrayAdapter<Currency> adapter = new ArrayAdapter<Currency>(this.getActivity(), android.R.layout.simple_spinner_item, gatherCurrencies());
                //Populate the spinner with the Currency enum
                purchaseCurrencySpinner.setAdapter(adapter);
                purchaseCurrencySpinner.setSelection(adapter.getPosition(currentPurchase.getPaidCurrency()));

                purchaseCurrencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Currency paidCurrency = (Currency) parent.getItemAtPosition(position);
                        //Log.d("Currency", paidCurrency.toString());
                        currentPurchase.setPaidCurrency(paidCurrency);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Auto-Generated Method Stub
                    }
                });
            }

            currentPurchase.setPurchaseExchangeRate(currentTrip.getCurrentExchangeRate());
        }
        //Inflate the view
        return myView;
    }

    //TODO: MOVE TO NOTES
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Button photoButton = (Button) getActivity().findViewById(R.id.purchasePhotoButton);

        photoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageActivity imageActivity = new ImageActivity(getActivity());
                //imageActivity.invokeCameraIntent();
                invokeCameraIntent();
            }
        });

    }

    private List<Currency> gatherCurrencies()
    {
        List<Currency> currencyList = new ArrayList<Currency>();

        currencyList.add(Currency.USD);
        currencyList.add(currentTrip.getTargetCurrency());

        return currencyList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(PURCHASE_SERIALIZABLE_ID, currentPurchase);
        super.onSaveInstanceState(outState);
    }

    //Return to the purchases list
    public void returnToPurchasesList()
    {
        SummaryFragment summaryFragment = new SummaryFragment();
        PurchaseListFragment purchaseListFragment = new PurchaseListFragment();

        Bundle args = new Bundle();
        args.putSerializable(PURCHASE_SERIALIZABLE_ID, currentPurchase);
        args.putSerializable(TRIP_SERIALIZABLE_ID, currentTrip);
        summaryFragment.setArguments(args);
        purchaseListFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.add(R.id.financePurchaseListContainer, purchaseListFragment);
        fragmentTransaction.add(R.id.financeSummaryContainer, summaryFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Need this for the date picker dialog. Called when the OK button is clicked
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_PURCHASE_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerDialog.DATE);
            currentPurchase.setPurchaseDate(date);
            purchaseDateEditText.setText(currentPurchase.getDateString());
        }

        //TODO:MOVE TO NOTES
        if (requestCode == CAMERA_REQUEST )
        {
            photo = (Bitmap) data.getExtras().get("data");

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            //tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //finalFile = new File(getRealPathFromURI(tempUri));

            // Save the image in local storage
            //storeImage(photo);
        }
    }

    //TODO:MOVE ALL BELOW TO NOTES
    public void invokeCameraIntent()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /**
     * Save an image within local storage with a timestamped image path
     * @param image the image to store
     */
    private void storeImage(Bitmap image)
    {
        File pictureFile = getOutputMediaFile();

        if (pictureFile == null)
        {
            Log.d("PhotoButton", "Error creating media file, check storage permissions: ");
            return;
        }

        try
        {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        }
        catch (FileNotFoundException e) { Log.d("PhotoButton", "File not found: " + e.getMessage()); }
        catch (IOException e) { Log.d("PhotoButton", "Error accessing file: " + e.getMessage()); }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile()
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(String.format("%s/Android/data/%s/Files", Environment.getExternalStorageDirectory(), getActivity().getApplicationContext().getPackageName()));

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists())
        {
            if (! mediaStorageDir.mkdirs())
                return null;
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}

