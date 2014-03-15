package com.harjup_kdhyne.TravelApp.Notes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.harjup_kdhyne.TravelApp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/28/14.
 * This fragment contains a list of all the current notes for the user to peruse.
 * The user can also create and delete notes
 */
public class NotesListFragment extends ListFragment
{
    private NotesDataSource notesDataSource;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    enum InteractionMode
    {
        DefaultMode,
        DeleteMode
    };

    InteractionMode interactionMode = InteractionMode.DefaultMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Open a connection to the notes database and use it to fill the noteList,
    //then insert the noteList into the dataAdapter so the view can use it
    private void fillNoteList()
    {
        notesDataSource = NotesDataSource.getInstance(getActivity());

        noteList = notesDataSource.getAllNotes();

        noteAdapter = new NoteAdapter(getActivity(), R.layout.note_list_item, (ArrayList<Note>) noteList);
        setListAdapter(noteAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fillNoteList();

        View myView = inflater.inflate(R.layout.note_list, container, false);

        Button addButton = (Button) myView.findViewById(R.id.notesAddNoteButton);
        final Button deleteButton = (Button) myView.findViewById(R.id.notesDeleteNoteButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new note, navigate to details page with specified note as argument
                viewNoteDetails(new Note());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (interactionMode)
                {
                    case DefaultMode:
                        deleteButton.setBackgroundColor(Color.RED);
                        interactionMode = InteractionMode.DeleteMode;
                        break;
                    case DeleteMode:
                        deleteButton.setBackgroundColor(Color.DKGRAY);
                        interactionMode = InteractionMode.DefaultMode;
                        break;
                }
            }
        });

        return myView;
    }

    //View a note's details page when it is tapped
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        switch (interactionMode)
        {
            case DefaultMode:
                viewNoteDetails(noteList.get(position));
                break;
            case DeleteMode:
                notesDataSource.deleteNote(noteList.get(position));
                noteAdapter.remove(noteList.get(position));
                break;
        }
    }


    private void viewNoteDetails(Note note){
        NoteDetailsFragment myNoteDetails = new NoteDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable("com.harjup_kdhyne.TravelApp.Notes.NOTE", note);
        myNoteDetails.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, myNoteDetails);
        //ft.addToBackStack(null);
        ft.commit();
    }

}