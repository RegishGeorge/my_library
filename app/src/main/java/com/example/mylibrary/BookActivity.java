package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookID";
    private ImageView imgBook;
    private TextView txtName, txtAuthor, txtPages, txtLongDesc;
    private Button btnCurrentlyReading, btnWishlist, btnAlreadyRead, btnFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();
        Intent intent = getIntent();
        if(intent != null) {
            int bookID = intent.getIntExtra(BOOK_ID_KEY, -1);
            if(bookID != -1) {
                Book incomingBook = Utils.getInstance(this).getBookByID(bookID);
                if(incomingBook != null) {
                    setData(incomingBook);
                    handleAlreadyRead(incomingBook);
                    handleCurrentlyReading(incomingBook);
                    handleWishlist(incomingBook);
                    handleFavourites(incomingBook);
                }
            }
        }

    }

    private void handleFavourites(final Book incomingBook) {
        ArrayList<Book> favourites = Utils.getInstance(this).getFavourites();
        boolean isInFavourites = false;
        for(Book b: favourites) {
            if(b.getId()==incomingBook.getId()) {
                isInFavourites = true;
                break;
            }
        }
        if(isInFavourites) {
            btnFavourites.setEnabled(false);
        } else {
            btnFavourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addFavourites(incomingBook)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWishlist(final Book incomingBook) {
        ArrayList<Book> wishlist = Utils.getInstance(this).getWishlist();
        boolean isInWishlist = false;
        for(Book b: wishlist) {
            if(b.getId()==incomingBook.getId()) {
                isInWishlist = true;
                break;
            }
        }
        if(isInWishlist) {
            btnWishlist.setEnabled(false);
        } else {
            btnWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addWishlist(incomingBook)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WishlistActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReading(final Book incomingBook) {
        ArrayList<Book> currentlyReading = Utils.getInstance(this).getCurrentlyReading();
        boolean isCurrentlyReading = false;
        for(Book b: currentlyReading) {
            if(b.getId()==incomingBook.getId()) {
                isCurrentlyReading = true;
                break;
            }
        }
        if(isCurrentlyReading) {
            btnCurrentlyReading.setEnabled(false);
        } else {
            btnCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addCurrentlyReading(incomingBook)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingBooksActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleAlreadyRead(final Book incomingBook) {
        ArrayList<Book> alreadyRead = Utils.getInstance(this).getAlreadyRead();
        boolean isAlreadyRead = false;
        for(Book b: alreadyRead) {
            if(b.getId()==incomingBook.getId()) {
                isAlreadyRead = true;
                break;
            }
        }
        if(isAlreadyRead) {
            btnAlreadyRead.setEnabled(false);
        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addAlreadyRead(incomingBook)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBooksActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtLongDesc.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImgURL())
                .into(imgBook);
    }

    private void initViews() {
        imgBook = findViewById(R.id.imgBook);
        txtName = findViewById(R.id.txtName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPages = findViewById(R.id.txtPages);
        txtLongDesc = findViewById(R.id.txtLongDesc);
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReading);
        btnWishlist = findViewById(R.id.btnWishlist);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnFavourites = findViewById(R.id.btnFavourites);
    }
}