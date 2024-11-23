package com.example.libman;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivityUser extends AppCompatActivity {

    private AutoCompleteTextView searchBookAutoComplete;
    private TextView bookDetailsTextView;
    private Spinner memberSpinner;
    private Button reserveButton;

    private DbHelper dbHelper;
    private ArrayAdapter<String> memberAdapter;
    private ArrayAdapter<String> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_user);

        searchBookAutoComplete = findViewById(R.id.searchBookAutoComplete);
        bookDetailsTextView = findViewById(R.id.bookDetailsTextView);
        reserveButton = findViewById(R.id.reserveButton);
        memberSpinner = findViewById(R.id.memberSpinner);
        dbHelper = new DbHelper(this);

            loadMemberNames();



        // Set up reserve button click listener
        reserveButton.setOnClickListener(v -> reserveBook());

        // Set up AutoCompleteTextView for book search
        setupBookAutoComplete();
    }

    private void setupBookAutoComplete() {
        List<String> bookNames = new ArrayList<>();
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bookNames);
        searchBookAutoComplete.setAdapter(bookAdapter);
        searchBookAutoComplete.setThreshold(1); // Show suggestions after typing one character

        // Set item click listener for book suggestions
        searchBookAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedBook = (String) parent.getItemAtPosition(position);
            searchBook(selectedBook);
        });

        // Set text change listener to fetch suggestions dynamically
        searchBookAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fetch book suggestions based on the typed text
                fetchBookSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchBookSuggestions(String query) {
        List<String> suggestions = dbHelper.getBookSuggestions(query);
        bookAdapter.clear();
        bookAdapter.addAll(suggestions);
        bookAdapter.notifyDataSetChanged();
    }

    private void searchBook(String bookName) {
        Cursor bookCursor = dbHelper.getBookByName(bookName);
        if (bookCursor != null && bookCursor.moveToFirst()) {
            // Book found, update book details display
            displayBookDetails(bookCursor);
        } else {
            // Book not found, show error message
            bookDetailsTextView.setText("");
            Toast.makeText(this, "Book not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMemberNames() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", "No email found");

        List<String> memberNames = new ArrayList<>();
        Cursor cursor = dbHelper.getAllMembers();
        if (cursor != null) {
            int memberNameColumnIndex = cursor.getColumnIndex(DbHelper.getMemberDetails("MEMBER_EMAIL"));
            if (memberNameColumnIndex != -1 && cursor.moveToFirst()) {
                do {
                    String memberName = cursor.getString(memberNameColumnIndex);
                    if(memberName.equals(email)) {
                        memberNames.add(email);  // Add the member name to the list if it matches the email
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        memberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, memberNames);
        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(memberAdapter);
    }

    private void displayBookDetails(Cursor bookCursor) {
        int bookIdColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_ID"));
        int authorColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_AUTHOR"));
        int publisherColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_PUBLISHER"));
        int quantityColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_QUANTITY"));

        // Check if column indexes are valid
        if (bookIdColumnIndex != -1 && authorColumnIndex != -1 && publisherColumnIndex != -1 && quantityColumnIndex != -1) {
            int bookId = bookCursor.getInt(bookIdColumnIndex);
            String bookName = searchBookAutoComplete.getText().toString().trim();
            String author = bookCursor.getString(authorColumnIndex);
            String publisher = bookCursor.getString(publisherColumnIndex);
            int quantity = bookCursor.getInt(quantityColumnIndex);

            String bookDetails = "Book Name: " + bookName + "\n\n\n" +
                    "Author: " + author + "\n\n\n" +
                    "Publisher: " + publisher + "\n\n\n" +
                    "Quantity: " + quantity;

            // Update book details display
            bookDetailsTextView.setText(bookDetails);
        } else {
            // One or more column indexes are invalid
            Toast.makeText(this, "Error retrieving book details", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("SetTextI18n")
    private void reserveBook() {
        String bookName = searchBookAutoComplete.getText().toString().trim();
        String selectedMemberName = memberSpinner.getSelectedItem().toString();

        // Check if member has already reserved a book
        Cursor memberReservationCursor = dbHelper.getReservationByMemberName(selectedMemberName);
        if (memberReservationCursor != null && memberReservationCursor.getCount() > 0) {
            // Member already has a reservation
            Toast.makeText(this, "This member has already reserved a book!", Toast.LENGTH_SHORT).show();
            return; // Stop further execution
        }

        if (bookName.isEmpty()) {
            Toast.makeText(this, "Please select a book to reserve!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedMemberName.isEmpty()) {
            Toast.makeText(this, "Your are not registered user", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get book details
        Cursor bookCursor = dbHelper.getBookByName(bookName);
        if (bookCursor != null && bookCursor.moveToFirst()) {
            int bookIdColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_ID"));
            int authorColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_AUTHOR"));
            int publisherColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_PUBLISHER"));
            int quantityColumnIndex = bookCursor.getColumnIndex(DbHelper.getBookDetails("BOOK_QUANTITY"));
            // Retrieve email from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            String email = sharedPreferences.getString("Email", "No email found");
            // Display email in a TextView
             TextView textViewSelectMemberLabel = findViewById(R.id.textViewSelectMemberLabel);
             textViewSelectMemberLabel.setText(email);
            // Check if column indexes are valid
            if (bookIdColumnIndex != -1 && authorColumnIndex != -1 && publisherColumnIndex != -1 && quantityColumnIndex != -1) {
                int bookId = bookCursor.getInt(bookIdColumnIndex);
                String author = bookCursor.getString(authorColumnIndex);
                String publisher = bookCursor.getString(publisherColumnIndex);
                int quantity = bookCursor.getInt(quantityColumnIndex);

                // Check if the quantity is greater than zero
                if (quantity > 0) {
                      if (!selectedMemberName.equals(email))
                      {
                        Toast.makeText(this, "You have no privilege to reserve!", Toast.LENGTH_SHORT).show();
                      }
                      else {

                          String bookDetails = "Book Name: " + bookName + "\n" +
                                  "Author: " + author + "\n" +
                                  "Publisher: " + publisher + "\n" +
                                  "Quantity: " + quantity;

                          // Reserve the book
                          dbHelper.reserveBook(bookId, selectedMemberName);

                          // Update book details display
                          bookDetailsTextView.setText(bookDetails);

                          // Show success message
                          Toast.makeText(this, "Book reserved successfully!", Toast.LENGTH_SHORT).show();

                      }

                } else  {
                    // Book not available for reservation
                    Toast.makeText(this, "This book is not available for reservation.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Book not found
                Toast.makeText(this, "Book not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}