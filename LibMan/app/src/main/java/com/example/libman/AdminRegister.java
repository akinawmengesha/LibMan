package com.example.libman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Declare EditTexts and Button
        EditText name = findViewById(R.id.txtname);
        EditText email = findViewById(R.id.txtemail);
        EditText password = findViewById(R.id.txtpassword);
        EditText confirm_Password = findViewById(R.id.txtcomfirmpassword);
        Button Register = findViewById(R.id.btnregister);

        // Disable the Register button
        Register.setEnabled(false);
        Register.setAlpha(0.5f);  // Optionally, dim the button to show it's disabled

        // Show a message that registration is not available
        Toast.makeText(AdminRegister.this, "Registration is currently disabled.", Toast.LENGTH_SHORT).show();

        TextView Login = findViewById(R.id.btnswaplogin);
        Login.setOnClickListener(v -> {
            // Navigate to login screen
            Intent intent = new Intent(AdminRegister.this, AdminLogin.class);
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
