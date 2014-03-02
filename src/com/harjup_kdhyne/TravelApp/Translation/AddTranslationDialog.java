package com.harjup_kdhyne.TravelApp.Translation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
    String homePhrase = "";
    String homeLanguage;
    String targetPhrase = "";
    String targetLanguage;

    public static AddTranslationDialog newInstance(String homePhrase, String homeLanguage, String targetPhrase, String targetLanguage)
    {
        AddTranslationDialog addTranslationDialog = new AddTranslationDialog();
        //Set any arguments for the dialog here
        Bundle args = new Bundle();
        args.putString("homePhrase", homePhrase);
        args.putString("homeLanguage", homeLanguage);
        args.putString("targetPhrase", targetPhrase);
        args.putString("targetLanguage", targetLanguage);
        addTranslationDialog.setArguments(args);


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

        homePhrase = getArguments().getString("homePhrase");
        homeLanguage = getArguments().getString("homeLanguage");
        targetPhrase = getArguments().getString("targetPhrase");
        targetLanguage = getArguments().getString("targetLanguage");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("asdf");
        builder.setNegativeButton("Nop", null);

        builder.setPositiveButton("Pon", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("newCategory");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                NewCategoryDialog newFragment = new NewCategoryDialog();
                newFragment.show(fm, "newCategory");
            }
        });


        View myView = getActivity().getLayoutInflater().inflate(R.layout.translation_add_dialog, null);
        assert myView != null;
        final TextView myTextView = (TextView) myView.findViewById(R.id.translationAddText);

        myTextView.setText(homePhrase + " - " + targetPhrase);

        builder.setView(myView);
        return builder.create();
    }


    void initDialog()
    {
        //Check if the translation already exists (if input phrase exactly matches homePhrase)
        //If it does not, create a new translation and assign input phrases to their respective values

        //Get a list of categories and populate them in the list box
        //If the translation has any categories, check all the corresponding category boxes
    }

}
