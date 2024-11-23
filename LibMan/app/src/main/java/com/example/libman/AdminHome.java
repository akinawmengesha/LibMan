package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        // Retrieve the username from the Intent

      //  TextView name = findViewById(R.id.textView9);            // ID for the welcome message TextView

       // Retrieve text from editTextName and set it to name TextView with a welcome message
       // String userName = name.getText().toString();
     //   name.setText("                        Welcome " + userName);

        Button btnbook = findViewById(R.id.btnbook);
        btnbook.setOnClickListener(v -> {
            startActivity(new Intent(AdminHome.this, Book.class));
        });

        Button member = findViewById(R.id.btnmember);
        member.setOnClickListener(v -> {
            startActivity(new Intent(AdminHome.this, Member.class));
        });

        Button lend = findViewById(R.id.btnlendbook);
        lend.setOnClickListener(v -> {
            startActivity(new Intent(AdminHome.this, ReservationActivity.class));
        });

        Button returnbook = findViewById(R.id.btnreturn);
        returnbook.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHome.this, ViewReservedBook.class);
            startActivity(intent);
        });

        Button home = findViewById(R.id.btnhome);
        home.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(AdminHome.this, AdminHome.class));
        });

        Button search = findViewById(R.id.btnsearch);
        search.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(AdminHome.this, ViewBook.class));
        });


        Button logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(AdminHome.this, SelectProfile.class));
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}