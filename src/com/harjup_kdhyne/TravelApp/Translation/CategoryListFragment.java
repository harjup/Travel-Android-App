package com.harjup_kdhyne.TravelApp.Translation;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.*;

/**
 * Created by Paul on 2/17/14.
 * TODO: Write short summary of class
 */
public class CategoryListFragment extends ListFragment
{
    private CategoryListItemAdapter categoryListItemAdapter;


    private List<Category> categoryList = new ArrayList<Category>() {{
        add(new Category(1,"Common"));
        add(new Category(2,"Food"));
        add(new Category(3,"Money"));
        add(new Category(4,"Transportation"));
    }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

}
