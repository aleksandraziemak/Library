package com.library;


import java.util.List;
import java.util.Objects;
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

    public void showMyLibrary() {
        List<Book> books = repository.getBooks();
        int index = 0;
        for (Book book : books) {
            System.out.println(++index + ". Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }

    public void deleteBook() {
        showMyLibrary();
        System.out.println("Please, choose number (book to delete):");
        repository.deleteBook(scanner.nextInt() - 1);
        System.out.println("Your book has been removed succesfully");
    }

    public void editBook() {
        showMyLibrary();
        System.out.println("Please, choose number (book to edit):");
        int index = scanner.nextInt() - 1;
        System.out.println("Please, choose what dou you want to edit 'T' / 'A' (Title/Author)");
        String edit = scanner.next();
        if (edit.equals("T")) {
            System.out.println("Set new Title:");
            repository.getBook(index).setTitle(scanner.next());
        } else {
            System.out.println("Set new Author:");
            repository.getBook(index).setAuthor(scanner.next());
        }
    }
}
