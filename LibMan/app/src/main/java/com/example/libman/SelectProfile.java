package com.example.libman;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class SelectProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
    }

    public void selectProfile(View view) {
        // Get the ID of the clicked RadioButton
        boolean checked = ((RadioButton) view).isChecked();

        // Use if-else to determine the profile selection
        if (view.getId() == R.id.selectAdmin) {  // ID for Admin RadioButton
            if (checked) {
                Intent adminIntent = new Intent(SelectProfile.this, AdminLogin.class);
                startActivity(adminIntent);
                Toast.makeText(getBaseContext(), "You have selected Admin profile", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.selectUser) {  // ID for User RadioButton
            if (checked) {
                Intent userIntent = new Intent(SelectProfile.this, UserLogin.class);
                startActivity(userIntent);
                Toast.makeText(getBaseContext(), "You have selected User profile", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
