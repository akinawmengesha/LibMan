package com.example.libman;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button reserveBookButton;
    private Button returnBookButton;
    private Button viewReservedBooksButton;
    private String memberName; // Holds the name of the logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Get the name passed from UserLogin
        memberName = getIntent().getStringExtra("member");

        // Display the welcome message
        if (memberName != null && !memberName.isEmpty()) {
            welcomeTextView.setText("Welcome, " + memberName + "!");
        } else {
            welcomeTextView.setText("Welcome!");
        }

        // Set click listeners for buttons
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Reserve Book
    }
}
