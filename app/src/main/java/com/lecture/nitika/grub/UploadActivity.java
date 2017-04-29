package com.lecture.nitika.grub;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        TextView c1 = (TextView)findViewById(R.id.title);
        TextView c2 = (TextView)findViewById(R.id.prepTime);
        TextView c3 = (TextView)findViewById(R.id.ingList);
       // TextView c4 = (TextView)findViewById(R.id.upload);
        TextView c5 = (TextView)findViewById(R.id.direct);
        TextView c6 = (TextView)findViewById(R.id.upload_text);

        FloatingActionButton backgroundImg = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        backgroundImg.setBackgroundColor(000000);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Cookit-Regular.ttf");

        c1.setTypeface(custom_font);
        c2.setTypeface(custom_font);
        c3.setTypeface(custom_font);
       // c4.setTypeface(custom_font);
        c5.setTypeface(custom_font);
        c6.setTypeface(custom_font);

    }
}
