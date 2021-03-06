package com.harjup_kdhyne.TravelApp.Notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.harjup_kdhyne.TravelApp.R;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paul on 1/28/14.
 * Display the contents of a note along with title and timestamp.
 *
 */
public class NoteDetailsFragment extends Fragment
{
    public final String NOTE_SERIALIZABLE_ID = "com.harjup_kdhyne.TravelApp.Notes.NOTE";
    static final int CAMERA_REQUEST = 1;

    private NotesDataSource notesDataSource;
    Note currentNote;
    private String photoPath;
    private File photoFile;
    private Uri tempImageUri;

    enum textBoxes
    {
        title, content, time
    }

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
        notesDataSource = NotesDataSource.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        openDbConnection();

        //Instantiate the view
        View myView = inflater.inflate(R.layout.note_details, container, false);

        if (myView != null)
        {
            final EditText titleText = (EditText) myView.findViewById(R.id.notes_detail_title);
            final EditText contentText = (EditText) myView.findViewById(R.id.notes_detail_content);
            final EditText timeText = (EditText) myView.findViewById(R.id.notes_detail_time);
            final ImageView noteImage = (ImageView) myView.findViewById(R.id.noteImageView);

            // check to see if each individual textView is null.
            // if not, assign some text and set their respective textChanged listener
            if (titleText != null) {
                titleText.setText(currentNote.getTitle());
                titleText.addTextChangedListener(getTextWatcher(textBoxes.title));
            }
            if (contentText != null) {
                contentText.setText(currentNote.getContent());
                contentText.addTextChangedListener(getTextWatcher(textBoxes.content));
            }
            if (timeText != null) {
                timeText.setText(currentNote.getTimeStampAsString());
                timeText.addTextChangedListener(getTextWatcher(textBoxes.time));
            }
            if (noteImage != null) {
                noteImage.setImageURI(currentNote.getImageUri());
            }

            final Button backButton = (Button) myView.findViewById(R.id.noteDetailsBackButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewNoteList();
                }
            });


            Button saveButton = (Button) myView.findViewById(R.id.notesSaveNote);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentNote.getId() == -1)
                        notesDataSource.createNote(currentNote);
                    else
                        notesDataSource.updateNote(currentNote);

                    viewNoteList();
                }
            });

            //DEBUG CURRENT ROW ID
            TextView idTextView = (TextView) myView.findViewById(R.id.notesIdText);
            idTextView.setText(String.valueOf(currentNote.getId()));
        }

        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Button photoButton = (Button) getActivity().findViewById(R.id.notePhotoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();

                if (Environment.MEDIA_MOUNTED.equals(state))
                {
                    invokeCameraIntent();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry there is a problem accessing your SDCard, " +
                            "please select a picture from your gallery instead.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(NOTE_SERIALIZABLE_ID, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bundle bundle = this.getArguments();

        // If this wasn't called by the date dialog exit
        if(resultCode == Activity.RESULT_CANCELED)
        {
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(),"Canceled, no photo was taken.",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST)
        {
            try
            {
                Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri photoUri = getImageUri(getActivity().getApplicationContext(), photoBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                photoFile = new File(getRealPathFromURI(photoUri));

                // Save the image in local storage
                storeImage(photoBitmap);

                if (photoUri != null && getView() != null)
                {
                    currentNote.setImageUri(photoUri);
                    ImageView noteImage = (ImageView) getView().findViewById(R.id.noteImageView);
                    noteImage.setImageURI(photoUri);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this.getActivity().getApplicationContext(), "Internal error", Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }

    private TextWatcher getTextWatcher(final textBoxes textType)
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
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
            public void afterTextChanged(Editable editable) {}
        };
    }

    private void viewNoteList()
    {
        NotesListFragment notesListFragment = new NotesListFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.notesActivityContainer, notesListFragment);
        ft.commit();
    }

    public void invokeCameraIntent()
    {
        try
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            PackageManager currentPM = getActivity().getPackageManager();

            if (currentPM != null && cameraIntent.resolveActivity(currentPM) != null)
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        catch (Exception e)
        {
            Toast.makeText(this.getActivity().getApplicationContext(), "There was an error accessing your camera",
                    Toast.LENGTH_LONG).show();
            //Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri)
    {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /**
     * Save an image within local storage with a timestamped image path
     * @param image the image to store
     */
    private void storeImage(Bitmap image)
    {
        File pictureFile = getOutputMediaFile();

        if (pictureFile == null)
        {
            Log.d("PhotoButton", "Error creating media file, check storage permissions: ");
            return;
        }

        try
        {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        }
        catch (FileNotFoundException e) { Log.d("PhotoButton", "File not found: " + e.getMessage()); }
        catch (IOException e) { Log.d("PhotoButton", "Error accessing file: " + e.getMessage()); }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile()
    {
        // To be safe, check that the SDCard is mounted
        if (!Environment.getExternalStorageState().equals("MEDIA_MOUNTED"))
        {
            return null;
        }

        File mediaStorageDir = new File(String.format("%s/Android/data/%s/Files", Environment.getExternalStorageDirectory(), getActivity().getPackageName()));

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists())
        {
            if (! mediaStorageDir.mkdirs())
                return null;
        }

        // Create a media file name
        long captureTime = System.currentTimeMillis();
        String mImageName = Environment.getExternalStorageDirectory() + "/TravelApp" + captureTime + ".jpg";
        return new File(String.format("%s%s%s", mediaStorageDir.getPath(), File.separator, mImageName));
    }
}
