package com.library.dao;

import com.library.table.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{

    // CREATE
    public void AddBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_library_branch VALUES " +
                "(?, ?, ?)", new Object[] {branch.getBranchId(), branch.getBranchName(), branch.getBranchAddress()});
    }
    // UPDATE
    public void Update(LibraryBranch branch) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_library_branch " +
                "SET branchName = ? , branchAddress = ? " +
                "WHERE branchId = ?", new Object[] {branch.getBranchName(),branch.getBranchAddress(),branch.getBranchId()});
    }
    // DELETE
    public void Delete(LibraryBranch branch) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] {branch.getBranchId()});
    }

    // READ
    public List<LibraryBranch> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * FROM tbl_library_branch", null);
    }

    @Override
    public List<LibraryBranch> ExtractData(ResultSet rs) throws SQLException {
        List<LibraryBranch> branchList = new ArrayList<>();
        while(rs.next()){
            LibraryBranch temp = new LibraryBranch();
            temp.setBranchId(rs.getInt("branchId"));
            temp.setBranchName(rs.getString("branchName"));
            temp.setBranchAddress(rs.getString("branchAddress"));
            branchList.add(temp);
        }
        return branchList;
    }

    public LibraryBranch getBranchByID(int branchId) throws SQLException, ClassNotFoundException {
        List<LibraryBranch> branchList = Read("SELECT * FROM tbl_library_branch WHERE branchId = ? ",
                new Object[] {branchId});

        if(branchList.size() <= 0)
            return null;
        else
            return branchList.get(0);

    }

    public boolean IsBookAvailable(LibraryBranch branch, Book book) throws SQLException, ClassNotFoundException {
        int copiesAvailable = NumberOfCopiesAtLibrary(branch,book) - NumberOfCopiesCheckedOut(branch, book);

        if(copiesAvailable > 0)
            return true;
        else
            return false;
    }

    public int NumberOfCopiesAtLibrary(LibraryBranch branch, Book book) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * " +
                "FROM tbl_book_copies bk_cp "+
                "WHERE branchId = ? AND bookId = ?");
        pstmt.setInt(1,branch.getBranchId());
        pstmt.setInt(2,book.getBookId());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            return rs.getInt("noOfCopies");
        }
        return 0;
    }

    public int NumberOfCopiesCheckedOut(LibraryBranch branch, Book book) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT count(bookId) as booksCheckedOut " +
                "FROM tbl_book_loans " +
                "WHERE branchId = ? AND bookId = ? AND dateIn IS NULL ");
        pstmt.setInt(1,branch.getBranchId());
        pstmt.setInt(2,book.getBookId());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            return rs.getInt("booksCheckedOut");
        }
        return 0;
    }

    public void updateDetails(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? " +
                "WHERE branchId = ?",
                new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
    }

    public void updateCopies(int branchId, int bookId, int noOfCopies) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_book_copies SET noOfCopies = ? WHERE branchId = ? AND bookId = ? ",
                new Object[] {noOfCopies, branchId, bookId});
    }

    public void addBookCopies(int branchId, int bookId, int noOfCopies) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_book_copies VALUES (?, ?, ?) ",
                new Object[] {bookId, branchId, noOfCopies});
    }

}
