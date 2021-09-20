package com.library.service;

import com.library.dao.*;
import com.library.table.*;
import com.mysql.cj.xdevapi.Schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    ConnectionUtil connUtil = new ConnectionUtil();
    private final Scanner myScanner = new Scanner(System.in);
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final BorrowerDAO borrowerDAO = new BorrowerDAO();
    private final GenreDAO genreDAO = new GenreDAO();
    private final LibraryBranchDAO libraryBranchDAO = new LibraryBranchDAO();
    private final PublisherDAO publisherDAO = new PublisherDAO();
    private final BookLoanDAO bookLoanDAO = new BookLoanDAO();

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

    public void addAuthor() throws SQLException, ClassNotFoundException {
        System.out.println("Enter a new ID number for new Author: ");
        boolean uniqueAuthorID = true;
        int newAuthorID;

        // GET NEW UNIQUE ID
        do {
            uniqueAuthorID = true;
            newAuthorID = getIntInput(1);
            if (newAuthorID < 0) {
                return;
            }
            List<Author> authorList = authorDAO.Read();
            for (Author aut : authorList) {
                if (aut.getAuthorId() == newAuthorID) {
                    uniqueAuthorID = false;
                    System.out.println("Author ID already exists!");
                }
            }
        }while(!uniqueAuthorID);    // loops back if ID already exists

        // GET NAME
        System.out.println("\nEnter Author name: ");
        String newAuthorName = myScanner.nextLine();

        // SEND TO DATABASE
        Author newAuthor = new Author(newAuthorID,newAuthorName);
        authorDAO.AddAuthor(newAuthor); // add to db
        System.out.println(newAuthor.toString());   // display new author (confirmation)

    }
    public void updateAuthor() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<Author> authorList = authorDAO.Read();

        int count = 1;
        System.out.println("Choose Author to update: ");
        for (Author aut : authorList) {
            System.out.println((count++) + ") " + aut.getAuthorName()); // List all authors
        }
        System.out.println(count + ") Return to admin menu");

        input = getIntInput(1, count);  // get user's choice
        if (input == count) {
            return;
        }
        if (input == -99) {
            return;
        }

        try {
            Author chosenAuthor = authorList.get(input - 1);

            // GET UPDATE NAME
            System.out.println("Current Name: " + chosenAuthor.getAuthorName());
            System.out.println("Enter new name (enter N/A to keep current): ");
            String newName = myScanner.nextLine();

            if (!newName.toUpperCase().equals("N/A"))   // if input is not N/A
                chosenAuthor.setAuthorName(newName);

            System.out.println(chosenAuthor.toString());
            authorDAO.Update(chosenAuthor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAuthor() throws SQLException, ClassNotFoundException {
        List<Author> authorList = authorDAO.Read();

        int count = 1;

        System.out.println("Choose which author to delete:");
        for (Author aut : authorList) {
            System.out.format("%d) %s \n", count++, aut.getAuthorName());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {
            System.out.println("back to Admin menu");
            return;
        }
        authorDAO.Delete(authorList.get(input - 1));
    }
    public void readAuthor() throws SQLException, ClassNotFoundException{
        List<Author> authorList = authorDAO.Read();
        authorList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    public void addBook() throws SQLException, ClassNotFoundException {
        System.out.print("Enter an ID number for new book: ");
        boolean uniqueBookId = true;
        int newBookId;
        // GET NEW ID -- Make sure no duplicate
        do {
            uniqueBookId = true;
            newBookId = getIntInput(1);
            if (newBookId < 0) {
                return;
            }
            List<Book> bookList = bookDAO.Read();
            for (Book book : bookList) {
                if (book.getBookId() == newBookId) {
                    uniqueBookId = false;
                    System.out.println("Book ID already exists!");
                }
            }
        }while(!uniqueBookId);  // will loop back if a book already exists with given ID

        // GET TITLE
        System.out.print("Enter Book title: ");
        String newBookTitle = myScanner.nextLine();

        // GET AUTHOR
        System.out.println("Choose Book Author: ");
        List<Author> authorList = authorDAO.Read();
        int count = 1;
        for (Author aut : authorList) {
            System.out.println((count++) + ") " + aut.getAuthorName()); // list all authors
        }
        System.out.println(count + ") Cancel");

        int input = getIntInput(1, count);
        if(input == count)
            return;

        Author chosenAuth = authorList.get(input-1);

        // GET PUBLISHER
        List<Publisher> publisherList = publisherDAO.Read();
        System.out.println("Choose publisher: ");
        count = 1;
        for (Publisher pub : publisherList) {
            System.out.format("%d) %s \n", count++, pub.getPublisherName());    // list all publishers
        }
        System.out.format("%d) to Cancel \n", count);

        input = getIntInput(1, count);
        if(input == count)
            return;
        Publisher chosenPub = publisherList.get(input-1);   // set chosen publisher

        // GET GENRE
        List<Genre> genreList = genreDAO.Read();
        System.out.println("Choose Genre: ");
        input = 0;
        count = 1;
        for (Genre gen : genreList) {
            System.out.format("%d) %s \n", count++, gen.getGenreName());    // list all publishers
        }
        System.out.printf("%d) to Cancel \n", count);

        input = getIntInput(1,count);
        if(input == count)
            return;
        Genre chosenGenre = genreList.get(input-1);

        // SEND NEW BOOK TO DATABASE
        Book newBook = new Book(newBookId,newBookTitle, chosenAuth, chosenGenre, chosenPub.getPublisherId());

        newBook.toString();
        bookDAO.AddBook(newBook);

        System.out.println(newBook.toString()); // display new book info (confirmation)

    }
    public void updateBook() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<Book> bookList = bookDAO.Read();

        int count = 1;
        System.out.println("Choose Book to update: ");
        for (Book book : bookList) {
            System.out.println((count++) + ") " + book.getTitle());
        }
        System.out.println(count + ") Return to admin menu");
        input = getIntInput(1, count);
        if (input == count) return;
        if (input == -99) return;

        try {
            // SET TITLE
            Book chosenBook = bookList.get(input - 1);
            System.out.println("Current Name: " + chosenBook.getTitle());
            System.out.println("Enter new title (enter N/A to keep current): ");
            String newName = myScanner.nextLine();
            if (!newName.toUpperCase().equals("N/A"))  //  input is not N/A
                chosenBook.setTitle(newName);

            // SET AUTHOR
            List<Author> authorList = authorDAO.Read();
            System.out.println("Current Author: " + chosenBook.getAuthorName());
            System.out.format("Choose new Author (enter %d to keep current): \n", authorList.size()+1);
            count = 1;
            for (Author auth : authorList) {
                System.out.println((count++) + ") " + auth.getAuthorName());
            }
            System.out.println(count + ") TO KEEP CURRENT AUTHOR");
            int authorInput = getIntInput(1,count);
            if(authorInput != count){
                chosenBook.setAuthor(authorList.get(authorInput-1));
            }

            //SET PUBLISHER
            List<Publisher> publisherList = publisherDAO.Read();
            System.out.println("Current publisher: " + publisherDAO.getPublisherById(chosenBook.getPubId()).getPublisherName());
            System.out.format("Choose new publisher (enter %d to keep current): \n", publisherList.size()+1);
            count = 1;
            for (Publisher pub : publisherList) {
                System.out.println((count++) + ") " + pub.getPublisherName() );
            }
            System.out.println(count + ") TO KEEP CURRENT PUBLISHER");
            int pubInput = getIntInput(1,count);
            if(pubInput == -99)     return;
            if(pubInput != count){
                chosenBook.setPubId(publisherList.get(pubInput-1).getPublisherId());
            }

            //SET GENRE
            List<Genre> genreList = genreDAO.Read();
            System.out.println("Current Genre: " + chosenBook.getGenreName());
            System.out.printf("Choose new Genre (enter %d to keep current): \n", genreList.size()+1);
            count = 1;
            for (Genre gen : genreList) {
                System.out.println((count++) + ") " + gen.getGenreName());
            }
            System.out.println(count + ") TO KEEP CURRENT GENRE");
            int genreInput = getIntInput(1,count);
            if(genreInput != count){
                chosenBook.setGenre(genreList.get(genreInput-1));
                System.out.println("HERE");
            }

            // SEND TO DB
            System.out.println(chosenBook.toString());
            bookDAO.Update(chosenBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteBook() throws SQLException, ClassNotFoundException {
        List<Book> bookList = bookDAO.Read();

        int count = 1;
        System.out.println("Choose which book to delete:");
        for (Book book : bookList) {
            System.out.format("%d) %s \n", count++, book.getTitle());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {
            System.out.println("back to Admin menu");
            return;
        }
        bookDAO.Delete(bookList.get(input - 1));
        System.out.println("DELETED " + bookList.get(input-1).toString());
    }
    public void readBook() throws SQLException, ClassNotFoundException  {
        List<Book> bookList = bookDAO.Read();
        bookList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    public void addBorrower() throws SQLException, ClassNotFoundException {
        System.out.println("Enter a card number for new borrower ");
        boolean uniqueCardNo = true;
        int newCardNo;
        do {
            uniqueCardNo = true;
            newCardNo = getIntInput(1);
            if (newCardNo < 0) {
                return;
            }
            List<Borrower> borrowerList = borrowerDAO.Read();
            for (Borrower bor : borrowerList) {
                if (bor.getCardNo() == newCardNo) {
                    uniqueCardNo = false;
                    System.out.println("Card number already exists!");
                }
            }
        }while(!uniqueCardNo);

        System.out.println("\nEnter borrower name: ");
        String newBorrowerName = myScanner.nextLine();
        System.out.println("\nEnter borrower address: ");
        String newBorrowerAddress = myScanner.nextLine();
        System.out.println("\nEnter borrower phone: ");
        String newBorrowerPhone = myScanner.nextLine();

        Borrower newBorrower = new Borrower(newCardNo,newBorrowerName, newBorrowerAddress, newBorrowerPhone);
        System.out.println(newBorrower.toString());

        borrowerDAO.AddBorrower(newBorrower);

    }
    public void updateBorrower() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<Borrower> borrowerList = borrowerDAO.Read();

        int count = 1;
        System.out.println("Choose Borrower to update: ");
        for (Borrower bor : borrowerList) {
            System.out.println((count++) + ") " + bor.getName());
        }
        System.out.println(count + ") Return to admin menu");

        input = getIntInput(1, count);
        if (input == count) {
            return;
        }
        if (input == -99) {
            return;
        }

        try {
            Borrower chosenBorrower = borrowerList.get(input - 1);
            System.out.println("Current Name: " + chosenBorrower.getName());
            System.out.println("Enter new name (enter N/A to keep current): ");
            String newName = myScanner.nextLine();
            System.out.println("Current Address: " + chosenBorrower.getAddress());
            System.out.println("Enter new address (enter N/A to keep current): ");
            String newAddress = myScanner.nextLine();
            System.out.println("Current Phone: " + chosenBorrower.getPhone());
            System.out.println("Enter new phone number (enter N/A to keep current): ");
            String newPhone = myScanner.nextLine();

            if (!newName.toUpperCase().equals("N/A"))  //  input is not N/A
                chosenBorrower.setName(newName);
            if (!newAddress.toUpperCase().equals("N/A"))
                chosenBorrower.setAddress(newAddress);
            if (!newPhone.toUpperCase().equals("N/A"))
                chosenBorrower.setPhone(newPhone);

            System.out.println(chosenBorrower.toString());
            borrowerDAO.Update(chosenBorrower);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteBorrower() throws SQLException, ClassNotFoundException {
        List<Borrower> borrowerList = borrowerDAO.Read();

        int count = 1;

        System.out.println("Choose which borrower to delete:");
        for (Borrower bor : borrowerList) {
            System.out.format("%d) %s \n", count++, bor.getName());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {
            System.out.println("back to Admin menu");
            return;
        }
        borrowerDAO.Delete(borrowerList.get(input - 1));
    }
    public void readBorrower() throws SQLException, ClassNotFoundException{
        List<Borrower> borrowerList = borrowerDAO.Read();
        borrowerList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    public void addPublisher() throws SQLException, ClassNotFoundException {
        System.out.println("Enter a ID number for new publisher ");
        boolean uniquePubId = true;
        int newPubId;
        do {
            uniquePubId = true;
            newPubId = getIntInput(1);
            if (newPubId < 0) {
                return;
            }
            List<Publisher> publisherList = publisherDAO.Read();
            for (Publisher pub : publisherList) {
                if (pub.getPublisherId() == newPubId) {
                    uniquePubId = false;
                    System.out.println("Publisher ID already exists!");
                }
            }
        }while(!uniquePubId);

        System.out.println("\nEnter publisher name: ");
        String newPublisherName = myScanner.nextLine();
        System.out.println("\nEnter publisher address: ");
        String newPublisherAddress = myScanner.nextLine();
        System.out.println("\nEnter publisher phone: ");
        String newPublisherPhone = myScanner.nextLine();

        Publisher newPublisher = new Publisher(newPubId,newPublisherName, newPublisherAddress, newPublisherPhone);
        System.out.println(newPublisher.toString());

        publisherDAO.AddPublisher(newPublisher);

    }
    public void updatePublisher() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<Publisher> publisherList = publisherDAO.Read();

        int count = 1;
        System.out.println("Choose publisher to update: ");
        for (Publisher pub : publisherList) {
            System.out.println((count++) + ") " + pub.getPublisherName());
        }
        System.out.println(count + ") Return to admin menu");

        input = getIntInput(1, count);
        if (input == count) {
            return;
        }
        if (input == -99) {
            return;
        }

        try {
            Publisher chosenPublisher = publisherList.get(input - 1);
            // Name
            System.out.println("Current Name: " + chosenPublisher.getPublisherName());
            System.out.println("Enter new name (enter N/A to keep current): ");
            String newName = myScanner.nextLine();
            // Address
            System.out.println("Current Address: " + chosenPublisher.getPublisherAddress());
            System.out.println("Enter new address (enter N/A to keep current): ");
            String newAddress = myScanner.nextLine();
            // Phone
            System.out.println("Current Phone: " + chosenPublisher.getPublisherPhone());
            System.out.println("Enter new publisher phone number (enter N/A to keep current): ");
            String newPhone = myScanner.nextLine();

            if (!newName.toUpperCase().equals("N/A"))  //  input is not N/A
                chosenPublisher.setPublisherName(newName);
            if (!newAddress.toUpperCase().equals("N/A"))
                chosenPublisher.setPublisherAddress(newAddress);
            if (!newPhone.toUpperCase().equals("N/A"))
                chosenPublisher.setPublisherPhone(newPhone);

            System.out.println(chosenPublisher.toString());
            publisherDAO.Update(chosenPublisher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletePublisher() throws SQLException, ClassNotFoundException {
        List<Publisher> publisherList = publisherDAO.Read();

        int count = 1;

        System.out.println("Choose which publisher to delete:");
        for (Publisher pub : publisherList) {
            System.out.format("%d) %s \n", count++, pub.getPublisherName());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {
            System.out.println("back to Admin menu");
            return;
        }
        publisherDAO.Delete(publisherList.get(input - 1));
    }
    public void readPublisher() throws SQLException, ClassNotFoundException{
        List<Publisher> publisherList = publisherDAO.Read();
        publisherList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    public void addLibrary() throws SQLException, ClassNotFoundException {
        boolean uniqueLibId = true; // determines if given Id is unique
        int newLibId;
        do {
            System.out.print("Enter a ID number for new Library Branch: ");
            uniqueLibId = true;
            newLibId  = getIntInput(1);
            if (newLibId < 0) {
                return;
            }
            List<LibraryBranch> libraryList = libraryBranchDAO.Read();
            for (LibraryBranch lib : libraryList) {
                if (lib.getBranchId() == newLibId) {
                    uniqueLibId = false;    //  flags false if ID is already in use
                    System.out.println("A Library with that ID already exists. ");
                }
            }
        }while(!uniqueLibId);   // loops if id is already used.

        System.out.print("Enter branch name: ");
        String newLibraryName = myScanner.nextLine();
        System.out.print("Enter branch address: ");
        String newLibraryAddress = myScanner.nextLine();

        LibraryBranch newLibrary = new LibraryBranch(newLibId, newLibraryName, newLibraryAddress);
        System.out.println(newLibrary.toString());

        libraryBranchDAO.AddBranch(newLibrary);

    }
    public void updateLibrary() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<LibraryBranch> libraryList = libraryBranchDAO.Read();

        int count = 1;
        System.out.println("Choose Library to update: ");
        for (LibraryBranch lib : libraryList) {
            System.out.println((count++) + ") " + lib.getBranchName());
        }
        System.out.println(count + ") Cancel Update");

        input = getIntInput(1, count);
        if (input == count) {
            return;
        }
        if (input == -99) {
            return;
        }

        try {
            LibraryBranch chosenLibrary = libraryList.get(input - 1);
            // GET NEW NAME
            System.out.println("Current Name: " + chosenLibrary.getBranchName());
            System.out.print("Enter new library name (enter N/A to keep current): ");
            String newName = myScanner.nextLine();
            // GET NEW ADDRESS
            System.out.println("Current Address: " + chosenLibrary.getBranchAddress());
            System.out.print("Enter new address (enter N/A to keep current): ");
            String newAddress = myScanner.nextLine();

            if (!newName.toUpperCase().equals("N/A"))  //  input is not 'N/A'
                chosenLibrary.setBranchName(newName);
            if (!newAddress.toUpperCase().equals("N/A"))    // input is not 'N/A'
                chosenLibrary.setBranchAddress(newAddress);

            System.out.println(chosenLibrary.toString());
            libraryBranchDAO.Update(chosenLibrary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteLibrary() throws SQLException, ClassNotFoundException {
        List<LibraryBranch> libraryList = libraryBranchDAO.Read();

        int count = 1;

        System.out.println("Choose which library branch to delete:");
        for (LibraryBranch lib : libraryList) {
            System.out.format("%d) %s \n", count++, lib.getBranchName());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);  // user input
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {   // user chose cancel
            System.out.println("back to Admin menu");
            return;
        }
        libraryBranchDAO.Delete(libraryList.get(input - 1));
    }
    public void readLibrary() throws SQLException, ClassNotFoundException{
        List<LibraryBranch> libraryList = libraryBranchDAO.Read();
        libraryList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    public void addGenre() throws SQLException, ClassNotFoundException {
        boolean uniqueGenreID = true;
        int newGenreID;

        // GET NEW UNIQUE ID
        do {
            System.out.print("Enter a new ID number for new genre: ");
            uniqueGenreID = true;
            newGenreID = getIntInput(1);
            if (newGenreID < 1) {
                return;
            }
            List<Genre> genreList = genreDAO.Read();
            for (Genre genre : genreList) {
                if (genre.getGenreId() == newGenreID) {
                    uniqueGenreID = false;
                    System.out.println("genre already exists with this ID. ");
                }
            }
        }while(!uniqueGenreID);    // loops back if ID already exists

        // GET NAME
        try {
            System.out.println("\nEnter Genre name: ");
            String newGenreName = myScanner.nextLine();

            // SEND TO DATABASE
            Genre newGenre = new Genre(newGenreID, newGenreName);
            genreDAO.AddGenre(newGenre); // add to db
            System.out.println(newGenre.toString());   // display new genre (confirmation)
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void updateGenre() throws SQLException, ClassNotFoundException {
        int input = 0;
        List<Genre> genreList = genreDAO.Read();

        int count = 1;
        System.out.println("Choose genre to update: ");
        for (Genre gen : genreList) {
            System.out.println((count++) + ") " + gen.getGenreName()); // List all genres
        }
        System.out.println(count + ") Return to admin menu");

        input = getIntInput(1, count);  // get user's choice
        if (input == count) {
            return;
        }
        if (input == -99) {
            return;
        }

        try {
            Genre chosenGenre = genreList.get(input - 1);

            // GET UPDATE NAME
            System.out.println("Current genre name: " + chosenGenre.getGenreName());
            System.out.println("Enter new name (enter N/A to keep current): ");
            String newGenreName = myScanner.nextLine();

            if (!newGenreName.toUpperCase().equals("N/A"))   // if input is not N/A
                chosenGenre.setGenreName(newGenreName);

            System.out.println(chosenGenre.toString());
            genreDAO.Update(chosenGenre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteGenre() throws SQLException, ClassNotFoundException {
        List<Genre> genreList = genreDAO.Read();

        int count = 1;

        System.out.println("Choose which genre to delete:");
        for (Genre gen : genreList) {
            System.out.format("%d) %s \n", count++, gen.getGenreName());
        }
        System.out.format("%d) to Cancel \n", count);

        int input = getIntInput(1, count);
        if (input == -99) {
            System.out.println("INPUT ERROR");
            return;
        }
        if (input == count) {
            System.out.println("back to Admin menu");
            return;
        }
        genreDAO.Delete(genreList.get(input - 1));
    }
    public void readGenre() throws SQLException, ClassNotFoundException{
        List<Genre> genreList = genreDAO.Read();
        genreList.forEach(x -> System.out.println(x.toString()));
        System.out.println();
    }

    // sets new due date of book by the given number of days from current date
    public void overrideDueDate() throws SQLException, ClassNotFoundException {
        // GET BOOK LOANS
        int input = 0;
        List<BookLoan> loansList = bookLoanDAO.GetAllBookLoans();

        System.out.println("Select the loan you would like to update: ");
        int count = 1;
        for (BookLoan loan : loansList) {
            System.out.printf("%d) book: %s || borrower: %s || branch: %s \n",
                    (count++), loan.getTitle(), loan.getBorrowerName(), loan.getBranchName() );
        }
        System.out.println(count + ") TO CANCEL");
        input = getIntInput(1,count);
//        if(input == count) return;
        if(input != count){
            BookLoan chosenLoan = loansList.get(input-1);
            System.out.println(chosenLoan.toString());
            System.out.println("CURRENT DUE DATE: " + chosenLoan.getDueDate());
            System.out.println("Enter number of days from today to set new due date");
            int daysFromNow = getIntInput(0);
            bookLoanDAO.UpdateDueDate(chosenLoan,daysFromNow);
        }
    }
    public void overrideDueDateByBorrower() throws SQLException, ClassNotFoundException {
        int input = 0;
        int count = 0;
        int cardNo = 0;

        while(true) {   // loops until Borrower is found
            System.out.println("1) Enter borrower Card Number \n2) Choose From Borrower List \n3) Return to override menu");
            input = getIntInput(1, 3);
            if (input == 3)  // return selected
                return;
            if (input == 1) {
                System.out.print("Enter borrower Card Number: ");
                cardNo = getIntInput(1);
                if (borrowerDAO.getBorrowerByCardNo(cardNo) != null) {
                    break;
                }
            }
            if (input == 2) {
                List<Borrower> borrowerList = borrowerDAO.Read();
                count = 0;
                for (Borrower bor : borrowerList) {
                    System.out.println((++count) + ") " + bor.getName());
                }
                cardNo = borrowerList.get(getIntInput(1, count)-1).getCardNo();
                break;
            }
        }

        // GET BOOK LOANS
        List<BookLoan> loansList = bookLoanDAO.getBookLoansByCardNo(cardNo);

        if(loansList.size() <= 0) {
            System.out.println("No Loans for this borrower");
            return;
        }

        System.out.println("Select the loan you would like to update: ");
        count = 1;
        for (BookLoan loan : loansList) {
            System.out.printf("%d) book: %s || borrower: %s || branch: %s \n",
                    (count++), loan.getTitle(), loan.getBorrowerName(), loan.getBranchName() );
        }
        System.out.println(count + ") TO CANCEL");
        input = getIntInput(1,count);
//        if(input == count) return;
        if(input != count){
            BookLoan chosenLoan = loansList.get(input-1);
            System.out.println(chosenLoan.toString());
            System.out.println("CURRENT DUE DATE: " + chosenLoan.getDueDate());
            System.out.println("Enter number of days from today to set new due date");
            int daysFromNow = getIntInput(0);
            bookLoanDAO.UpdateDueDate(chosenLoan,daysFromNow);
        }
    }
    public void overrideDueDateByBook() throws SQLException, ClassNotFoundException {
        int input = 0;
        int count = 0;
        int bookId = 0;
        while(true) {
            System.out.println("1) Enter Book Card Number \n2) Choose From Book List \n3) Return ");
            input = getIntInput(1, 3);
            if (input == 3)  // return selected
                return;
            if (input == 1) {
                System.out.print("Enter Book ID: ");
                bookId = getIntInput(1);    // get input
                if (bookDAO.getBookById(bookId) == null)  // if book not found
                    System.out.println("Book not found. ");
                else
                    break;

            }
            if (input == 2) {
                List<Book> bookList = bookDAO.Read();
                count = 0;
                for (Book book : bookList) {
                    System.out.println((++count) + ") " + book.getTitle());
                }
                bookId = bookList.get(getIntInput(1, count)-1).getBookId();
                break;
            }
        }

        // GET BOOK LOANS
        List<BookLoan> loansList = bookLoanDAO.getBookLoansByBookId(bookId);

        if(loansList.size() <= 0) {
            System.out.println("No Loans for this book");
            return;
        }

        System.out.println("Select the loan you would like to update: ");
        count = 1;
        for (BookLoan loan : loansList) {
            System.out.printf("%d) book: %s || borrower: %s || branch: %s \n",
                    (count++), loan.getTitle(), loan.getBorrowerName(), loan.getBranchName() );
        }
        System.out.println(count + ") TO CANCEL");
        input = getIntInput(1,count);
//        if(input == count) return;
        if(input != count){
            BookLoan chosenLoan = loansList.get(input-1);
            System.out.println(chosenLoan.toString());
            System.out.println("CURRENT DUE DATE: " + chosenLoan.getDueDate());
            System.out.println("Enter number of days from today to set new due date");
            int daysFromNow = getIntInput(0);
            bookLoanDAO.UpdateDueDate(chosenLoan,daysFromNow);
        }
    }
    public void overrideDueDateByBranch() throws SQLException, ClassNotFoundException {
        int input = 0;
        int count = 0;
        int branchId = 0;
        while(true) {
            System.out.println("1) Enter Branch ID \n2) Choose From branch List \n3) Return to override menu ");
            input = getIntInput(1, 3);
            if (input == 3)  // return selected
                return;
            if (input == 1) {
                System.out.print("Enter Branch ID: ");
                branchId = getIntInput(1);
                if(libraryBranchDAO.getBranchByID(branchId) == null)
                    System.out.println("Library branch not found");
                else
                    break;

            }
            if (input == 2) {
                List<LibraryBranch> branchList = libraryBranchDAO.Read();
                count = 0;
                for (LibraryBranch lib : branchList) {
                    System.out.println((++count) + ") " + lib.getBranchName());
                }
                branchId = branchList.get(getIntInput(1, count)-1).getBranchId();
                break;
            }

        }

        // GET BOOK LOANS
        List<BookLoan> loansList = bookLoanDAO.getBookLoansByBranchId(branchId);

        if(loansList.size() <= 0) {
            System.out.println("No Loans for this borrower");
            return;
        }

        System.out.println("Select the loan you would like to update: ");
        count = 1;
        for (BookLoan loan : loansList) {
            System.out.printf("%d) book: %s || borrower: %s || branch: %s \n",
                    (count++), loan.getTitle(), loan.getBorrowerName(), loan.getBranchName() );
        }
        System.out.println(count + ") TO CANCEL");
        input = getIntInput(1,count);
//        if(input == count) return;
        if(input != count){
            BookLoan chosenLoan = loansList.get(input-1);
            System.out.println(chosenLoan.toString());
            System.out.println("CURRENT DUE DATE: " + chosenLoan.getDueDate());
            System.out.println("Enter number of days from today to set new due date");
            int daysFromNow = getIntInput(0);
            bookLoanDAO.UpdateDueDate(chosenLoan,daysFromNow);
        }
    }

}
