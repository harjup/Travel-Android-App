package com.harjup_kdhyne.TravelApp.Notes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 2/8/14.
 * TODO: Write short summary of class
 */
public class NotesContainerFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.notes_activity_container, container, false);

        ListFragment notesListFragment = new NotesListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.notesActivityContainer, notesListFragment);
        transaction.commit();

        return myView;
    }
}
