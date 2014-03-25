package com.harjup_kdhyne.TravelApp.Translation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.harjup_kdhyne.TravelApp.R;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 2/10/14.
 * Home page of translation tab
 * Allows the user to type in a phrase and get it translated to a target language
 */

public class TranslationHomeFragment extends Fragment
{
    TranslationDataSource myDataSource;

    static Language currentLanguage;
    public static Language getCurrentLanguage(){
        if (currentLanguage == null)
        {
            currentLanguage = Language.FRENCH;
        }
        return currentLanguage;
    }

    final String INPUT_TEXT_ID = "STRING_TO_TRANSLATE";
    final String OUTPUT_TEXT_ID = "TRANSLATED_STRING";



    String stringToTranslate;
    String translatedString;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        myDataSource = TranslationDataSource.getInstance(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.translation_home, container, false);

        final EditText inputField = (EditText) myView.findViewById(R.id.tranlationInputEditText);
        final TextView outputField = (TextView) myView.findViewById(R.id.translationOutputTextView);

        //final Spinner
        //Spinner to select currency used to purchase the item
        Spinner targetLanguageSpinner = (Spinner)myView.findViewById(R.id.targetLanguageSpinner);

        if (targetLanguageSpinner != null)
        {

            Language[] languages = Language.values();
            final List<String> langValues = new ArrayList<String>();
            for (Language l : languages)
            {
                if (l.name().equals("AUTO_DETECT"))
                    continue;

                langValues.add(l.name());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, langValues);


            //Populate the spinner with the Currency enum and select currentTrip currency
            targetLanguageSpinner.setAdapter(adapter);
            //targetLanguageSpinner.setSelection(adapter.getPosition(currentTrip.getTargetCurrency()));

            targetLanguageSpinner.setSelection(
                    langValues.indexOf(getCurrentLanguage().name())
            );

            targetLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedLangString = langValues.get(position);
                    Language selectedLang;
                    for (Language l : Language.values())
                    {
                        if (l.name().equals(selectedLangString))
                            currentLanguage = l;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }



        final Button translateButton = (Button) myView.findViewById(R.id.translateButton);
        final Button addToPhrasebookButton = (Button) myView.findViewById(R.id.addToPhrasebookButton);
        final Button phraseBookButton = (Button) myView.findViewById(R.id.phrasebookButton);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString(INPUT_TEXT_ID) != null)
            {
                stringToTranslate = savedInstanceState.getString(INPUT_TEXT_ID);
                inputField.setText(stringToTranslate);
            }
            if (savedInstanceState.getString(OUTPUT_TEXT_ID) != null)
            {
                translatedString = savedInstanceState.getString(OUTPUT_TEXT_ID);
                outputField.setText(translatedString);

            }
        }


        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringToTranslate = editable.toString();
            }
        });

        outputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                translatedString = editable.toString();
            }
        });


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                outputField.setText("Getting translation...");
                new AsyncTranslate(){
                    @Override
                    protected void onPostExecute(String result) {
                        //outputField.setText(translatedString);
                        Log.d("Translation" , String.format("Translating %s to %s", inputField.getText().toString(), result));
                        outputField.setText(result);
                        if (result != null)
                            translatedString = result;
                    }
                }.execute(inputField.getText().toString());
            }
        });


        addToPhrasebookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Needs to open some kinda pop up that asks for categories it should be stuck under
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                Translation translationToAdd = myDataSource.getTranslationByHomePhrase(stringToTranslate);

                if (translationToAdd == null)
                {
                    translationToAdd = new Translation(-1, stringToTranslate, translatedString);
                    translationToAdd.setPhrase(getCurrentLanguage().toString(), translatedString);
                }
                else if (!translationToAdd.getLanguages().contains(getCurrentLanguage().toString()))
                {
                    translationToAdd.setPhrase(new Phrase(getCurrentLanguage().toString(), translatedString));
                }

                // Create and show the dialog.
                AddPhraseDialog newFragment = AddPhraseDialog.newInstance(translationToAdd);
                //AddPhraseDialog newFragment = AddPhraseDialog.newInstance(stringToTranslate, "en", translatedString, "fr");
                //newFragment.setArguments(INPUT_TEXT_ID, stringToTranslate);

                newFragment.show(fm, "dialog");
            }
        });


        phraseBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCategoryList();
            }
        });

        return myView;
    }

    private void viewCategoryList(){
        CategoryListFragment myCategoryList = new CategoryListFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, myCategoryList);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString(INPUT_TEXT_ID, stringToTranslate);
        outState.putString(OUTPUT_TEXT_ID, translatedString);
        super.onSaveInstanceState(outState);
        Log.d("lifecycle", "instance state saved");
    }

}


