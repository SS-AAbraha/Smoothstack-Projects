package com.library.dao;

import com.library.table.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends BaseDAO{
    // CREATE
    public void AddAuthor(Author author) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_author VALUES (?, ?)",
                new Object[] {author.getAuthorId(), author.getAuthorName()});
    }
    // UPDATE
    public void Update(Author author) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_author SET authorName = ? " +
                "WHERE authorId = ?", new Object[] {author.getAuthorName(),author.getAuthorId()});
    }
    // DELETE
    public void Delete(Author author) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] {author.getAuthorId()});
    }

    // READ
    public List<Author> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * FROM tbl_author", null);
    }

    @Override
    public List<Author> ExtractData(ResultSet rs) throws SQLException {
        List<Author> authorList = new ArrayList<>();
        while(rs.next()){
            Author temp = new Author();
            temp.setAuthorId(rs.getInt("authorId"));
            temp.setAuthorName(rs.getString("authorName"));
            authorList.add(temp);
        }
        return authorList;
    }

    public Author getAuthorByBookId(int bookId) throws SQLException, ClassNotFoundException {
        List<Author> authorList = Read("SELECT * FROM tbl_book_authors WHERE bookId = ?", new Object[] {bookId});
        return authorList.get(0);
    }
}
