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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomDialog.CustomListener , UpdateCustomDialog.UpdateCustomListener{

    ListView detailsView;
    ArrayList<User> userList;
    CustomAdapter adapter;

    TextView total_amount;
    User user;

    String name,num,city;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mylocalpref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         userList = new ArrayList<>();
         detailsView = findViewById(R.id.DetailsListView);
        FloatingActionButton fab = findViewById(R.id.fab);

        total_amount = findViewById(R.id.totalAmount);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);

        userList.add(new User( "ronak" , "9988998888" , "ahmedabad"  , "50"));
        userList.add(new User( "vaibhav" , "9988998888" , "ahmedabad"  , "60"));
        userList.add(new User( "bhavya" , "9988998888" , "ahmedabad"  , "80"));
        userList.add(new User( "raj" , "9988998888" , "ahmedabad"  , "150"));
        userList.add(new User( "karan" , "1234569852" , "ahmedabad"  , "90" ));
        userList.add(new User( "jatin" , "9988998888" , "ahmedabad"  , "10" ));
        userList.add(new User( "parth" , "8765412365" , "ahmedabad"  , "150"));
        userList.add(new User( "john" , "9874563210" , "ahmedabad"  , "10" ));
        userList.add(new User( "gaurav" , "8529637412" , "ahmedabad"  , "40"));
        userList.add(new User( "savan" , "1234569874" , "ahmedabad"  , "20"));

        adapter = new CustomAdapter(this  , userList);
        detailsView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog();
                dialog.show(getSupportFragmentManager(), "dialog for input data");
            }
        });

        detailsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateCustomDialog dialog = new UpdateCustomDialog();
                dialog.show(getSupportFragmentManager(), "dialog for update data");

                user =  userList.get(position);

                name =  user.getNames();
                num =  user.getNumbers();
                city = user.getCity();

            }
        });



        int val = calculateTotal();
        total_amount.setText("Total Amount : " +val);

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

    public int calculateTotal(){
        int total=0;
        for(int i=0;i<userList.size();++i){
            User user = userList.get(i);
            total += Integer.parseInt(user.getMoney());
        }

        return total;
    }

    @Override
    public void input(String username, String num, String city, String amount) {
        userList.add(new User( username, num , city , amount));
        adapter.notifyDataSetChanged();
        int val = calculateTotal();
        total_amount.setText("Total Amount : " +val);
    }

    @Override
    public void update(String amount) {

        if(amount.length() != 0){
            adapter.remove(user);
            userList.add(new User( name, num , city , amount));
            adapter.notifyDataSetChanged();
            int val = calculateTotal();
            total_amount.setText("Total Amount : " +val);
        }


    }
}

