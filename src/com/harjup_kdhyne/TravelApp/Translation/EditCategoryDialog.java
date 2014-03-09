package com.harjup_kdhyne.TravelApp.Translation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/23/14.
 * TODO: Write short summary of class
 */
public class EditCategoryDialog extends DialogFragment
{
    TranslationDataSource myDataSource;

    public static EditCategoryDialog newInstance(Category category)
    {
        EditCategoryDialog editCategoryDialog = new EditCategoryDialog();
        //Set any arguments for the dialog here
        Bundle args = new Bundle();
        args.putSerializable("category", category);
        editCategoryDialog.setArguments(args);

        return editCategoryDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        myDataSource = TranslationDataSource.getInstance(getActivity());
        Category inputCategory = (Category) getArguments().getSerializable("category");


        final Translation[] allTranslations = myDataSource.getAllTranslations();
        final List<Long> setTranslationList = new ArrayList<Long>();



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (inputCategory != null)
        {
            //Set title to Edit Category
            builder.setTitle("Edit Category");
            //Initialize fields with values...
            //Name
            //Associated Phrases
        }
        else
        {
            builder.setTitle("Add Category");
            inputCategory = new Category();
        }
        final Category finalInputCategory = inputCategory;

        View myView = getActivity().getLayoutInflater().inflate(R.layout.translation_edit_category_dialog, null);

        if (myView != null) {
            EditText categoryNameEditText = (EditText) myView.findViewById(R.id.categoryNameEditText);
            categoryNameEditText.setText(finalInputCategory.getName());

            categoryNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    finalInputCategory.setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }





        //Display list of categories that can be added to the list
        assert myView != null;
        final ListView myListView = (ListView) myView.findViewById(R.id.setPhrasesListView);
        final ArrayAdapter<Translation> adapter = new ArrayAdapter<Translation>(
                getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                allTranslations);
        myListView.setAdapter(adapter);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myListView.isItemChecked(i))
                {
                     if (!setTranslationList.contains(allTranslations[i].getId()))
                     {
                         setTranslationList.add(allTranslations[i].getId());
                     }
                }
                else
                {
                    if (setTranslationList.contains(allTranslations[i].getId()))
                    {
                        setTranslationList.remove(allTranslations[i].getId());
                    }
                }
            }
        });






        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Dismiss dialog, go back to whatever the previous view was without doing anything

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Save Translation-Category mappings
                //Save current category to db

                finalInputCategory.setId(myDataSource.saveCategory(finalInputCategory));

                myDataSource.editTranslationCategoryMap(
                        setTranslationList,
                        finalInputCategory.getId());

                Intent intent = new Intent();
                intent.putExtra("category", finalInputCategory);
                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
            }
        });

        builder.setView(myView);
        return builder.create();
    }


}
