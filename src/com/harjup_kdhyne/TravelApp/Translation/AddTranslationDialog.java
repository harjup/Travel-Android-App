package com.harjup_kdhyne.TravelApp.Translation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/23/14.
 * TODO: Write short summary of class
 */
public class AddTranslationDialog extends DialogFragment
{
    public static AddTranslationDialog newInstance(int num)
    {
        AddTranslationDialog addTranslationDialog = new AddTranslationDialog();
        //Set any arguments for the dialog here
        return addTranslationDialog;
    }

    public AddTranslationDialog() {
//        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("asdf");
        builder.setPositiveButton("Pon", null);
        builder.setNegativeButton("Nop", null);

        View myView = getActivity().getLayoutInflater().inflate(R.layout.translation_add_dialog, null);
        assert myView != null;
        final TextView myTextView = (TextView) myView.findViewById(R.id.translationAddPlaceholderTextView);
        final CheckBox myCheckBox = (CheckBox) myView.findViewById(R.id.boxboxbox);

        myCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    myTextView.setText("I AM CHECKED AS YOU CAN PLAINLY SEE");
                }
                else
                {
                    myTextView.setText("BUTTS BUTTS BUTTS BUTTS");
                }
            }
        });

        builder.setView(myView);
        return builder.create();
    }
}
