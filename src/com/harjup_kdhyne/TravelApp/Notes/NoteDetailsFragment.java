package com.harjup_kdhyne.TravelApp.Notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Paul on 1/28/14.
 * Displays a list of notes for the user to examine(?)
 * The user can also create new notes(?)
 *
 * Display the contents of a note along with title and timestamp.
 */
public class NoteDetailsFragment extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        //Instantiate the view
        View myView = inflater.inflate(R.layout.note_details, container, false);

        TextView titleText = (TextView) myView.findViewById(R.id.notes_detail_title);
        TextView contentText = (TextView) myView.findViewById(R.id.notes_detail_content);
        TextView timeText = (TextView) myView.findViewById(R.id.notes_detail_time);

        // check to see if each individual textview is null.
        // if not, assign some text!
        if (titleText != null)
        {
            //titleText.setText(bundle.getString("_TITLE"));
            titleText.setText("A TITLE");
        }
        if (contentText != null)
        {
            //contentText.setText(bundle.getString("_CONTENT"));
            contentText.setText("_CONTENT");
        }
        if (timeText != null)
        {
            //timeText.setText(bundle.getString("_DATE"));
            timeText.setText("_DATE");
        }
        //Init stuff




        return myView;
    }
}
