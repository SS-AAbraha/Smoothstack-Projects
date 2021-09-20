package com.library.dao;

import com.library.table.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO extends BaseDAO{

    // CREATE
    public void AddBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_borrower VALUES (?, ?, ?, ?)",
                new Object[] {borrower.getCardNo(), borrower.getName(), borrower.getAddress(), borrower.getPhone()});
    }
    // UPDATE
    public void Update(Borrower borrower) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_borrower " +
                "SET name = ? , address = ? , phone = ? " +
                "WHERE cardNo = ?", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone(), borrower.getCardNo()});
    }
    // DELETE
    public void Delete(Borrower borrower) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] {borrower.getCardNo()});
    }

    // READ
    public List<Borrower> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * FROM tbl_borrower", null);
    }

    @Override
    public List<Borrower> ExtractData(ResultSet rs) throws SQLException {
        List<Borrower> borrowerList = new ArrayList<>();
        while(rs.next()){
            Borrower temp = new Borrower();
            temp.setCardNo(rs.getInt("cardNo"));
            temp.setName(rs.getString("name"));
            temp.setAddress(rs.getString("address"));
            temp.setPhone(rs.getString("phone"));
            borrowerList.add(temp);
        }
        return borrowerList;
    }

    public Borrower getBorrowerByCardNo(int cardNo) throws SQLException, ClassNotFoundException {
        List<Borrower> borrowerList = Read("SELECT * FROM tbl_borrower WHERE cardNo = ? ", new Object[] {cardNo});

        if(borrowerList.size() <= 0)
            return null;
        else
            return borrowerList.get(0);
    }
}
