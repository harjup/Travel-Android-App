package com.harjup_kdhyne.TravelApp.Translation;

import android.os.AsyncTask;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by Paul on 3/24/14.
 * Handles Translation requests sent out to the API
 */
public class AsyncTranslate extends AsyncTask<String, Integer, String> {

    final String clientId = "harjup_kdhyne_travelAppliction";
    final String clientSecret = "rApbkdNvepN7+Lp1QAsve6b4xHx+/cXX4UJKksuivSE=";

    String stringToTranslate;
    String translatedString;
    @Override
    protected String doInBackground(String... strings) {
        stringToTranslate = strings[0];
        Translate.setClientId(clientId);
        Translate.setClientSecret(clientSecret);

        try {
            translatedString = Translate.execute(stringToTranslate, Language.ENGLISH, TranslationHomeFragment.getCurrentLanguage());
        } catch (Exception e) {
            translatedString = e.toString();
        }
        return translatedString;
    }
}
