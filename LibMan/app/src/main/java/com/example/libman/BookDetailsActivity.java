package com.example.libman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    private String bookName;
    private String membername;
    private String bookAuthor;
    private String bookPublisher;
    private String member; // User who has reserved the book
    private int bookId; // Book's unique identifier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Get book details from intent
        Intent intent = getIntent();
        bookName = intent.getStringExtra("bookName");
        bookAuthor = intent.getStringExtra("bookAuthor");
        bookPublisher = intent.getStringExtra("bookPublisher");
        member = intent.getStringExtra("member");

        // Display book details
        TextView txtBookName = findViewById(R.id.txtBookName);
        TextView txtBookAuthor = findViewById(R.id.txtBookAuthor);
        TextView txtBookPublisher = findViewById(R.id.txtBookPublisher);
        TextView txtMember = findViewById(R.id.memberSpinner2);

        txtBookName.setText(bookName);
        txtBookAuthor.setText(bookAuthor);
        txtBookPublisher.setText(bookPublisher);
        txtMember.setText(member);

        // Retrieve bookId from the database based on the bookName
        getBookIdByName(bookName);

        // Button for returning the book
        Button btnReturnBook = findViewById(R.id.btnReturnBook);
        btnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle return book action
                returnBook();
            }
        });
    }

    // Retrieve the bookId from the database using the bookName
    private void getBookIdByName(String bookName) {
        try (DbHelper dbHelper = new DbHelper(this)) {
            // Query the database for the book's ID based on the bookName
            Cursor cursor = dbHelper.getBookByName(bookName);
            if (cursor != null && cursor.moveToFirst()) {
                int bookIdColumnIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_ID"));
                if (bookIdColumnIndex != -1) {
                    bookId = cursor.getInt(bookIdColumnIndex); // Store the bookId
                }
            }
        } catch (Exception e) {
            Log.e("BookDetailsActivity", "Error occurred while retrieving book ID", e);
        }
    }
    private void returnBook() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", "No email found");
        String selectedMemberName = member;
        try(DbHelper dbHelper = new DbHelper(this)){

            if (!selectedMemberName.equals(email))
            {
                if (email.equals("admin@gmail.com")) {
                    dbHelper.returnBook(member);
                    Toast.makeText(this, "Book returned successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(this, "You don't have privilege to return", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                    dbHelper.returnBook(member);
                    Toast.makeText(this, "Book returned successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        catch (Exception e) {
            Log.e("ReturnActivity", "Error occurred while deleting member", e);
            Toast.makeText(this, "Error Return book", Toast.LENGTH_SHORT).show();
        }
    }

}
