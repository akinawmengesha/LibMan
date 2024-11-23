package com.example.libman;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewReservedBook extends AppCompatActivity {

    private ReservedBookAdapter adapter;
    private List<ReservedCardItem> cardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reserved_book);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservedBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cardItemList = new ArrayList<>();
        adapter = new ReservedBookAdapter(this, cardItemList);
        recyclerView.setAdapter(adapter);


        Button home = findViewById(R.id.btnhome);
        home.setOnClickListener(v -> {
            // refresh the activity
            startActivity(new Intent(ViewReservedBook.this, AdminHome.class));
        });

        Button btnsearch = findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(v -> {
            startActivity(new Intent(ViewReservedBook.this, ViewBook.class));
        });

        Button logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(ViewReservedBook.this, SelectProfile.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapters();
    }

    private void refreshAdapters() {
        cardItemList.clear();
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getAllReservedBooks();

        if (cursor != null && cursor.moveToFirst()) {
            int bookNameIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_NAME"));
            int bookAuthorIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_AUTHOR"));
            int bookPublisherIndex = cursor.getColumnIndex(DbHelper.getBookDetails("BOOK_PUBLISHER"));
            int reservedByIndex = cursor.getColumnIndex(DbHelper.getReservedBookDetails("RESERVE_MEMBER_NAME"));

            do {
                String bookName = cursor.getString(bookNameIndex);
                String bookAuthor = cursor.getString(bookAuthorIndex);
                String bookPublisher = cursor.getString(bookPublisherIndex);
                String reservedBy = cursor.getString(reservedByIndex);

                cardItemList.add(new ReservedCardItem(bookName, bookAuthor, bookPublisher, reservedBy));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d("ViewReservedBook", "Cursor is null or empty");
        }
        dbHelper.close();
        adapter.notifyDataSetChanged();
    }

    private static class ReservedCardItem {
        String name;
        String author;
        String publisher;
        String reservedBy;

        ReservedCardItem(String name, String author, String publisher, String reservedBy) {
            this.name = name;
            this.author = author;
            this.publisher = publisher;
            this.reservedBy = reservedBy;
        }
    }

    private static class ReservedBookAdapter extends RecyclerView.Adapter<ReservedBookAdapter.ViewHolder> {
        private final List<ReservedCardItem> cardItems;
        private final Context context;

        ReservedBookAdapter(Context context, List<ReservedCardItem> cardItems) {
            this.context = context;
            this.cardItems = cardItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserved_book_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ReservedCardItem cardItem = cardItems.get(position);
            holder.ViewReservedBookName.setText(cardItem.name);
            holder.ViewReservedBookAuthor.setText(cardItem.author);
            holder.ViewReservedBookPublisher.setText(cardItem.publisher);
            holder.ViewReservedBy.setText(cardItem.reservedBy);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open BookDetailsActivity and pass book details
                    Intent intent = new Intent(context, BookDetailsActivity.class);
                    intent.putExtra("bookName", cardItem.name);
                    intent.putExtra("bookAuthor", cardItem.author);
                    intent.putExtra("bookPublisher", cardItem.publisher);
                    intent.putExtra("member", cardItem.reservedBy);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView ViewReservedBookName;
            final TextView ViewReservedBookAuthor;
            final TextView ViewReservedBookPublisher;
            final TextView ViewReservedBy;

            ViewHolder(View itemView) {
                super(itemView);
                ViewReservedBookName = itemView.findViewById(R.id.txtReservedBookName);
                ViewReservedBookAuthor = itemView.findViewById(R.id.txtReservedBookAuthor);
                ViewReservedBookPublisher = itemView.findViewById(R.id.txtReservedBookPublisher);
                ViewReservedBy = itemView.findViewById(R.id.txtReservedBy);
            }

        }
    }
}
