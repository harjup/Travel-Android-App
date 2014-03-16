package com.harjup_kdhyne.TravelApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kyle 2.1 on 3/16/14
 * A generic error dialog that will display a given string, notifying the user that something is amiss
 */
public class ErrorMessageDialog extends DialogFragment
{
    public static final String ERROR_MESSAGE = "com.harjup_kdhyne.TravelApp.error_message";

    private String errorMessage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        errorMessage = (String) getArguments().getSerializable(ERROR_MESSAGE);

        //Inflate dialog
        View myView = getActivity().getLayoutInflater().inflate(R.layout.error_message_dialog, null);

        if (myView != null)
        {
            // Access the DatePicker in dialog_date.xml
            TextView errorMessageTextView = (TextView) myView.findViewById(R.id.errorMessageTextView);

            if (errorMessageTextView != null)
                errorMessageTextView.setText(errorMessage);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(myView)
                .setTitle("Oops!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public static ErrorMessageDialog newInstance(String errorMessage)
    {
        // Bundle holds the value for birth date
        Bundle dataPassed = new Bundle();

        // Put the key value pair in the Bundle
        dataPassed.putSerializable(ERROR_MESSAGE, errorMessage);

        // Create the DateDialogFragment and attach the birth date
        ErrorMessageDialog errorMessageDialogFragment = new ErrorMessageDialog();
        errorMessageDialogFragment.setArguments(dataPassed);
        return errorMessageDialogFragment;
    }

    private void sendResult(int resultCode)
    {
        // Check that the targetFragment was set up in the caller Fragment
        if(getTargetFragment() == null) return;

        // If we have the target, set up our intention to provide it with the date
        Intent theIntent = new Intent();
        theIntent.putExtra(ERROR_MESSAGE, errorMessage);

        // Get the target and have it receive the data
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, theIntent);
    }
}
