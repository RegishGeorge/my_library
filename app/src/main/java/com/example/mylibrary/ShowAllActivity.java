package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ShowAllActivity extends AppCompatActivity {
    private RecyclerView booksRecView;
    private BooksRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        booksRecView = findViewById(R.id.booksRecView);
        adapter = new BooksRecViewAdapter(this, "allBooks");
        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));


        adapter.setBooks(Utils.getInstance(this).getAllBooks());

    }
}