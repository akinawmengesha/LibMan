package com.example.libman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Akinaw Mengesha on [Current Date].
 */

public class AdminOrUser extends DialogFragment {

    final CharSequence[] item = {"Admin", "User"};
    String selection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Your Profile :").setSingleChoiceItems(item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selection = (String) item[which];
                        Intent admin = new Intent(getContext(), AdminLogin.class);
                        startActivity(admin);
                        Toast.makeText(getActivity(), "You have selected " + selection + " profile", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        selection = (String) item[which];
                        Intent user = new Intent(getContext(), UserLogin.class);
                        startActivity(user);
                        Toast.makeText(getActivity(), "You have selected " + selection + " profile", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return builder.create();
    }
}
