package com.harjup_kdhyne.TravelApp.Translation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by Paul on 2/10/14.
 * Home page of translation tab
 * Allows the user to type in a phrase and get it translated to a target language
 */

public class TranslationHomeFragment extends Fragment
{
    final String INPUT_TEXT_ID = "STRING_TO_TRANSLATE";
    final String OUTPUT_TEXT_ID = "TRANSLATED_STRING";


    final String clientId = "harjup_kdhyne_travelAppliction";
    final String clientSecret = "rApbkdNvepN7+Lp1QAsve6b4xHx+/cXX4UJKksuivSE=";
    String stringToTranslate;
    String translatedString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.translation_home, container, false);

        final EditText inputField = (EditText) myView.findViewById(R.id.tranlationInputEditText);
        final TextView outputField = (TextView) myView.findViewById(R.id.translationOutputTextView);

        final Button translateButton = (Button) myView.findViewById(R.id.translateButton);
        final Button phraseBookButton = (Button) myView.findViewById(R.id.phraseBookButton);


        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString(INPUT_TEXT_ID) != null)
            {
                inputField.setText(savedInstanceState.getString(INPUT_TEXT_ID));
            }
            if (savedInstanceState.getString(OUTPUT_TEXT_ID) != null)
            {
                outputField.setText(savedInstanceState.getString(OUTPUT_TEXT_ID));
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

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                outputField.setText("Getting translation...");
                new MyAsyncTask(){
                    @Override
                    protected void onPostExecute(String result) {
                        //outputField.setText(translatedString);
                        Log.d("Translation" ,"Translating " + inputField.getText().toString() + " to " + result);
                        outputField.setText(result);
                        translatedString = result;
                    }
                }.execute(inputField.getText().toString());
            }
        });

        phraseBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCategoryList();
            }
        });

       // outputField.setText(translatedString);

        return myView;
    }

    private void viewCategoryList(){
        CategoryListFragment myCategoryList = new CategoryListFragment();

        //myNoteDetails.setNote(note);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.translationActivityContainer, myCategoryList);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        if (stringToTranslate != null)
        outState.putString(INPUT_TEXT_ID, stringToTranslate);
        outState.putString(OUTPUT_TEXT_ID, translatedString);
        super.onSaveInstanceState(outState);
        Log.d("lifecycle", "instance state saved");
    }



    class MyAsyncTask extends AsyncTask<String, Integer, String> {

        String stringToTranslate;
        String translatedString;
        @Override
        protected String doInBackground(String... strings) {
            stringToTranslate = strings[0];
            Translate.setClientId(clientId);
            Translate.setClientSecret(clientSecret);

            try {
                translatedString = Translate.execute(stringToTranslate, Language.ENGLISH, Language.FRENCH);
            } catch (Exception e) {
                translatedString = e.toString();
            }
            return translatedString;
        }
    }

}


