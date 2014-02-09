package com.harjup_kdhyne.TravelApp.Notes;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Open a connection to the notes database and use it to fill the noteList,
    //then insert the noteList into the dataAdapter so the view can use it
    private void fillNoteList()
    {
        notesDataSource = new NotesDataSource(getActivity());

        try { notesDataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

        noteList = notesDataSource.getAllNotes();

        noteAdapter = new NoteAdapter(getActivity(), R.layout.notes_list_item, (ArrayList<Note>) noteList);
        setListAdapter(noteAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fillNoteList();

        View myView = inflater.inflate(R.layout.note_list_layout, container, false);

        Button addButton = (Button) myView.findViewById(R.id.notesAddNoteButton);
        Button deleteButton = (Button) myView.findViewById(R.id.notesDeleteNoteButton);


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
                //Delete the note at index 0 for right now,
                //Update db/view
                notesDataSource.deleteNote(noteList.get(0));
                noteAdapter.remove(noteList.get(0));
            }
        });

        return myView;
    }

    //When the user navigates to a different fragment,
    //close the noteList's database connection
    @Override
    public void onDestroyView() {

        try { notesDataSource.close();}
        catch (Exception e) { e.printStackTrace(); }

        super.onDestroyView();
    }

    //View a note's details page when it is tapped
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        viewNoteDetails(noteList.get(position));
    }


    private void viewNoteDetails(Note note){
        NoteDetailsFragment myNoteDetails = new NoteDetailsFragment();

        myNoteDetails.setNote(note);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, myNoteDetails);
        ft.addToBackStack(null);
        ft.commit();
    }

}