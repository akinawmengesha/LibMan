package com.example.libman;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditBookUser extends AppCompatActivity {

    private String bookName, bookAuthor, bookPublisher, bookQuantity;
    private EditText bookNameEdit, bookAuthorEdit, bookPublisherEdit, bookQuantityEdit;
    private TextView bookIdView;
    private DbHelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_bookuser);


        bookNameEdit = findViewById(R.id.txtupdatebookname);
        bookAuthorEdit = findViewById(R.id.txtupdatebookauthor);
        bookPublisherEdit = findViewById(R.id.txtupdatepublication);
        bookQuantityEdit = findViewById(R.id.txtupdatequantity);
        bookIdView = findViewById(R.id.txtbookid);
        dbhelper = new DbHelper(this);


        dbhelper = new DbHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookName = extras.getString("bookName");
        }
        retrieveDataFromDatabase();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void retrieveDataFromDatabase() {
        Cursor cursor = dbhelper.getBookByName(bookName);
        if (cursor != null && cursor.moveToFirst()) {
            int bookIdIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_ID"));
            int bookAuthorIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_AUTHOR"));
            int bookPublisherIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_PUBLISHER"));
            int bookQuantityIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_QUANTITY"));
            int bookAvailableIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_AVAILABLE"));

            bookIdView.setText(cursor.getString(bookIdIndex));
            bookAuthor = cursor.getString(bookAuthorIndex);
            bookPublisher= cursor.getString(bookPublisherIndex);
            bookQuantity = cursor.getString(bookQuantityIndex);

            cursor.close();
        }

        bookNameEdit.setText(bookName);
        bookAuthorEdit.setText(bookAuthor);
        bookPublisherEdit.setText(bookPublisher);
        bookQuantityEdit.setText(bookQuantity);
    }


}