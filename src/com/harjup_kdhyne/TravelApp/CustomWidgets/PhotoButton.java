package com.harjup_kdhyne.TravelApp.CustomWidgets;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle 2.1 on 2/10/14
 * A button that allows the user to either take a picture with the device's camera
 * or select one from an existing photo album
 */
public class PhotoButton extends ImageButton
{
    ImageActivity imageActivity = new ImageActivity();

    private String ImageURIString;

    public PhotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getImageURIString() {
        return ImageURIString;
    }

    public void setImageURIString(String imageURIString) {
        ImageURIString = imageURIString;
    }

    @Override
    public void setOnClickListener(final OnClickListener listener)
    {
        super.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create a new intent to ask for camera
                imageActivity.invokeCameraIntent();
                Log.d("PhotoButton", "Call to invokeCameraIntent has fired");
                setImageBitmap(imageActivity.getPhoto());
                setImageURIString(imageActivity.getTempUri().toString());

                listener.onClick(v);
            }
        });
    }
}

class ImageActivity extends Activity
{
    private static final int CAMERA_REQUEST = 0;

    private Bitmap photo;
    private Uri tempUri;
    private File finalFile;

    public Bitmap getPhoto() {
        return photo;
    }

    public Uri getTempUri() {
        return tempUri;
    }

    public File getFinalFile() {
        return finalFile;
    }

    void invokeCameraIntent()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));

            // Save the image in local storage
            storeImage(photo);
        }
        else
        {
            Log.d("PhotoButton", "result not processed");
        }
    }

    Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
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
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        }
        catch (FileNotFoundException e) { Log.d("PhotoButton", "File not found: " + e.getMessage()); }
        catch (IOException e) { Log.d("PhotoButton", "Error accessing file: " + e.getMessage()); }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile()
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(String.format("%s/Android/data/%s/Files", Environment.getExternalStorageDirectory(), getApplicationContext().getPackageName()));

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists())
        {
            if (! mediaStorageDir.mkdirs())
                return null;
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}