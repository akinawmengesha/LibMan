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

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);

        // Declare EditTexts and Button
        //EditText email = findViewById(R.id.txtloginemail);
        EditText name = findViewById(R.id.txtloginemail);
        EditText password = findViewById(R.id.txtloginpassword);
        Button Login = findViewById(R.id.btnlogin2);

        Login.setOnClickListener(v -> {
            // Get the text entered by the user in the EditTexts
            String Email = name.getText().toString().trim();
            String Password = password.getText().toString().trim();


            //check if email and password are not empty
            if(!Email.isEmpty() && !Password.isEmpty()){
                try (DbHelper dbHelper = new DbHelper(UserLogin.this)) {
                    //pass email and password to the dbhelper
                    if(dbHelper.loginUser(Email, Password)){
                        // Store the logged-in user's name in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Email", Email);
                        editor.apply();
                        Toast.makeText(UserLogin.this, "Login successful !.", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(UserLogin.this, UserHome.class);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        // if login is not successful, show an error message
                        Toast.makeText(UserLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Log.e("LoginActivity", "DB Error occurred", e);
                }
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            }
        });


        TextView Register = findViewById(R.id.btnswapregister);
        Register.setOnClickListener(v -> {
            Intent intent = new Intent(UserLogin.this, UserRegister.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}