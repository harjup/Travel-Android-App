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

        final Translation translation = translationList.get(position);

        if (translation != null)
        {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView homeLanguagePhraseView = (TextView) v.findViewById(R.id.homeLanguagePhraseTextView);
            final TextView targetLanguagePhraseView = (TextView) v.findViewById(R.id.targetLanguagePhraseTextView);

            TextView homeLanguageView = (TextView) v.findViewById(R.id.homeLanguageTextView);
            TextView targetLanguageView = (TextView) v.findViewById(R.id.targetLanguageTextView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (homeLanguagePhraseView != null)
            {
                homeLanguagePhraseView.setText(translation.getHomePhrase());
            }

            if (homeLanguageView != null)
            {
                //targetLanguagePhraseView.setText(translation.getHomeLanguage());
                homeLanguageView.setText("");
            }


            if (targetLanguagePhraseView != null)
            {
                final String langString = TranslationHomeFragment.getCurrentLanguage().toString();



                if (translation.getPhraseContent(langString) == null)
                {
                    //getting translation...
                    new AsyncTranslate(){
                        @Override
                        protected void onPostExecute(String result) {
                            translation.setPhrase(new Phrase(langString, result));
                            targetLanguagePhraseView.setText(result);

                            TranslationDataSource.getInstance(getContext()).saveTranslation(translation);
                        }
                    }.execute(translation.getHomePhrase());

                    targetLanguagePhraseView.setText("Translating...");
                }
                else
                {
                    targetLanguagePhraseView.setText(translation.getPhraseContent(langString));
                }
            }

            if (targetLanguageView != null)
            {
                 targetLanguageView.setText("");
            }


            // the view must be returned to our activity
            return v;
        }


        return super.getView(position, convertView, parent);
    }
}
