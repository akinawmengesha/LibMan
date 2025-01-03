package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);

        Button addbook = findViewById(R.id.btnaddbookformdetailsscreen);
        addbook.setOnClickListener(v -> {
            Intent intent = new Intent(Book.this, AddBook.class);
            startActivity(intent);
        });

        Button viewbook = findViewById(R.id.btnviewbookfromdetailsscreen);
        viewbook.setOnClickListener(v -> {
            Intent intent = new Intent(Book.this, ViewBook.class);
            startActivity(intent);
        });

        Button home = findViewById(R.id.btnhome);
        home.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(Book.this, AdminHome.class));
        });

        Button search = findViewById(R.id.btnsearch);
        search.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(Book.this, ViewBook.class));
        });

        Button logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(Book.this, SelectProfile.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}