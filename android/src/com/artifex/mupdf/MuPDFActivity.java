package com.artifex.mupdf;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.LinearLayout.*;
import java.io.File;

import com.artifex.mupdf.PixmapView;

public class MuPDFActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            System.out.println("Media mounted read/write");
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            System.out.println("Media mounted read only");
        }
        else
        {
            System.out.println("No media at all! Bale!\n");
            return;
        }
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "test.pdf");
        System.out.println("Trying to open "+file.toString());
        PixmapView pixmapView = new PixmapView(this, file.toString());
        super.onCreate(savedInstanceState);

        /* Now create the UI */
        RelativeLayout  layout;
        LinearLayout    bar;
        MyButtonHandler bh = new MyButtonHandler(pixmapView);

        bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bh.buttonStart = new Button(this);
        bh.buttonStart.setText("<<");
        bh.buttonStart.setOnClickListener(bh);
        bar.addView(bh.buttonStart);
        bh.buttonPrev = new Button(this);
        bh.buttonPrev.setText("<");
        bh.buttonPrev.setOnClickListener(bh);
        bar.addView(bh.buttonPrev);
        bh.buttonNext = new Button(this);
        bh.buttonNext.setText(">");
        bh.buttonNext.setOnClickListener(bh);
        bar.addView(bh.buttonNext);
        bh.buttonEnd = new Button(this);
        bh.buttonEnd.setText(">>");
        bh.buttonEnd.setOnClickListener(bh);
        bar.addView(bh.buttonEnd);

        layout = new RelativeLayout(this);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(
                                   RelativeLayout.LayoutParams.FILL_PARENT,
                                   RelativeLayout.LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.FILL);

        RelativeLayout.LayoutParams barParams =
                          new RelativeLayout.LayoutParams(
                                   RelativeLayout.LayoutParams.FILL_PARENT,
                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
        barParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bar.setId(100);
        layout.addView(bar, barParams);

        RelativeLayout.LayoutParams pixmapParams =
                          new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.FILL_PARENT,
                                    RelativeLayout.LayoutParams.FILL_PARENT);
        pixmapParams.addRule(RelativeLayout.ABOVE,100);
        layout.addView(pixmapView, pixmapParams);

        setContentView(layout);
    }

    private class MyButtonHandler implements OnClickListener
    {
        Button buttonStart;
        Button buttonPrev;
        Button buttonNext;
        Button buttonEnd;
        PixmapView pixmapView;

        public MyButtonHandler(PixmapView pixmapView)
        {
            this.pixmapView = pixmapView;
        }

        public void onClick(View v)
        {
            if (v == buttonStart)
                pixmapView.changePage(Integer.MIN_VALUE);
            else if (v == buttonPrev)
                pixmapView.changePage(-1);
            else if (v == buttonNext)
                pixmapView.changePage(+1);
            else if (v == buttonEnd)
                pixmapView.changePage(Integer.MAX_VALUE);
        }
    }
}