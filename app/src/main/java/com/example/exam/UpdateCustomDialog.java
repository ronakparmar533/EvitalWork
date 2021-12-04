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

public class UpdateCustomDialog extends AppCompatDialogFragment {

    UpdateCustomListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update: ");

        View customLayout = getLayoutInflater().inflate(R.layout.update_amount_layout , null);
        builder.setView(customLayout);

        EditText EV_amount = customLayout.findViewById(R.id.money);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String amount = EV_amount.getText().toString();
                listener.update(amount);
            }
        });

        return builder.create();

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (UpdateCustomListener) context;
    }

    interface UpdateCustomListener {
        void update(String amount);
    }

}

