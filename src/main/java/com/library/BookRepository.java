package com.library;

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

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String PATH_TO_JSON_FILE = "src/main/resources/Books.json";

    public void addBook(Book book) {
        List<Book> books = getBooksFromJsonFile();
        books.add(book);
        JSONArray jsonBooks = jsonBooks(books);
        writeToJsonFile(jsonBooks);
    }

    private JSONArray jsonBooks(List<Book> books) {
        JSONArray jsonBooks = new JSONArray();
        books.stream()
                .map(book -> {
                    JSONObject newBook = new JSONObject();
                    newBook.put(TITLE, book.getTitle());
                    JSONObject newAuthor = new JSONObject();
                    newAuthor.put(FIRST_NAME, book.getAuthor().getFirstName());
                    newAuthor.put(LAST_NAME, book.getAuthor().getLastName());
                    newBook.put(AUTHOR, newAuthor);
                    return newBook;
                })
                .forEach(jsonObj -> jsonBooks.add(jsonObj));
        return jsonBooks;
/*        for (Book book: books) {
            JSONObject newBook = new JSONObject();
            newBook.put("title", book.getTitle());
            newBook.put("author", book.getAuthor());
            jsonBooks.add(newBook);
        }*/
    }

    private void writeToJsonFile(JSONArray jsonBooks) {
        try (FileWriter file = new FileWriter(PATH_TO_JSON_FILE)) {
            file.write(jsonBooks.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int index) {
        List<Book> books = getBooksFromJsonFile();
        books.remove(index);
        JSONArray jsonBooks = jsonBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public void settingNewTitle(int index, String title) {
        List<Book> books = getBooksFromJsonFile();
        books.get(index).setTitle(title);
        JSONArray jsonBooks = jsonBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public void settingNewAuthor(int index, Author author) {
        List<Book> books = getBooksFromJsonFile();
        books.get(index).setAuthor(author);
        JSONArray jsonBooks = jsonBooks(books);
        writeToJsonFile(jsonBooks);
    }

    public Book getBook(int index) {
        return getBooksFromJsonFile().get(index);
    }

    public List<Book> getBooksFromJsonFile() {
        ArrayList<Book> books = new ArrayList<>();
        final JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(PATH_TO_JSON_FILE); //wskazuję plik
            JSONArray booksJson = (JSONArray) parser.parse(fr); //wskazuję plik jako Json'owy plik i robię z niego Array
            for (Object object : booksJson) { //idę po kolei po wszystkich obiektach z listy booksJson
                JSONObject book = (JSONObject) object; //wskazuję, że obiekt book jest obiektem Json'owym
                JSONObject jsonAuthor = (JSONObject) book.get(AUTHOR);
                Author author = new Author((String) jsonAuthor.get(FIRST_NAME), (String) jsonAuthor.get(LAST_NAME));
                books.add(new Book((String) book.get(TITLE), author)); //dodaję new Book do listy books, na podstawie obiektów Json'owych
/*              UWAGA! TO JEST TO SAMO TYLKO INACZEJ ZAPISANE:
                Book newBook = new Book();
                newBook.setAuthor((String)book.get("author"));
                newBook.setTitle((String)book.get("title"));
                books.add(newBook);*/
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return books.stream()
                .sorted(Comparator.comparing(book -> book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }
}
