package com.example.libman;

import android.database.Cursor;
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

public class UserViewMember extends AppCompatActivity {

    private String memberName, memberEmail, memberPhone, memberAddress;
    private EditText memberNameEdit, memberEmailEdit, memberPhoneEdit, memberAddressEdit;
    private TextView memberIdView;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_view_member);

        memberNameEdit = findViewById(R.id.txtupdatemembername);
        memberEmailEdit = findViewById(R.id.txtupdatememberemail);
        memberPhoneEdit = findViewById(R.id.txtupdatememberphone);
        memberAddressEdit = findViewById(R.id.txtupdatememberaddress);
        memberIdView = findViewById(R.id.txtmemberid);
        dbHelper = new DbHelper(this);


        dbHelper = new DbHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            memberName = extras.getString("memberName");
        }
        retrieveDataFromDatabase();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void retrieveDataFromDatabase() {
        Cursor cursor = dbHelper.getMemberByName(memberName);
        if (cursor != null && cursor.moveToFirst()) {
            int memberIdIndex = cursor.getColumnIndex(DbHelper.getMemberDetails("MEMBER_ID"));
            int memberEmailIndex = cursor.getColumnIndex(DbHelper.getMemberDetails("MEMBER_EMAIL"));
            int memberPhoneIndex = cursor.getColumnIndex(DbHelper.getMemberDetails("MEMBER_PHONE"));
            int memberAddressIndex = cursor.getColumnIndex(DbHelper.getMemberDetails("MEMBER_ADDRESS"));

            memberIdView.setText(cursor.getString(memberIdIndex));
            memberEmail = cursor.getString(memberEmailIndex);
            memberPhone = cursor.getString(memberPhoneIndex);
            memberAddress = cursor.getString(memberAddressIndex);

            cursor.close();
        }

        memberNameEdit.setText(memberName);
        memberEmailEdit.setText(memberEmail);
        memberPhoneEdit.setText(memberPhone);
        memberAddressEdit.setText(memberAddress);
    }



    private void clearFields() {
        memberNameEdit.setText("");
        memberEmailEdit.setText("");
        memberPhoneEdit.setText("");
        memberAddressEdit.setText("");
    }
}
