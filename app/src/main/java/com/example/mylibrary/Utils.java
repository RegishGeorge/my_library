package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String CURRENTLY_READING_KEY = "currently_reading";
    private static final String ALREADY_READ_KEY = "already_read";
    private static final String WISHLIST_KEY = "wishlist";
    private static final String FAVOURITES_KEY = "favourites";

    public Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if(getAllBooks() == null) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getCurrentlyReading() == null) {
            editor.putString(CURRENTLY_READING_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(getAlreadyRead() == null) {
            editor.putString(ALREADY_READ_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(getWishlist() == null) {
            editor.putString(WISHLIST_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(getFavourites() == null) {
            editor.putString(FAVOURITES_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(100, "Don Quixote", "Miguel De Cervantes", 863, "https://images-na.ssl-images-amazon.com/images/I/81pFndfXT2L.jpg",
                "The story of the gentle knight and his servant Sancho Panza has entranced readers for centuries.", "Long Description"));
        books.add(new Book(101, "Pilgrim's Progress", "John Bunyan", 191, "https://images-na.ssl-images-amazon.com/images/I/91FXl+Ys3gL.jpg",
                "The one with the Slough of Despond and Vanity Fair.", "Long Description"));
        books.add(new Book(102, "Robinson Crusoe", "Daniel Defoe", 198, "https://images-na.ssl-images-amazon.com/images/I/81N04CQ76LL.jpg",
                "The first English novel.", "Long Description"));
        books.add(new Book(103, "Gulliver's Travels", "Jonathan Swift", 352, "https://images-na.ssl-images-amazon.com/images/I/810EXPO3uBL.jpg",
                "A wonderful satire that still works for all ages, despite the savagery of Swift's vision.", "Long Description"));
        books.add(new Book(104, "Tom Jones", "Henry Fielding", 700, "https://images-na.ssl-images-amazon.com/images/I/61azV0qOEcL.jpg",
                "The adventures of a high-spirited orphan boy: an unbeatable plot and a lot of sex ending in a blissful marriage.", "Long Description"));
        books.add(new Book(105, "Clarissa", "Samuel Richardson", 1534, "https://m.media-amazon.com/images/I/51WpnISEo-L.jpg",
                "One of the longest novels in the English language, but unputdownable.", "Long Description"));
        books.add(new Book(106, "Tristram Shandy", "Laurence Sterne", 600, "https://m.media-amazon.com/images/I/41EwkQGeDiL.jpg",
                "One of the first bestsellers, dismissed by Dr Johnson as too fashionable for its own good.", "Long Description"));
        books.add(new Book(107, "Dangerous Liaisons", "Pierre Choderlos De Laclos", 400, "https://m.media-amazon.com/images/I/41pWJYyUERL.jpg",
                "An epistolary novel and a handbook for seducers: foppish, French, and ferocious.", "Long Description"));
        books.add(new Book(108, "Emma", "Jane Austen", 544, "https://images-na.ssl-images-amazon.com/images/I/51ibuPcSgOL._SX311_BO1,204,203,200_.jpg",
                "Near impossible choice between this and Pride and Prejudice. But Emma never fails to fascinate and annoy.", "Long Description"));
        books.add(new Book(109, "Frankenstein", "Mary Shelley", 280, "https://m.media-amazon.com/images/I/511hI+jTvOL.jpg",
                "Inspired by spending too much time with Shelley and Byron.", "Long Description"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if(null == instance) {
            instance = new Utils(context);
            return instance;
        } else {
            return instance;
        }
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReading() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyRead() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getWishlist() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WISHLIST_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getFavourites() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITES_KEY, null), type);
        return books;
    }

    public Book getBookByID(int id) {
        ArrayList<Book> books = getAllBooks();
        if(books != null) {
            for(Book b: books) {
                if(b.getId()==id) {
                    return b;
                }
            }
        }
        return null;
    }

    public boolean addAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyRead();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_KEY);
                editor.putString(ALREADY_READ_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReading();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_KEY);
                editor.putString(CURRENTLY_READING_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addWishlist(Book book) {
        ArrayList<Book> books = getWishlist();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WISHLIST_KEY);
                editor.putString(WISHLIST_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addFavourites(Book book) {
        ArrayList<Book> books = getFavourites();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITES_KEY);
                editor.putString(FAVOURITES_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyRead();
        if(books != null) {
            for(Book b: books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_KEY);
                        editor.putString(ALREADY_READ_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReading();
        if(books != null) {
            for(Book b: books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_KEY);
                        editor.putString(CURRENTLY_READING_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeWishlist(Book book) {
        ArrayList<Book> books = getWishlist();
        if(books != null) {
            for(Book b: books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WISHLIST_KEY);
                        editor.putString(WISHLIST_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFavourites(Book book) {
        ArrayList<Book> books = getFavourites();
        if(books != null) {
            for(Book b: books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITES_KEY);
                        editor.putString(FAVOURITES_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
