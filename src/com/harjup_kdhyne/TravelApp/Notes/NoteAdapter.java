package com.harjup_kdhyne.TravelApp.Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/28/14.
 * TODO: Write short summary of class
 */
public class NoteAdapter extends ArrayAdapter<Note>
{
    private ArrayList<Note> noteArrayList;

    public NoteAdapter(Context context, int resource, ArrayList<Note> objects)
    {
        super(context, resource, objects);
        noteArrayList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.notes_list_item, null);
        }

        /*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Note note = noteArrayList.get(position);

        if (note != null)
        {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView titleText = (TextView) v.findViewById(R.id.notes_list_item_title);
            TextView timeText = (TextView) v.findViewById(R.id.notes_list_item_timestamp);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (titleText != null)
            {
                titleText.setText(note.getTitle());
            }
            if (timeText != null)
            {
                timeText.setText(note.getTimeStampAsString());
            }

        // the view must be returned to our activity
        return v;
        }


        return super.getView(position, convertView, parent);
    }


}
