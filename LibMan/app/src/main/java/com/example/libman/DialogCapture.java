package com.example.libman;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DialogCapture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make appear dialog for admin and login Page
        AdminOrUser selectSingleOptions = new AdminOrUser();
        selectSingleOptions.show(getSupportFragmentManager(),"select_single_options");
    }
}
