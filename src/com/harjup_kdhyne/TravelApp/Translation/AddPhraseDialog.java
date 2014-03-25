package com.harjup_kdhyne.TravelApp.Translation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.*;
import com.harjup_kdhyne.TravelApp.R;

import java.util.List;

/**
 * Created by Paul on 2/23/14.
 * TODO: Write short summary of class
 */
public class AddPhraseDialog extends DialogFragment
{
    TranslationDataSource myDataSource;
    Translation myTranslation = new Translation();
    String homePhrase = "";
    String homeLanguage;
    String targetPhrase = "";
    String targetLanguage;

    List<Category> categoryList;
    ArrayAdapter<Category> adapter;
    ListView myListView;

    public static AddPhraseDialog newInstance(Translation translation)
    {
        AddPhraseDialog addPhraseDialog = new AddPhraseDialog();

        //Set any arguments for the dialog here
        Bundle args = new Bundle();
        args.putSerializable("translation", translation);
        addPhraseDialog.setArguments(args);

        return addPhraseDialog;
    }


    public AddPhraseDialog() {
//        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        myDataSource = TranslationDataSource.getInstance(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryList = myDataSource.getAllCategories();

        myTranslation = (Translation)getArguments().getSerializable("translation");
        homePhrase = myTranslation.getHomePhrase();

        String langString = TranslationHomeFragment.getCurrentLanguage().toString();
        targetPhrase = myTranslation.getPhraseContent(langString);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Phrase");
        builder.setNegativeButton("Cancel", null);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Package up and save/update the current translation object with all its categories and phrases
                //myTranslation.setPhrase(targetLanguage, targetPhrase);
                myDataSource.saveTranslation(myTranslation);
                dialogInterface.dismiss();
            }
        });

        //Get View
        View myView = getActivity().getLayoutInflater().inflate(R.layout.translation_add_phrase_dialog, null);
        assert myView != null;

        //Set text for phrase to be added
        final TextView myTextView = (TextView) myView.findViewById(R.id.translationAddText);
        myTextView.setText(homePhrase + " - " + targetPhrase);

        //Display list of categories that can be added to the list
        myListView = (ListView) myView.findViewById(R.id.setCategoryListView);
        adapter = new ArrayAdapter<Category>(
                getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                categoryList);
        myListView.setAdapter(adapter);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myListView.isItemChecked(i))
                {
                    myTranslation.addCategory(categoryList.get(i));
                }
                else
                {
                    myTranslation.removeCategory(categoryList.get(i));
                }
            }
        });

        List<Category> translationCategories = myTranslation.getCategories();


        refreshCheckedCategories();


        final Fragment thisFragment = this;
        //Let the user create a new category if they hit the newCategory button
        Button newCategoryButton = (Button) myView.findViewById(R.id.newCategoryButton);
        newCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("newCategory");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                EditCategoryDialog newFragment = EditCategoryDialog.newInstance(null);
                newFragment.setTargetFragment(thisFragment, 1);

                newFragment.show(fm, "newCategory");
            }
        });



        builder.setView(myView);
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Category newCategory = (Category)data.getSerializableExtra("category");

        //Add the created Category to the current translation
        myTranslation.addCategory(newCategory);

        //Add the category to the top of the list
        categoryList.add(0, (Category)data.getSerializableExtra("category"));

        //refresh which items should be checked
        refreshCheckedCategories();

        super.onActivityResult(requestCode, resultCode, data);
    }

    void refreshCheckedCategories()
    {
        //In the event that the underlying data has changed, tell the adapter to refresh
        adapter.notifyDataSetChanged();

        //Uncheck all the items in the list view
        for (int j = 0; j < categoryList.size(); j++)
        {
                myListView.setItemChecked(j, false);

        }

        List<Category> translationCategories = myTranslation.getCategories();
        //Check any items contained in the current translation object
        for (int i = 0; i < translationCategories.size(); i++)
        {
            for (int j = 0; j < categoryList.size(); j++)
            {
                if (translationCategories.get(i).getName().equals(categoryList.get(j).getName()))
                {
                    myListView.setItemChecked(j, true);
                }

            }
        }
    }
}
