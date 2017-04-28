package com.lecture.nitika.grub;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        TextView c=(TextView)findViewById(R.id.surveyTitle);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Cookit-Regular.ttf");

        c.setTypeface(custom_font);
    }
}
