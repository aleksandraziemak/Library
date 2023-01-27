package com.library;

import java.util.Scanner;

public class Menu {

    private final static BookService bookService = new BookService();

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        int option = 1;

        while (true) {
            printMenu();
            option = scanner.nextInt();
            manageOptions(option);
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1 - Add a book to Library");
        System.out.println("2 - Edit a book in Library");
        System.out.println("3 - Delete book from Library");
        System.out.println("4 - Show the Library");
        System.out.println("5 - Search books");
        System.out.println("0 - Exit");
        System.out.println("Choose option: ");
    }

    private static void manageOptions(int option) {
        switch (option) {
            case 1:
                bookService.addBook();
                break;
            case 2:
                bookService.editBook();
                break;
            case 3:
                bookService.deleteBook();
                break;
            case 4:
                System.out.println("Your Library");
                bookService.showMyLibrary();
                break;
            case 5:
                System.out.println("Searching in Library");
                bookService.searchLibrary();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Choose a number between 1 and 5.");
                break;
        }
    }
}
