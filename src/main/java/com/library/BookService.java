package com.library;


import com.library.model.Author;
import com.library.model.Book;
import com.library.util.UuidGeneratorUtil;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookService {
    Scanner scanner = new Scanner(System.in);
    private static final BookRepository repository = new BookRepository();

    public void addBook() {
        Book book = new Book(UuidGeneratorUtil.uuidGenerate());
        Author author = new Author();
        System.out.println("Add a book to Library");
        System.out.println("Title:");
        book.setTitle(scanner.nextLine());
        System.out.println("Set first name: ");
        author.setFirstName(scanner.nextLine());
        System.out.println("Set last name: ");
        author.setLastName(scanner.nextLine());
        book.setAuthor(author);
        repository.addBook(book);
    }

    private void showMyLibrary(List<Book> books) {
        IntStream.range(0, books.size())
                .forEach(index -> System.out.println((index + 1) + ". " + books.get(index).getDescription()));
/*        for (Book book : books) {
            System.out.println(++index + ". " + book.getDescription());
        }*/
    }

    public void showMyLibrary() {
        List<Book> books = repository.getBooksFromJsonFile();
        IntStream.range(0, books.size())
                .forEach(index -> System.out.println((index + 1) + ". " + books.get(index).getDescription()));
/*        for (Book book : books) {
            System.out.println(++index + ". " + book.getDescription());
        }*/
    }

    public void searchLibrary() {
        System.out.println("Title / Author Name? (t / a)");
        String getOption = scanner.nextLine();
        searchingLibrary(getOption);
    }

    private void searchingLibrary(String editOption) {
        List<Book> books = repository.getBooksFromJsonFile();
        switch (editOption) {
            case "t":
                System.out.println("Write a title:");
                break;
            case "a":
                System.out.println("Write last name of author:");
                break;
            default:
                System.out.println("Wrong input");
                searchLibrary();
                break;
        }
        String search = scanner.nextLine();
        List<Book> searchedBooks = searchingLibrary(books, search, editOption);
        showBooks(searchedBooks);
    }

    private List<Book> searchingLibrary(List<Book> books, String search, String editOption) {
        List<Book> searchedBooks = books.stream()
                .filter(book -> searchCondition(book, search, editOption))
                .collect(Collectors.toList());
        return searchedBooks;
/*        for (Book book : books) {
            if (editOption.equals("t") && book.getTitle().contains(search)
                    || editOption.equals("a") && book.getAuthor().contains(search)) {
                searchedBooks.add(book);
            }
        }*/
    }

    private boolean searchCondition(Book book, String search, String editOption) {
        return (book.getAuthor().getLastName().contains(search) && editOption.equals("a")) || (book.getTitle().contains(search) && editOption.equals("t"));
    }

    private void showBooks(List<Book> searchedBooks) {
        if (searchedBooks.isEmpty()) {
            System.out.println("No books found");
        } else {
            IntStream.range(0, searchedBooks.size())
                    .forEach(index -> System.out.println((index + 1) + ". " + searchedBooks.get(index).getDescription()));
/*            for (int i = 0; i < searchedBooks.size(); i++) {
                System.out.println((i + 1) + ". " + searchedBooks.get(i).getDescription());
            }*/
        }
    }

    public void deleteBook() {
        System.out.println("Delete book from Library");
        List<Book> books = repository.getBooksFromJsonFile();
        showMyLibrary(books);
        System.out.println("Please, choose number (book to delete):");
        int index = 0;
        while (true) {
            try {
                index = getIndex();
                Book book = books.get(index);
                repository.deleteBook(book);
                break;
            } catch (InputMismatchException | IndexOutOfBoundsException exception) {
                System.out.println("Wrong input, please choose number between 1 and " + repository.getBooksFromJsonFile().size());
                scanner.nextLine();
            }
        }
        System.out.println("Your book has been removed successfully");
    }

    public void editBook() {
        System.out.println("Edit a book in Library");
        List<Book> books = repository.getBooksFromJsonFile();
        showMyLibrary(books);
        System.out.println("Please, choose number (book to edit):");
        int index = getIndex();
        Book book = books.get(index);
        System.out.println("Do you want to change Title of a book: " + book.getTitle() + "? (y / n)");
        String option = getOption();
        editingTitle(option, index);
        System.out.println("Do you want to change Author of a book: " + book.getAuthor().getFullName() + "? (y / n)");
        option = getOption();
        editingAuthor(option, index);
    }

    private void editingTitle(String editOption, int index) {
        List<Book> books = repository.getBooksFromJsonFile();
        Book book = books.get(index);
        switch (editOption) {
            case "y":
                System.out.println("Set new Title:");
                String newTitle = scanner.nextLine();
                repository.settingNewTitle(book, newTitle);
                System.out.println("Title changed successfully");
                break;
            case "n":
                System.out.println("Title: " + book.getTitle());
                break;
        }
    }

    private void editingAuthor(String editOption, int index) {
        List<Book> books = repository.getBooksFromJsonFile();
        Book book = books.get(index);
        switch (editOption) {
            case "y":
                System.out.println("Set new first name: ");
                String newFirstname = scanner.nextLine();
                System.out.println("Set new last name: ");
                String newLastname = scanner.nextLine();
                repository.settingNewAuthor(book, new Author(newFirstname, newLastname));
                System.out.println("Author changed successfully");
                break;
            case "n":
                System.out.println("Author: " + book.getAuthor().getFullName());
                break;
        }
    }

    private String getOption() {
        String input;
        do {
            input = scanner.nextLine();
        } while (chooseYesOrNoExceptions(input));
        return input;
    }

    private boolean chooseYesOrNoExceptions(String input) {
        boolean inputInvalid = !input.matches("[y]") && !input.matches("[n]");
        if (inputInvalid) {
            System.out.println("Wrong input, choose 'y' / 'n' (yes/no)");
        }
        return inputInvalid;
    }

    private int getIndex() {
        String input;
        do {
            input = scanner.nextLine();
        } while (isInputInvalid(input));
        return Integer.parseInt(input) - 1;
    }

    private boolean isInputInvalid(String input) {
        boolean inputInvalid = !input.matches("[1-9]") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > repository.getBooksFromJsonFile().size();
        if (inputInvalid) {
            System.out.println("Wrong input, please choose number between 1 and " + repository.getBooksFromJsonFile().size());
        }
        return inputInvalid;
    }
}
