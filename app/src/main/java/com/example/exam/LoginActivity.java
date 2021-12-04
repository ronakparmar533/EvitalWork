package com.example.exam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText mobileNumber , password;
    Button loginBtn;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mylocalpref";
    private static final String KEY_MOBILE = "mobile_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        mobileNumber = findViewById(R.id.Number);
        password = findViewById(R.id.Password);
        sharedPreferences  =  getSharedPreferences( SHARED_PREF_NAME , MODE_PRIVATE );

        loginBtn = findViewById(R.id.Loginbutton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_number = mobileNumber.getText().toString();
                String Password = password.getText().toString();

                if(validateInfo(mobile_number , Password)){
                    if(mobile_number.equals("7777777777") && Password.equals("eVital@123")){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString( KEY_MOBILE , mobileNumber.getText().toString());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        finish();

                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid details", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    // validating login information
    private Boolean validateInfo(String mobile_number, String user_password) {

        if(mobile_number.length()!=10){
            mobileNumber.requestFocus();
            mobileNumber.setError("Enter valid mobile number");
            return false;
        }

        else if( !mobile_number.matches("[6-9][0-9]{9}")  ){
            mobileNumber.requestFocus();
            mobileNumber.setError("Enter Correct number");
            return false;
        }

        else if( user_password.length() < 6 ){
            password.requestFocus();
            password.setError("Minimum 6 character required.");
            return false;
        }
        else{
            return true;
        }

    }


}