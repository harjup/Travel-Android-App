package com.harjup_kdhyne.TravelApp.Translation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.List;

/**
 * Created by Paul on 2/17/14.
 * TODO: Write short summary of class
 */
public class TranslationListItemAdapter extends ArrayAdapter<Translation>
{



    public TranslationListItemAdapter(Context context, int resource, List<Translation> objects)
    {
        super(context, resource, objects);
        translationList = objects;
    }

    private List<Translation> translationList;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.translation_phrase_list_item, null);
        }

        Translation translation = translationList.get(position);

        if (translation != null)
        {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView homeLanguagePhraseView = (TextView) v.findViewById(R.id.homeLanguagePhraseTextView);
            TextView targetLanguagePhraseView = (TextView) v.findViewById(R.id.targetLanguagePhraseTextView);

            TextView homeLanguageView = (TextView) v.findViewById(R.id.homeLanguageTextView);
            TextView targetLanguageView = (TextView) v.findViewById(R.id.targetLanguageTextView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (homeLanguagePhraseView != null)
            {
                homeLanguagePhraseView.setText(translation.getPhraseByIndex(0).getContents());
            }

            if (homeLanguageView != null)
            {
                homeLanguageView.setText(translation.getPhraseByIndex(0).getLanguage());
            }


            if (targetLanguagePhraseView != null)
            {
                targetLanguagePhraseView.setText(translation.getPhraseByIndex(1).getContents());
            }

            if (targetLanguageView != null)
            {
                targetLanguageView.setText(translation.getPhraseByIndex(1).getLanguage());
            }


            // the view must be returned to our activity
            return v;
        }


        return super.getView(position, convertView, parent);
    }
}
