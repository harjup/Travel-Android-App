package com.harjup_kdhyne.TravelApp.Translation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/23/14.
 * TODO: Write short summary of class
 */
public class EditCategoryDialog extends DialogFragment
{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("fuff");

        View myView = getActivity().getLayoutInflater().inflate(R.layout.translation_edit_category_dialog, null);
        builder.setView(myView);
        return builder.create();
    }
}
