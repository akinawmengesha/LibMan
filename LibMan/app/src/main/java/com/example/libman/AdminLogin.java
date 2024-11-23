package com.example.libman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLogin extends AppCompatActivity {

    private static final String TAG = "AdminLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);

        // Declare EditTexts and Button
        EditText email = findViewById(R.id.txtloginemail);
        EditText password = findViewById(R.id.txtloginpassword);
        Button Login = findViewById(R.id.btnlogin);
        Login.setOnClickListener(v -> {
            // Get the text entered by the user in the EditTexts
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();

            //check if email and password are not empty
            if(!Email.isEmpty() && !Password.isEmpty()){
                // Check if the entered email and password match the valid credentials
                if (Email.equals("admin@gmail.com") && Password.equals("admin")) {
                    // Successful login
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email", Email);
                    editor.apply();
                    Toast.makeText(AdminLogin.this, "Login successful !.", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(AdminLogin.this, AdminHome.class);
                    startActivity(intent);
                    finish();
                } else {
                    // if login is not successful, show an error message
                    Toast.makeText(AdminLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            }
        });

        // Register button to navigate to the registration screen
        TextView register = findViewById(R.id.btnswapregister);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(AdminLogin.this, AdminRegister.class);
            startActivity(intent);
        });

        // Handle window insets for better layout adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
