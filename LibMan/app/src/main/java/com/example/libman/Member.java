package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Member extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member);


        Button addmember = findViewById(R.id.btnaddmemberformdetailsscreen);
        addmember.setOnClickListener(v -> {
            Intent intent = new Intent(Member.this, AddMember.class);
            startActivity(intent);
        });

        Button viewmember = findViewById(R.id.btnviewmemberfromdetailsscreen);
        viewmember.setOnClickListener(v -> {
            Intent intent = new Intent(Member.this, ViewMember.class);
            startActivity(intent);
        });

        Button home = findViewById(R.id.btnhome);
        home.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(Member.this, AdminHome.class));
        });

        Button search = findViewById(R.id.btnsearch);
        search.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(Member.this, ViewBook.class));
        });

        Button logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(Member.this, SelectProfile.class));
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}