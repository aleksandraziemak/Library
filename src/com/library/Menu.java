package com.library;

import java.util.Scanner;

public class Menu {

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = 1;

        while (option != 5) {
            printMenu();
            option = scanner.nextInt();
            manageOptions(option);
        }
        System.exit(0);
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1 - Add a book to Library");
        System.out.println("2 - Edit a book in Library");
        System.out.println("3 - Delete book from Library");
        System.out.println("4 - Show the Library");
        System.out.println("5 - Exit");
        System.out.println("Choose option: ");
    }

    private static void manageOptions(int option) {
        switch (option) {
            case 1:
                System.out.println("Add a book to Library");
                break;
            case 2:
                System.out.println("Edit a book in Library");
                break;
            case 3:
                System.out.println("Delete book from Library");
                break;
            case 4:
                System.out.println("Your Library");
                break;
            default:
                System.out.println("Choose a number between 1 and 5.");
                break;
        }
    }

}
