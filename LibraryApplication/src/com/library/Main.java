package com.library;

import com.library.dao.*;
import com.library.table.*;
import com.library.service.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner myScanner = new Scanner(System.in);

    public static final String driver = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/library";
    public static final String username = "aabraha";
    public static final String password = "Aa212730!";

    public static final BorrowerDAO borrowerDAO = new BorrowerDAO();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("\nWelcome to the SS Library Management System. Which category of a user are you? (please enter the number indicate with category)");
        int input = 0;
        boolean applicationRunning = true;
        while (applicationRunning) {
            System.out.println("1) Librarian \n2) Administrator \n3) Borrower ");
            input = getIntInput(1,3);
            if(input == -99){
                System.out.println("Invalid Input");
            }

            // LIBRARIAN
            if (input == 1) {
                while(true){ // loops through LIB1 menu.

                    System.out.println("1) Enter Branch you manage  \n2) Back to Main Menu");
                    input = getIntInput(1, 2);
                    if (input == -99) {
                        System.out.println("INPUT ERROR");
                    } // exits application due to error
                    if (input == 2) {
                        input = 0;
                        break; // returns to previous menu
                    }

                    if (input == 1) {
                        //CHOOSE BRANCH
                        boolean LIB2 = true;
                        while(LIB2) {   // loops through LIB2 menu
                            LibrarianService librarian = new LibrarianService();

                            LibraryBranch branchSelected = librarian.getBranchManaged(); // gets the branch user manages

                            while(branchSelected != null){ // Loops through LIB3 menu
                                int libInput = 0;
                                System.out.println("1) Update the details of the library. \n" +
                                                   "2) Add copies of Book to the Branch \n" +
                                                   "3) Quit to previous");
                                libInput = getIntInput(1,3);
                                if(libInput == -99){
                                    System.out.println("INPUT ERROR");
                                } // exits application due to error.
                                if(libInput == 3){
                                    LIB2 = false;
                                    break;
                                } // return to previous menu.

                                // update library
                                if(libInput == 1){
                                    librarian.changeBranchDetails(branchSelected);
                                }

                                // add copies
                                if(libInput == 2){
                                    librarian.addBookCopies(branchSelected);
                                }

                            }
                            break;
                        }
                    }
                }
                input = 0;
            }

            //ADMIN
            if (input == 2) {
                System.out.println("Admin Selected");
                AdminService admin = new AdminService();
                while(true) {
                    System.out.println("What would you like to do: ");
                    System.out.println("1) Add/Update/Delete/Read Authors \n2) Add/Update/Delete/Read Books \n" +
                            "3) Add/Update/Delete/Read Genres \n4) Add/Update/Delete/Read Publishers \n" +
                            "5) Add/Update/Delete/Read Library Branches \n6) Add/Update/Delete/Read Borrowers \n" +
                            "7) Override Due Date for a book loan \n8) Return to Main Menu ");
                    input = getIntInput(1, 8);
                    if(input == 8){ break; }
                    if(input == 1){ // Authors
                        while(true) {
                            System.out.println("1) Add Author \n2) Read Author \n3) Update Author \n" +
                                    "4) Delete Author \n5) Back to Admin Menu");
                            int authorInput = getIntInput(1, 5);

                            if (authorInput == 1) admin.addAuthor();
                            if (authorInput == 2) admin.readAuthor();
                            if (authorInput == 3) admin.updateAuthor();
                            if (authorInput == 4) admin.deleteAuthor();
                            if (authorInput == 5) break;
                        }
                    }
                    if(input == 2){ // Books
                        while(true) {
                            System.out.println("1) Add Book \n2) Read Book \n3) Update Book \n" +
                                    "4) Delete Book \n5) Back to Admin Menu");
                            int bookInput = getIntInput(1, 5);

                            if (bookInput == 1) admin.addBook();
                            if (bookInput == 2) admin.readBook();
                            if (bookInput == 3) admin.updateBook();
                            if (bookInput == 4) admin.deleteBook();
                            if (bookInput == 5) break;
                        }
                    }
                    if(input == 3){ // Genres
                        while(true) {
                            System.out.println("1) Add Genre \n2) Read Genre \n3) Update Genre \n" +
                                    "4) Delete Genre \n5) Back to Admin Menu");
                            int genreInput = getIntInput(1, 5);

                            if (genreInput == 1) admin.addGenre();
                            if (genreInput == 2) admin.readGenre();
                            if (genreInput == 3) admin.updateGenre();
                            if (genreInput == 4) admin.deleteGenre();
                            if (genreInput == 5) break;
                        }
                    }
                    if(input == 4){ // Publishers
                        while(true) {
                            System.out.println("1) Add Publisher \n2) Read Publisher \n3) Update Publisher \n" +
                                    "4) Delete Publisher \n5) Back to Admin Menu");
                            int publisherInput = getIntInput(1, 5);

                            if (publisherInput == 1) admin.addPublisher();
                            if (publisherInput == 2) admin.readPublisher();
                            if (publisherInput == 3) admin.updatePublisher();
                            if (publisherInput == 4) admin.deletePublisher();
                            if (publisherInput == 5) break;
                        }
                    }
                    if(input == 5){ // Library Branches
                        while(true) {
                            System.out.println("1) Add Library Branch \n2) Read Library Branch \n3) Update Library Branch \n" +
                                    "4) Delete Library Branch \n5) Back to Admin Menu");
                            int libInput = getIntInput(1, 5);

                            if (libInput == 1) admin.addLibrary();
                            if (libInput == 2) admin.readLibrary();
                            if (libInput == 3) admin.updateLibrary();
                            if (libInput == 4) admin.deleteLibrary();
                            if (libInput == 5) break;
                        }
                    }
                    if (input == 6) {   // Borrowers
                        while(true) {
                            System.out.println("1) Add Borrower \n2) Read Borrowers \n3) Update Borrower \n" +
                                    "4) Delete Borrower \n5) Back to Admin Menu");
                            int borrInput = getIntInput(1, 5);
                            if (borrInput == 1) admin.addBorrower();
                            if (borrInput == 2) admin.readBorrower();
                            if (borrInput == 3) admin.updateBorrower();
                            if (borrInput == 4) admin.deleteBorrower();
                            if (borrInput == 5) break;
                        }
                    }
                    if (input == 7){    // Override Due Date
                        System.out.println("OVERRIDE MENU");
                        System.out.println("1) View all loans \n2) View loans by borrower \n" +
                                "3) View loans by book \n4) View loans by branch \n5) Back to admin menu");
                        int overrideInput = getIntInput(1,5);
                        // ALL LOANS
                        if(overrideInput == 1)
                            admin.overrideDueDate();

                        // BY BORROWER
                        if(overrideInput == 2)
                            admin.overrideDueDateByBorrower();

                        // BY BOOK
                        if(overrideInput == 3)
                            admin.overrideDueDateByBook();

                        // BY BRANCH
                        if(overrideInput == 4)
                            admin.overrideDueDateByBranch();
                    }
                }
            }

            //BORROWER
            if (input == 3) {
                BorrowerService borrower = new BorrowerService();
                int cardNo;

                while (true) {    // loops borrower
                    System.out.print("Please Input your card number (Enter 0 to go back): ");
                    cardNo = getIntInput(0);
                    if (cardNo == 0) {
                        break;
                    }else { // cardNo entered
                        Borrower currentBorrower = borrowerDAO.getBorrowerByCardNo(cardNo);
                        if (currentBorrower == null){
                            System.out.println("Borrower not found. ");
                        }else { // borrower found

                            while (true) {    // loop borrower list
                                System.out.printf("Welcome %s! \n", currentBorrower.getName());
                                System.out.println("1) Check out a book \n2) Return a book \n3) Quit to main menu");
                                input = getIntInput(1, 3);
                                if (input == 3)
                                    break;
                                if (input == 1) { // check out
                                    borrower.checkout(cardNo);
                                    input = 0;
                                }
                                if (input == 2) {  // return book
                                    borrower.returnBook(cardNo);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /* gets an integer input from user within the provided range */
    public static int getIntInput(int rangeMin, int rangeMax) {
        int input = 0;
        while(true) {
            try {
                input = Integer.parseInt(myScanner.nextLine());
                if(input >= rangeMin && input <= rangeMax) {
                    return input;
                }else
                    System.out.println("Enter a valid integer between "+rangeMin+" and "+rangeMax);
            } catch (Exception e) {
                e.printStackTrace();
                return -99;
            }
        }
    }
    public static int getIntInput(int minNumber) {
        int input = 0;
        while(true) {
            try {
                input = Integer.parseInt(myScanner.nextLine());
                if(input >= minNumber) {
                    return input;
                }else
                    System.out.println("Enter a valid integer over "+minNumber);
            } catch (Exception e) {
                e.printStackTrace();
                return -99;
            }
        }
    }

}
