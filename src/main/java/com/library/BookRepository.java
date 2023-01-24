package com.library;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void deleteBook(int index){
        books.remove(index);
    }

    public Book getBook(int index){
        return books.get(index);
    }
}
