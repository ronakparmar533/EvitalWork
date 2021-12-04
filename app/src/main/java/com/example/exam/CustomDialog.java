package com.example.exam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CustomDialog extends AppCompatDialogFragment {

    CustomListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter data : ");

        View customLayout = getLayoutInflater().inflate(R.layout.insertion_layout , null);
        builder.setView(customLayout);

        EditText EV_name = customLayout.findViewById(R.id.name);
        EditText EV_number = customLayout.findViewById(R.id.number);
        EditText EV_City = customLayout.findViewById(R.id.city);
        EditText EV_amount = customLayout.findViewById(R.id.money);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = EV_name.getText().toString();
                String number = EV_number.getText().toString();
                String city = EV_City.getText().toString();
                String amount = EV_amount.getText().toString();

                listener.input(name, number , city , amount);

            }
        });

        return builder.create();

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CustomListener) context;
    }

    interface CustomListener {
        void input(String username, String num , String city , String amount);
    }

}

