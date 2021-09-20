package com.library.service;

import com.library.dao.*;
import com.library.table.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibrarianService {

    private final BorrowerDAO borrowerDAO = new BorrowerDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final BookLoanDAO bookLoanDAO = new BookLoanDAO();
    private final LibraryBranchDAO libraryBranchDAO = new LibraryBranchDAO();

    private Scanner myScanner = new Scanner(System.in);

    /* gets an integer input from user within the provided range */
    public int getIntInput(int rangeMin, int rangeMax) {
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
    public int getIntInput(int minNumber) {
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

    public LibraryBranch getBranchManaged() throws SQLException, ClassNotFoundException {
        int input = 0;

        List<LibraryBranch> branchList = libraryBranchDAO.Read();

        int count = 1;
        for (LibraryBranch lib : branchList) {
            System.out.printf("%d) %s \n", (count++), lib.getBranchName());
        }
        System.out.printf("%d) Quit to previous \n", count);
        input = getIntInput(1,count);

        if(input == -99) {
            System.out.println("INPUT ERROR");
            return null;
        }
        if(input == count)
            return null;

        return branchList.get(input-1);

    }

    public void changeBranchDetails(LibraryBranch libraryBranch){
        System.out.printf("Now updating Branch with ID: %d and Branch Name: %s \n" +
                "Enter 'quit' at any time to cancel operation. \n" , libraryBranch.getBranchId(), libraryBranch.getBranchName());

        try {
            // Get changes
            System.out.printf("Current branch name: %s \n", libraryBranch.getBranchName());
            System.out.println("Please enter new branch name or enter 'N/A' for no change: ");
            String newName = myScanner.nextLine();
            if(newName.toLowerCase().equals("quit"))
                return;

            System.out.printf("Current branch address: %s \n", libraryBranch.getBranchAddress());
            System.out.println("Please enter new branch address or enter 'N/A' for no change: ");
            String newAddress = myScanner.nextLine();
            if(newAddress.toLowerCase().equals("quit"))
                return;

            // Make changes
            if(!newName.toUpperCase().equals("N/A"))
                libraryBranch.setBranchName(newName);

            if(!newAddress.toUpperCase().equals("N/A"))
                libraryBranch.setBranchAddress(newAddress);

            libraryBranchDAO.updateDetails(libraryBranch);
            System.out.println(libraryBranch.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addBookCopies (LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
        List<Book> bookList = bookDAO.Read();

        int count = 1;
        for (Book book : bookList) {
            System.out.printf("%d) %s by %s \n", count++, book.getTitle(), book.getAuthorName());
        }
        System.out.printf("%d) Quit operation  \n", count);
        int input = getIntInput(1,count);

        if(input == -99){
            System.out.println("INPUT ERROR");
            return;
        }
        if(input == count)
            return;

        Book chosenBook = bookList.get(input-1);


        if(libraryBranchDAO.NumberOfCopiesAtLibrary(libraryBranch, chosenBook) > 0) {   // branch already has copies
            int currentNoOfCopies = libraryBranchDAO.NumberOfCopiesAtLibrary(libraryBranch, chosenBook);
            System.out.println("Current number of copies: " + currentNoOfCopies);
            System.out.print("Enter New Number of Copies: ");
            int newNoOfCopies = getIntInput(0);

            libraryBranchDAO.updateCopies(libraryBranch.getBranchId(), chosenBook.getBookId(), newNoOfCopies);

        }else { // branch doesn't contain any copies of tho book yet
            System.out.println("Current number of copies: 0");
            System.out.print("Enter New Number of Copies: ");
            int newNoOfCopies = getIntInput(0);

            libraryBranchDAO.addBookCopies(libraryBranch.getBranchId(), chosenBook.getBookId(), newNoOfCopies);
        }
    }

}