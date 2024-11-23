package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    // TextViews to display user data
    TextView email,contactNo, validityPeriod, userLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Set title for the Action Bar
        getSupportActionBar().setTitle("Welcome To LibMan");


        // Retrieve data passed via the Intent (session data)
        // If you passed data using Intent, extract it here
        // Example: get data from previous activity (Login or Registration)
        email.setText(getIntent().getStringExtra("Email"));
        contactNo.setText(getIntent().getStringExtra("ContactNo"));
        validityPeriod.setText(getIntent().getStringExtra("Validity"));

        // Setup logout button
        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "User Logging Out...", Toast.LENGTH_SHORT).show();

                // Redirect to login screen on logout
                Intent startUserLoginActivity = new Intent(Dashboard.this, UserLogin.class);
                startActivity(startUserLoginActivity);
                finish();  // Optional: Call finish to remove the current activity from the stack
            }
        });
    }

    // Override the back button behavior (to prevent going back to the previous screen)
    @Override
    public void onBackPressed() {
        // Move the activity to the back of the activity stack
        moveTaskToBack(true);
        // Call the super method to handle default back button behavior
        super.onBackPressed();
    }
}
