package com.library.dao;

import com.library.table.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookLoanDAO extends BaseDAO{

    public void AddBookLoan(BookLoan loan) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_book_loans VALUES " +
                "(?, ?, ?, current_time(), date_add(current_timestamp(), INTERVAL 7 DAY), NULL) ",
                new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo()});
    }
    public void AddBookLoan(int bookId, int branchId, int cardNo) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_book_loans VALUES " +
                "(?, ?, ?, current_time(), date_add(current_timestamp(), INTERVAL 7 DAY), NULL) ",
                new Object[] {bookId, branchId, cardNo});
    }

    //UPDATE
    public void UpdateDueDate(BookLoan bookLoan, int daysFromToday) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_book_loans lns\n" +
                "SET dueDate = date_add(current_timestamp(), INTERVAL ? DAY)\n" +
                "WHERE bookId = ? AND branchId = ? AND cardNo = ?",
                new Object[] {daysFromToday, bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});
    }

    // READ
    public List<BookLoan> GetAllBookLoans() throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo ", null);
    }

    @Override
    public List<BookLoan> ExtractData(ResultSet rs) throws SQLException {
        List<BookLoan> bookLoansList = new ArrayList<>();
        while(rs.next()){
            BookLoan temp = new BookLoan();
            temp.setBookId(rs.getInt("bookId"));
            temp.setTitle(rs.getString("title"));
            temp.setCardNo(rs.getInt("cardNo"));
            temp.setBorrowerName(rs.getString("name"));
            temp.setBranchId(rs.getInt("branchId"));
            temp.setBranchName(rs.getString("branchName"));
            temp.setDateOut(rs.getString("dateOut"));
            temp.setDueDate(rs.getString("dueDate"));
            bookLoansList.add(temp);
        }
        return bookLoansList;
    }

    public void returnBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_book_loans SET dateIn = current_time() " +
                "WHERE bookId = ? AND branchId = ? AND cardNo = ?",
                new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});
    }
    public String getDueDate(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
        List<BookLoan> loanList = Read("SELECT * FROM tbl_book_loans " +
                "WHERE bookId = ? AND branchId = ? AND cardNo = ? " ,
                new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});

        if(loanList!=null){
            return loanList.get(0).getDueDate();
        }
        else
            return null;
    }

    public List<BookLoan> getBookLoansByCardNo(int cardNo) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo AND lns.cardNo = ?",
                new Object[] {cardNo});
    }
    public List<BookLoan> getBookLoansByBookId(int bookId) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo AND lns.bookId = ?",
                new Object[] {bookId});
    }
    public List<BookLoan> getBookLoansByBranchId(int branchId) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo AND lns.branchId = ? ",
                new Object[] {branchId});
    }

    public List<BookLoan> getBookLoansCheckedOut(int cardNo) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                        "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                        "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo AND " +
                        "lns.dateIn IS NULL AND lns.cardNo = ?", new Object[] {cardNo});
    }

    public Boolean checkIfLoanExists(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
        List<BookLoan> loanList = Read("SELECT * " +
                        "FROM tbl_book_loans lns, tbl_book bk, tbl_library_branch lib, tbl_borrower bor " +
                        "WHERE lns.bookId = bk.bookId AND lns.branchId = lib.branchId AND lns.cardNo = bor.cardNo " +
                        "AND lns.bookId = ? AND lns.branchId = ? AND lns.cardNo = ?",
                        new Object[] {bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo()});
        if(loanList.size() > 0)
            return true;
        else
            return false;
    }
}
