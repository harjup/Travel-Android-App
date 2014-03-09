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
        //super.onCreateDialog(savedInstanceState);
        categoryList = myDataSource.getAllCategories();

        myTranslation = (Translation)getArguments().getSerializable("translation");
        homeLanguage = myTranslation.getHomeLanguage();
        homePhrase = myTranslation.getHomePhrase();
        targetLanguage = "fr";
        targetPhrase = myTranslation.getPhraseContent("fr");





        /*final Category[] categoryList = new Category[] {
                new Category(-1, "Food"),
                new Category(-1, "Drink"),
                new Category(-1, "Common")
        };*/


        //final Category[] categoryList = categories.toArray(new Category[categories.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("asdf");
        builder.setNegativeButton("Nop", null);

        builder.setPositiveButton("Pon", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Package up and save/update the current translation object with all its categories and phrases
                myTranslation.setPhrase(targetLanguage, targetPhrase);
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
        //final Category[] translationCategories = translationCats.toArray(new Category[translationCats.size()]);


        checkListItemsInTranslation();


        final Fragment thisFragment = this;
        //Let the user create a new catagory if they hit the newCategory button
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

        //Add the category to the top of the list, refresh the dataAdapter, and set it as checked
        categoryList.add(0, (Category)data.getSerializableExtra("category"));
        adapter.notifyDataSetChanged();
        //myListView.setItemChecked(0, true);
        checkListItemsInTranslation();

        super.onActivityResult(requestCode, resultCode, data);
    }

    void checkListItemsInTranslation()
    {
        List<Category> translationCategories = myTranslation.getCategories();
        //final Category[] translationCategories = translationCats.toArray(new Category[translationCats.size()]);


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


    void initDialog()
    {
        //Check if the translation already exists (if input phrase exactly matches homePhrase)
        //If it does not, create a new translation and assign input phrases to their respective values

        //Get a list of categories and populate them in the list box
        //If the translation has any categories, check all the corresponding category boxes
    }
}
