package com.library;


import com.library.model.Author;
import com.library.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.UUID;

public class BookMapper {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String ID = "id";

    public static JSONArray mapBooks(List<Book> books) {
        JSONArray jsonBooks = new JSONArray();
        books.stream()
                .map(BookMapper::mapBook)
                .forEach(jsonBooks::add);
        return jsonBooks;
    }

    private static JSONObject mapBook(Book book) {
        JSONObject newBook = new JSONObject();
        newBook.put(TITLE, book.getTitle());
        newBook.put(ID, book.getId().toString());
        JSONObject newAuthor = new JSONObject();
        newAuthor.put(FIRST_NAME, book.getAuthor().getFirstName());
        newAuthor.put(LAST_NAME, book.getAuthor().getLastName());
        newBook.put(AUTHOR, newAuthor);
        return newBook;
    }

    public static Book mapJson(JSONObject book) {
        JSONObject jsonAuthor = (JSONObject) book.get(AUTHOR);
        Author author = new Author((String) jsonAuthor.get(FIRST_NAME), (String) jsonAuthor.get(LAST_NAME));
        return new Book((String) book.get(TITLE), author, UUID.fromString((String) book.get(ID)));
    }
}