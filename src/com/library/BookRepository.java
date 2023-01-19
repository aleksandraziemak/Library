package com.library;

import java.util.ArrayList;

public class BookRepository {
    ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

}
