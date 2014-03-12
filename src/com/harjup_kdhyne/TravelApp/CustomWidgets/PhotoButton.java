package com.harjup_kdhyne.TravelApp.CustomWidgets;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
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
    //ImageActivity imageActivity = new ImageActivity(getContext());

    private String ImageURIString;

    public PhotoButton(Context context, AttributeSet attrs)
    {
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
                /*imageActivity.invokeCameraIntent();
                Log.d("PhotoButton", "Call to invokeCameraIntent has fired");
                setImageBitmap(imageActivity.getPhoto());
                setImageURIString(imageActivity.getTempUri().toString());*/

                listener.onClick(v);
            }
        });
    }
}

