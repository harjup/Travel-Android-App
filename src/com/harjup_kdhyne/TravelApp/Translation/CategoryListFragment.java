package com.harjup_kdhyne.TravelApp.Translation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
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

    private List<Category> categoryList = new ArrayList<Category>() {{
        add(new Category(1,"Common"));
        add(new Category(2,"Food"));
        add(new Category(3,"Money"));
        add(new Category(4,"Transportation"));
    }};

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


        return myView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        viewCategory(categoryList.get(position));

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


}
