package com.harjup_kdhyne.TravelApp.Translation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/17/14.
 * TODO: Write short summary of class
 */
public class CategoryListItemAdapter extends ArrayAdapter<Category>
{
    private List<Category> categoryArrayList;

    public CategoryListItemAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        categoryArrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.translation_category_list_item, null);
        }

        Category category = categoryArrayList.get(position);

        if (category != null)
        {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView nameText = (TextView) v.findViewById(R.id.categoryItemName);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (nameText != null)
            {
                nameText.setText(category.getName());
            }

            // the view must be returned to our activity
            return v;
        }


        return super.getView(position, convertView, parent);
    }
}
