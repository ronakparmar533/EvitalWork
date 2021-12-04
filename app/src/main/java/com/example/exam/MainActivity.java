package com.example.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView detailsView;
    ArrayList<User> userList;
    DatabaseHelper mDatabaseHelper;
    CustomAdapter adapter;

    EditText name , number , city, money;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mylocalpref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         userList = new ArrayList<>();
         detailsView = findViewById(R.id.DetailsListView);
        FloatingActionButton fab = findViewById(R.id.fab);
        mDatabaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);

        userList.add(new User( "ronak" , "9988998888" , "ahmedabad"  , "150"));
        userList.add(new User( "vaibhav" , "9988998888" , "ahmedabad"  , "150"));
        userList.add(new User( "bhavya" , "9988998888" , "ahmedabad"  , "150"));
        userList.add(new User( "raj" , "9988998888" , "ahmedabad"  , "150"));
        userList.add(new User( "karan" , "1234569852" , "ahmedabad"  , "150" ));
        userList.add(new User( "jatin" , "9988998888" , "ahmedabad"  , "10" ));
        userList.add(new User( "parth" , "8765412365" , "ahmedabad"  , "150"));
        userList.add(new User( "john" , "9874563210" , "ahmedabad"  , "150" ));
        userList.add(new User( "gaurav" , "8529637412" , "ahmedabad"  , "10"));
        userList.add(new User( "savan" , "1234569874" , "ahmedabad"  , "20"));

        adapter = new CustomAdapter(this  , userList);
        detailsView.setAdapter(adapter);

        detailsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User userData = userList.get(position);

                AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update amount : ");

                View customLayout = getLayoutInflater().inflate(R.layout.update_amount_layout , null);
                builder.setView(customLayout);

                money = customLayout.findViewById(R.id.money);
                money.setText(userData.getMoney());

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // update listview
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // for adding data to listview
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter data : ");

                View customLayout = getLayoutInflater().inflate(R.layout.insertion_layout , null);
                builder.setView(customLayout);

                builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         name = customLayout.findViewById(R.id.name);
                         number = customLayout.findViewById(R.id.number);
                         city = customLayout.findViewById(R.id.city);
                         money = customLayout.findViewById(R.id.money);

                        String user_name = name.getText().toString();
                        String mobile_no = number.getText().toString();
                        String user_city = city.getText().toString();
                        String user_money =  money.getText().toString();

                        if(validateInfo(user_name , mobile_no , user_city , user_money)){
                            userList.add(new User( user_name, mobile_no , user_city , user_money));
                            adapter.notifyDataSetChanged();

                            boolean insertData = mDatabaseHelper.addData(name.getText().toString() ,number.getText().toString() ,city.getText().toString(),money.getText().toString());

                            if (insertData) {
                                Log.d("message" , "added");
                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        LoadData();
    }

    // for validating information
    private Boolean validateInfo(String user_name, String mobile_no, String user_city, String user_money) {

        if(user_name.length()==0){
            name.requestFocus();
            name.setError("Field can't be empty");
            return false;
        }

       else if(user_city.length()==0){
            city.requestFocus();
            city.setError("Field can't be empty");
            return false;
        }

        else if(user_money.length()==0){
            money.requestFocus();
            money.setError("Field can't be empty");
            return false;
        }

        else if(Integer.parseInt(user_money) <= 0){
            money.requestFocus();
            money.setError("enter proper amount");
            return false;
        }

        else if(mobile_no.length()!=10){
            number.requestFocus();
            number.setError("Enter valid mobile number");
            return false;
        }

        else if( !mobile_no.matches("[6-9][0-9]{9}")  ){
            number.requestFocus();
            number.setError("Enter correct number");
            return false;
        }else{
            return true;
        }

    }

    // loading data that user added
    private void LoadData() {

      Cursor data =  mDatabaseHelper.getData();

      if(data.getCount() == 0){
          Log.d("message"  ,"empty");
      }else{

          while(data.moveToNext()){
              userList.add(new User( data.getString(1) , data.getString(2) , data.getString(3)  , (data.getString(4)) ));
              adapter.notifyDataSetChanged();
          }

      }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar , menu);
        return super.onCreateOptionsMenu(menu);
    }

    // clear sharedprefrences
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.logout){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}