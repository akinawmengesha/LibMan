package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        Button lend = findViewById(R.id.btnlendbook);
        lend.setOnClickListener(v -> {
            startActivity(new Intent(UserHome.this, ReservationActivityUser.class));
        });

        Button returnbook = findViewById(R.id.btnreturn);
        returnbook.setOnClickListener(v -> {
            startActivity(new Intent(UserHome.this, ViewReservedBookUser.class));
        });
        Button btnbook = findViewById(R.id.btnviewbook);
        btnbook.setOnClickListener(v -> {
            startActivity(new Intent(UserHome.this, ViewBookUser.class));
        });
        Button btnsearch = findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(v -> {
            startActivity(new Intent(UserHome.this, ViewBookUser.class));
        });

        Button home = findViewById(R.id.btnhome);
        home.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(UserHome.this, UserHome.class));
        });

        Button logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(UserHome.this, SelectProfile.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}