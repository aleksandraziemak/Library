package com.library;

import com.library.model.Author;
import com.library.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {

    private static final String PATH_TO_JSON_FILE = "src/main/resources/Books.json";

    public void addBook(Book book) {
        List<Book> books = getBooksFromJsonFile();
        books.add(book);
        JSONArray jsonBooks = BookMapper.mapBooks(books);
        writeToJsonFile(jsonBooks);
    }

    private void writeToJsonFile(JSONArray jsonBooks) {
        try (FileWriter file = new FileWriter(PATH_TO_JSON_FILE)) {
            file.write(jsonBooks.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Book book) {
        List<Book> books = getBooksFromJsonFile();
        books.remove(book);
        JSONArray jsonBooks = BookMapper.mapBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public void settingNewTitle(Book book, String title) {
        List<Book> books = getBooksFromJsonFile();
        books.remove(book);
        book.setTitle(title);
        books.add(book);
        JSONArray jsonBooks = BookMapper.mapBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public void settingNewAuthor(Book book, Author author) {
        List<Book> books = getBooksFromJsonFile();
        books.remove(book);
        book.setAuthor(author);
        books.add(book);
        JSONArray jsonBooks = BookMapper.mapBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public List<Book> getBooksFromJsonFile() {
        ArrayList<Book> books = new ArrayList<>();
        final JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(PATH_TO_JSON_FILE);
            JSONArray booksJson = (JSONArray) parser.parse(fr);
            booksJson.forEach(object -> books.add(BookMapper.mapJson((JSONObject) object)));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return books.stream()
                .sorted(Comparator.comparing(book -> book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }
}