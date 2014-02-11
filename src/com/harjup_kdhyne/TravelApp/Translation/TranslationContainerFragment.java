package com.harjup_kdhyne.TravelApp.Translation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/10/14.
 * Container for Translation tab
 * Contains logic for tab initialization
 */
public class TranslationContainerFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View myView = inflater.inflate(R.layout.translation_activity_container, container, false);

        Fragment translationHomeFragment = new TranslationHomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.translationActivityContainer, translationHomeFragment);
        transaction.commit();

        return myView;
    }
}
