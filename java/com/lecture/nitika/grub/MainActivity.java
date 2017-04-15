package com.lecture.nitika.grub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
View view;
    EditText email,pwd;
    Button login;
    private static final String TAG="Grub";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        view=findViewById(R.id.image);
        /*Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vegetables);
        Bitmap blurredBitmap = BlurBuilder.blur( getApplicationContext(), largeIcon );
        view.setBackground( new BitmapDrawable( getResources(), blurredBitmap ) );*/
        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.loginButton);


      /*  TextView c1 = (TextView)findViewById(R.id.caption1);
        TextView c2 = (TextView)findViewById(R.id.caption2);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Cookit-Regular.ttf");

        c1.setTypeface(custom_font);
        c2.setTypeface(custom_font);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vegetables);
        Bitmap blurredBitmap = BlurBuilder.blur( getApplicationContext(), largeIcon );
        view.setBackground( new BitmapDrawable( getResources(), blurredBitmap ) );
        email.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email:
                email.getText().clear();
                break;
            case R.id.loginButton:
                if(email.getText().toString().equalsIgnoreCase("") && pwd.getText().toString().equalsIgnoreCase(""))
            {
                email.setError("Please enter username/email");//it gives user to info message //use any one //
                pwd.setError("Please enter password");
            }
                else if(pwd.getText().toString().equalsIgnoreCase("")){
                    pwd.setError("Please enter password");
                }
                else if(email.getText().toString().equalsIgnoreCase("")){
                    email.setError("Please enter username/email");//it gives user to info message //use any one //

                }
            else
            {
                Log.i(TAG,"going to next");
                Intent i = new Intent(this, MainPageActivity.class);
                i.putExtra("username", email.getText().toString());
                startActivity(i);
            }
        }
    }
}
