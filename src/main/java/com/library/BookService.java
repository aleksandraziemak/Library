package com.library;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookService {
    Scanner scanner = new Scanner(System.in);
    private static final BookRepository repository = new BookRepository();

    public void addBook() {
        Book book = new Book();
        System.out.println("Add a book to Library");
        System.out.println("Title:");
        book.setTitle(scanner.nextLine());
        System.out.println("Author:");
        book.setAuthor(scanner.nextLine());
        repository.addBook(book);
    }

    public void showMyLibrary() {
        List<Book> books = repository.getBooksFromJsonFile();
        int index = 0;
        for (Book book : books) {
            System.out.println(++index + ". Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }

    public void deleteBook() {
        System.out.println("Delete book from Library");
        showMyLibrary();
        System.out.println("Please, choose number (book to delete):");
        int index = 0;
        while (true) {
            try {
                index = scanner.nextInt();
                repository.deleteBook(index - 1);
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
        showMyLibrary();
        System.out.println("Please, choose number (book to edit):");
        int index = getIndex();
        System.out.println("Do you want to change Title of a book: " + repository.getBook(index).getTitle() + "? (y / n)");
        String option = getOption();
        editingTitle(option, index);
        System.out.println("Do you want to change Author of a book: " + repository.getBook(index).getTitle() + "? (y / n)");
        option = getOption();
        editingAuthor(option, index);
    }

    private void editingTitle(String editOption, int index) {
        switch (editOption) {
            case "y":
                System.out.println("Set new Title:");
                repository.getBook(index).setTitle(scanner.nextLine());
                System.out.println("Title changed successfully");
                break;
            case "n":
                System.out.println("Title: " + repository.getBook(index).getTitle());
                break;
        }
    }

    private void editingAuthor(String editOption, int index) {
        switch (editOption) {
            case "y":
                System.out.println("Set new Author:");
                repository.getBook(index).setAuthor(scanner.nextLine());
                System.out.println("Author changed successfully");
                break;
            case "n":
                System.out.println("Author: " + repository.getBook(index).getAuthor());
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
