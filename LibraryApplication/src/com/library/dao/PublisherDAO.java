package com.library.dao;

import com.library.table.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO extends BaseDAO{

    // CREATE
    public void AddPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_publisher VALUES (?, ?, ?, ?)",
                new Object[] {publisher.getPublisherId(), publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
    }
    // UPDATE
    public void Update(Publisher publisher) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_publisher SET publisherName = ? , publisherAddress = ? , publisherPhone = ? " +
                        "WHERE publisherID = ?",
                new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
    }
    // DELETE
    public void Delete(Publisher publisher) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] {publisher.getPublisherId()});
    }

    // READ
    public List<Publisher> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * FROM tbl_publisher", null);
    }

    @Override
    public List<Publisher> ExtractData(ResultSet rs) throws SQLException {
        List<Publisher> publisherList = new ArrayList<>();
        while(rs.next()){
            Publisher temp = new Publisher();
            temp.setPublisherId(rs.getInt("publisherId"));
            temp.setPublisherName(rs.getString("publisherName"));
            temp.setPublisherAddress(rs.getString("publisherAddress"));
            temp.setPublisherPhone(rs.getString("publisherPhone"));
            publisherList.add(temp);
        }
        return publisherList;
    }

    public Publisher getPublisherById(int pubId) throws SQLException, ClassNotFoundException {
        List<Publisher> publisherList =  Read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[] {pubId});
        return publisherList.get(0);
    }


}
