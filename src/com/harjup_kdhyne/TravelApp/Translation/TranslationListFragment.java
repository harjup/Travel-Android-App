package com.harjup_kdhyne.TravelApp.Translation;

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
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
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

    Boolean deleteMode = false;

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
                viewCategoryList();
            }
        });

        final Button deleteButton = (Button) myView.findViewById(R.id.phraseListDeleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMode = !deleteMode;

                if (deleteMode)
                {
                    deleteButton.setBackgroundColor(Color.RED);
                    return;
                }
                deleteButton.setBackgroundColor(Color.DKGRAY);
            }
        });

        return myView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        if (!deleteMode)
        {
            viewTranslation(translationList.get(position));
        }
        else
        {
            myDataSource.deleteTranslation(translationList.get(position));
            translationList.remove(position);
            translationListItemAdapter.notifyDataSetChanged();
        }

        super.onListItemClick(l, v, position, id);
    }

    private void viewTranslation(Translation selectedTranslation) {
        //Launch translation view pane or something
        //Needs to open some kinda pop up that asks for categories it should be stuck under
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddPhraseDialog newFragment = AddPhraseDialog.newInstance(selectedTranslation, false);
        newFragment.show(fm, "dialog");

    }

    private void viewCategoryList(){
        CategoryListFragment categoryListFragment = new CategoryListFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, categoryListFragment);
        ft.commit();
    }
}
