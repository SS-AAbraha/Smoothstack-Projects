package com.library.service;

import com.library.dao.*;
import com.library.table.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BorrowerService {

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

    public void checkout(int cardNo) throws ClassNotFoundException, SQLException {
        int input = 0;

        Borrower currentBorrower = borrowerDAO.getBorrowerByCardNo(cardNo);

        // ask which branch to checkout from
        System.out.println("CHOOSE LIBRARY TO CHECKOUT FROM: ");
        List<LibraryBranch> branchesList = libraryBranchDAO.Read();  // get list of branches

        if(branchesList.size() <= 0){
            System.out.println("No libraries found");
        }else{
            int count = 1;
            for (LibraryBranch lib : branchesList) {
                System.out.println((count++) + ") " + lib.getBranchName());
            }
            System.out.println(count + ") CANCEL CHECKOUT");

            input = getIntInput(1,count);
            if(input == -99) {  // error thrown
                System.out.println("input error");
                return;
            }
            if(input == count) return;    // Cancel selected

            LibraryBranch chosenLibrary = branchesList.get(input-1);    // Chosen library branch


            // GET BOOK
            List<Book> bookList = bookDAO.getBooksByBranch(chosenLibrary); // get books available at branch

            System.out.println("SELECT BOOK TO CHECK OUT");
            count = 1;
            for (Book book : bookList) {
                System.out.println((count++) +") "+ book.getTitle() );
            }
            System.out.printf("%d) CANCEL CHECKOUT. \n", count);

            input = getIntInput(1,count);
            if(input == -99) {  // error thrown
                System.out.println("INPUT ERROR");
                return;
            }
            if(input == count) return;   // return to menu selected

            Book chosenBook = bookList.get(input-1);



            BookLoan newLoan = new BookLoan(chosenBook.getBookId(), chosenBook.getTitle(),
                    cardNo, currentBorrower.getName(), chosenLibrary.getBranchId(), chosenLibrary.getBranchName());



            if(bookLoanDAO.checkIfLoanExists(newLoan)){
                System.out.println("Checkout incomplete. You have already checked out this book. ");
                return;
            }
            if(!libraryBranchDAO.IsBookAvailable(chosenLibrary, chosenBook)){
                System.out.println("This book is currently all checked out ");
                return;
            }


            bookLoanDAO.AddBookLoan(newLoan);
            System.out.printf("Checked out: %s \n", newLoan.toString());


        }

    }

    public void returnBook(int cardNo) throws SQLException, ClassNotFoundException {
        int input=0;
        List<BookLoan> bookLoanList = bookLoanDAO.getBookLoansCheckedOut(cardNo);

        System.out.println("SELECT BOOK TO RETURN");
        int count = 1;
        for (BookLoan bookLoan : bookLoanList) {
            System.out.printf("%d) %s. Checked out from: %s. \n", count++, bookLoan.getTitle(), bookLoan.getBranchName());
        }
        System.out.printf("%d) CANCEL RETURN. \n", count );
        input = getIntInput(1,count);
        if(input == count)
            return;

        BookLoan chosenLoan = bookLoanList.get(input-1);
        bookLoanDAO.returnBookLoan(chosenLoan);
        System.out.println(chosenLoan.toString());

    }


}
