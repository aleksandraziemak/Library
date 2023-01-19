package com.library;


import java.util.List;
import java.util.Scanner;

public class BookService {
    Scanner scanner = new Scanner(System.in);
    private static final BookRepository repository = new BookRepository();

    public void addBook() {
        Book book = new Book();
        System.out.println("Title:");
        book.setTitle(scanner.nextLine());
        System.out.println("Author:");
        book.setAuthor(scanner.nextLine());
        repository.addBook(book);
    }
    public void showMyLibrary(){
        List<Book> books = repository.getBooks();
        int index = 0;
        for (Book book: books) {
            System.out.println(++index + ". Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }
}
