package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long LOAD_SCREEN_DELAY = 3000;  // 3-second delay before transitioning

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Ensure activity_main.xml exists

        // Set the activity to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Delay for 3 seconds before navigating to the next activity
        new Handler().postDelayed(() -> {
            // Ensure that SelectProfile activity is available and declared
            Intent mainIntent = new Intent(MainActivity.this, SelectProfile.class);
            startActivity(mainIntent);
            finish(); // Call finish() to end this activity and prevent it from appearing in the back stack
        }, LOAD_SCREEN_DELAY);

    }

}
