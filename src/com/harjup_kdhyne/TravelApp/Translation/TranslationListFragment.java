package com.harjup_kdhyne.TravelApp.Translation;

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
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.MySQLiteHelper;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/17/14.
 * TODO: Write short summary of class
 */
public class TranslationListFragment extends ListFragment
{
    private TranslationListItemAdapter translationListItemAdapter;
    Category currentCategory;
    private List<Translation> translationList;
    private TranslationDataSource myDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myDataSource = TranslationDataSource.getInstance(getActivity());

        try {
            myDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentCategory = (Category)bundle.getSerializable("com.harjup_kdhyne.TravelApp.Category");
            translationList = myDataSource.getTranslationsByCategory(currentCategory.getName());
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        translationListItemAdapter = new TranslationListItemAdapter(getActivity(), R.layout.translation_phrase_list, translationList);
        setListAdapter(translationListItemAdapter);

        View myView = inflater.inflate(R.layout.translation_phrase_list, container, false);

        TextView currentTranslationView = (TextView) myView.findViewById(R.id.currentLanguageTextView);
        String translationName = TranslationHomeFragment.getCurrentLanguage().name();
        translationName =
                String.valueOf(translationName.charAt(0)).toUpperCase() +
                        translationName.substring(1, translationName.length()).toLowerCase();

        currentTranslationView.setText(translationName);


        Button backButton = (Button) myView.findViewById(R.id.phraseListBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCatagoryList();
            }
        });


        return myView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        viewTranslation(translationList.get(position));

        super.onListItemClick(l, v, position, id);
    }

    private void viewTranslation(Translation selectedTranslation) {
        //Launch translation view pane or something
    }

    private void viewCatagoryList(){
        CategoryListFragment categoryListFragment = new CategoryListFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, categoryListFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }
}
