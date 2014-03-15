package com.harjup_kdhyne.TravelApp.Translation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.*;

/**
 * Created by Paul on 2/17/14.
 * TODO: Write short summary of class
 */
public class CategoryListFragment extends ListFragment
{
    TranslationDataSource myDataSource;
    private CategoryListItemAdapter categoryListItemAdapter;
    final String CATEGORY_ARG = "com.harjup_kdhyne.TravelApp.Category";

    enum InteractionMode
    {
        DefaultMode,
        EditMode,
        DeleteMode
    };

    Integer editCategoryIndex;

    InteractionMode interactionMode = InteractionMode.DefaultMode;

    private List<Category> categoryList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDataSource = TranslationDataSource.getInstance(getActivity());
        categoryList = myDataSource.getAllCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        categoryListItemAdapter = new CategoryListItemAdapter(getActivity(), R.layout.translation_category_list_item, categoryList);
        setListAdapter(categoryListItemAdapter);


        View myView = inflater.inflate(R.layout.translation_category_list, container, false);

        assert myView != null;


        Button backButton = (Button) myView.findViewById(R.id.categoryListBackButton);
        Button newButton = (Button) myView.findViewById(R.id.categoryListNewButton);
        final Button editButton = (Button) myView.findViewById(R.id.categoryListEditButton);
        final Button deleteButton = (Button) myView.findViewById(R.id.categoryListDeleteButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTranslationHome();
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (interactionMode)
                {
                    case DefaultMode:
                    case DeleteMode:
                        deleteButton.setBackgroundColor(Color.DKGRAY);
                        editButton.setBackgroundColor(Color.BLUE);
                        interactionMode = InteractionMode.EditMode;
                        break;
                    case EditMode:
                        editButton.setBackgroundColor(Color.DKGRAY);
                        interactionMode = InteractionMode.DefaultMode;
                        break;
                }
            }
        });


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEditCategoryDialog(null);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (interactionMode)
                {
                    case DefaultMode:
                    case EditMode:
                        editButton.setBackgroundColor(Color.DKGRAY);
                        deleteButton.setBackgroundColor(Color.RED);
                        interactionMode = InteractionMode.DeleteMode;
                        break;
                    case DeleteMode:
                        deleteButton.setBackgroundColor(Color.DKGRAY);
                        interactionMode = InteractionMode.DefaultMode;
                        break;
                }
            }
        });

        return myView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        switch (interactionMode)
        {
            case DefaultMode:
                viewCategory(categoryList.get(position));

                break;
            case EditMode:
                editCategoryIndex = position;
                viewEditCategoryDialog(categoryList.get(position));
                break;
            case DeleteMode:
                //Delete the Category from the database
                myDataSource.deleteCategory(categoryList.get(position));

                //Remove the item from the current list and refresh the adapter
                categoryList.remove(position);
                categoryListItemAdapter.notifyDataSetChanged();
                break;
        }

        super.onListItemClick(l, v, position, id);
    }

    private void viewCategory(Category category)
    {
        TranslationListFragment translationListFragment = new TranslationListFragment();

        Bundle args = new Bundle();
        args.putSerializable(CATEGORY_ARG, category);
        translationListFragment.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, translationListFragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void viewEditCategoryDialog(Category categoryToEdit){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("newCategory");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        EditCategoryDialog newFragment = EditCategoryDialog.newInstance(categoryToEdit);
        newFragment.setTargetFragment(this, 1);

        newFragment.show(fm, "newCategory");
    }



    private void viewTranslationHome(){
        TranslationHomeFragment translationHomeFragment = new TranslationHomeFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, translationHomeFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Category resultingCategory = (Category)data.getSerializableExtra("category");

        //Add the category to the top of the list
        if (interactionMode == InteractionMode.EditMode)
        {
            categoryList.set(editCategoryIndex, resultingCategory);
        }
        else
        {
            categoryList.add(resultingCategory);
        }
        categoryListItemAdapter.notifyDataSetChanged();

        super.onActivityResult(requestCode, resultCode, data);
    }
}
