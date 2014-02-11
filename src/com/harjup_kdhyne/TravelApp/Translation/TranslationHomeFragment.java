package com.harjup_kdhyne.TravelApp.Translation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/10/14.
 * Home page of translation tab
 * Allows the user to type in a phrase and get it translated to a target language
 */
public class TranslationHomeFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View myView = inflater.inflate(R.layout.translation_home, container, false);

        return myView;
    }
}
