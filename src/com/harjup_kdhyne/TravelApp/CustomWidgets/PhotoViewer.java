package com.harjup_kdhyne.TravelApp.CustomWidgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Kyle 2.1 on 2/10/14.
 */
public class PhotoViewer extends ImageButton
{
    private final ImageButton self = this;
    private OnClickListener listener;

    public PhotoViewer(Context context){
        super(context);
    }

    @Override
    public void setOnClickListener(OnClickListener listener)
    {
        this.listener = listener;

        

    }

}
