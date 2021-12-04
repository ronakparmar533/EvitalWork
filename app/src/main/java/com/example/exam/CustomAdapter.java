package com.example.exam;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<User> {

    ArrayList<User> users;
    Context context;

    public CustomAdapter(Context context, ArrayList<User> users) {
        super(context,0 ,users);
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View Myview = LayoutInflater.from(context).inflate(R.layout.userinfolayout , parent , false);

        TextView names = Myview.findViewById(R.id.name);
        TextView numbers = Myview.findViewById(R.id.number);
        TextView cities = Myview.findViewById(R.id.city);
        TextView TV_money = Myview.findViewById(R.id.money);

        ImageView curr_status = Myview.findViewById(R.id.status);

        User Currentuser = getItem(position);

        names.setText(Currentuser.getNames());
        numbers.setText(Currentuser.getNumbers());
        cities.setText(Currentuser.getCity());

        TV_money.setText( "\u20B9"+Currentuser.getMoney());

        int money = Integer.parseInt(Currentuser.getMoney());

        if(money > 50){
              curr_status.setBackgroundResource(R.drawable.ic_baseline_trending_up_24);
        }else{
            curr_status.setBackgroundResource(R.drawable.ic_baseline_trending_down_24);
        }


        return  Myview;

    }
}
