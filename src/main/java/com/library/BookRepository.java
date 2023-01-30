package com.library;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public void addBook(Book book) {
        List<Book> books = getBooksFromJsonFile();
        books.add(book);
        JSONArray jsonBooks = jsonBooks(books);
        writeToJsonFile(jsonBooks);
    }

    private JSONArray jsonBooks (List<Book> books){
        JSONArray jsonBooks = new JSONArray();
        for (Book book: books) {
            JSONObject newBook = new JSONObject();
            newBook.put("title", book.getTitle());
            newBook.put("author", book.getAuthor());
            jsonBooks.add(newBook);
        }
        return jsonBooks;
    }

    private void writeToJsonFile(JSONArray jsonBooks) {
        try (FileWriter file = new FileWriter("src/main/resources/Books.json")) {
            file.write(jsonBooks.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int index) {
        throw new UnsupportedOperationException();
    }

    public Book getBook(int index) {
        return getBooksFromJsonFile().get(index);
    }

    public List<Book> getBooksFromJsonFile() {
        ArrayList<Book> books = new ArrayList<>();
        final JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader("src/main/resources/Books.json"); //wskazuję plik
            JSONArray booksJson = (JSONArray) parser.parse(fr); //wskazuję plik jako Json'owy plik i robię z niego Array
            for (Object object : booksJson) { //idę po kolei po wszystkich obiektach z listy booksJson
                JSONObject book = (JSONObject) object; //wskazuję, że obiekt book jest obiektem Json'owym
                books.add(new Book((String) book.get("title"), (String) book.get("author"))); //dodaję new Book do listy books, na podstawie obiektów Json'owych
/*              UWAGA! TO JEST TO SAMO TYLKO INACZEJ ZAPISANE:
                Book newBook = new Book();
                newBook.setAuthor((String)book.get("author"));
                newBook.setTitle((String)book.get("title"));
                books.add(newBook);*/
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
