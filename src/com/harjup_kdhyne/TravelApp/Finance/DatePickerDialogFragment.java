package com.harjup_kdhyne.TravelApp.Finance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import com.harjup_kdhyne.TravelApp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kyle 2.1 on 2/12/14.
 */
public class DatePickerDialogFragment extends DialogFragment
{
    public static final String DATE = "com.harjup_kdhyne.TravelApp.date";

    private Date date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        date = (Date) getArguments().getSerializable(DATE);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Inflate dialog
        View myView = getActivity().getLayoutInflater().inflate(R.layout.date_picker_single, null);

        // Access the DatePicker in dialog_date.xml
        DatePicker datePicker = (DatePicker) myView.findViewById(R.id.date_picker);
        // If the birth date changes prepare to pass the new value

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker theView, int year, int month, int day) {

                date = new GregorianCalendar(year, month, day).getTime();

                // Put the new value in the Bundle for passing
                // to other activities

                getArguments().putSerializable(DATE, date);

            }

        });

        return new AlertDialog.Builder(getActivity())
                .setView(myView)
                .setTitle("Pick a date")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public static DatePickerDialogFragment newInstance(Date date){

        // Bundle holds the value for birth date
        Bundle dataPassed = new Bundle();

        // Put the key value pair in the Bundle
        dataPassed.putSerializable(DATE, date);

        // Create the DateDialogFragment and attach the birth date
        DatePickerDialogFragment dateFragment = new DatePickerDialogFragment();
        dateFragment.setArguments(dataPassed);
        return dateFragment;

    }

    private void sendResult(int resultCode){

        // Check that the target Fragment was set up in ContactFragment
        if(getTargetFragment() == null) return;

        // If we have the target set up are intention to provide
        // it with the date
        Intent theIntent = new Intent();
        theIntent.putExtra(DATE, date);

        // Get the target and have it receive the data
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, theIntent);

    }
}
