package com.harjup_kdhyne.TravelApp.Notes;

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

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Paul on 1/28/14.
 * Display the contents of a note along with title and timestamp.
 *
 */
public class NoteDetailsFragment extends Fragment
{
    public final String NOTE_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.Notes.NOTE";

    private NotesDataSource notesDataSource;
    Note currentNote;

    enum textBoxes
    {
        title, content, time
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            currentNote = (Note) bundle.getSerializable(NOTE_SERIALIZABLE_ID);
        }

        super.onCreate(savedInstanceState);
    }

    void openDbConnection(){
        notesDataSource = new NotesDataSource(getActivity());

        try { notesDataSource.open();}
        catch (SQLException e) { e.printStackTrace(); }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        openDbConnection();

        //Instantiate the view
        View myView = inflater.inflate(R.layout.note_details, container, false);

        final EditText titleText = (EditText) myView.findViewById(R.id.notes_detail_title);
        final EditText contentText = (EditText) myView.findViewById(R.id.notes_detail_content);
        final EditText timeText = (EditText) myView.findViewById(R.id.notes_detail_time);

        // check to see if each individual textview is null.
        // if not, assign some text!
        if (titleText != null){
            titleText.setText(currentNote.getTitle());
        }
        if (contentText != null){
            contentText.setText(currentNote.getContent());
        }
        if (timeText != null){
            timeText.setText(currentNote.getTimeStampAsString());
        }

        titleText.addTextChangedListener(getTextWatcher(textBoxes.title));
        contentText.addTextChangedListener(getTextWatcher(textBoxes.content));
        timeText.addTextChangedListener(getTextWatcher(textBoxes.time));





        Button saveButton = (Button) myView.findViewById(R.id.notesSaveNote);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (currentNote.getId() == -1)
                {
                    notesDataSource.createNote(currentNote);
                }
                else
                {
                    //Here' a shitty method of updating the current note
                    //object to reflect the changes made in the text boxes!

                    //This should probably be implemented by having two-way data-binding
                    // between the textViews and currentNote object
                    //Note newNote = new Note();
                    //newNote.setId(currentNote.getId());
                    //newNote.setTitle(titleText.getText().toString());
                    //newNote.setContent(contentText.getText().toString());

                    //notesDataSource.updateNote(newNote);
                    notesDataSource.updateNote(currentNote);
                }


                ViewNoteList();
            }
        });



        //DEBUG CURRENT ROW ID
        TextView idTextView = (TextView) myView.findViewById(R.id.notesIdText);
        idTextView.setText(String.valueOf(currentNote.getId()));

        return myView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(NOTE_SERIALIZABLE_ID, currentNote);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        try { notesDataSource.close();}
        catch (Exception e) { e.printStackTrace(); }

        super.onDestroyView();
    }

    private void ViewNoteList(){
        NotesListFragment notesListFragment = new NotesListFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, notesListFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }


    private TextWatcher getTextWatcher(final textBoxes textType){

    return new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            switch (textType)
            {
                case title:
                    currentNote.setTitle(charSequence.toString());
                    break;
                case content:
                    currentNote.setContent(charSequence.toString());
                    break;
                case time:
                    //I don't know why I keep acting like I'm going to
                    //let the user edit this
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}

}
