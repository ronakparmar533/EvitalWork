package com.example.exam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mylocalpref";
    private static final String KEY_MOBILE = "mobile_number";

    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Animation animation = AnimationUtils.loadAnimation(FirstActivity.this  ,R.anim.splash_anim);
        message = findViewById(R.id.txt);
        message.startAnimation(animation);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);

        String mobileNumber = sharedPreferences.getString(KEY_MOBILE,null);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(mobileNumber != null){
                     intent = new Intent(FirstActivity.this , MainActivity.class);

                }else{
                     intent = new Intent(FirstActivity.this , LoginActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },3000);


    }
}