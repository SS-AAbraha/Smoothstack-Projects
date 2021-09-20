package com.library.dao;

import com.library.table.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends BaseDAO<Book>{

    // CREATE
    public void AddBook(Book book) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_book VALUES " +
                "(?, ?, ?)", new Object[] {book.getBookId(), book.getTitle(), book.getPubId()});
        Save("INSERT INTO tbl_book_authors VALUES (?,?)" , new Object[] {book.getBookId(), book.getAuthorId()});
        Save("INSERT INTO tbl_book_genres VALUES (?,?)" , new Object[] {book.getGenreId(), book.getBookId()});

    }
    // UPDATE
    public void Update(Book book) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_book " +
                "SET title = ? , pubId = ? " +
                "WHERE bookId = ?", new Object[] {book.getTitle(),book.getPubId(),book.getBookId()});
        Save("UPDATE tbl_book_authors SET authorId = ? WHERE bookId = ? "
                , new Object[] {book.getAuthorId(), book.getBookId()});
        Save("UPDATE tbl_book_genres SET genre_id = ? WHERE bookId = ? "
                , new Object[] {book.getGenreId(), book.getBookId()});
    }
    // DELETE
    public void Delete(Book book) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {book.getBookId()});
    }

    // READ
    public List<Book> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book bk, tbl_book_authors bk_aut, tbl_author aut, tbl_book_genres bk_gen, tbl_genre gen " +
                "WHERE bk.bookId = bk_aut.bookId AND bk_aut.authorId = aut.authorId " +
                "AND bk_gen.bookId = bk.bookId AND gen.genre_id = bk_gen.genre_id " +
                "ORDER BY bk.bookId ASC", null);
    }

    @Override
    public List<Book> ExtractData(ResultSet rs) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        while(rs.next()){
            Book temp = new Book();
            temp.setBookId(rs.getInt("bookId"));
            temp.setTitle(rs.getString("title"));
            temp.setAuthor(new Author(rs.getInt("authorId"), rs.getString("authorName")));
            temp.setGenre(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
            temp.setPubId(rs.getInt("pubId"));
            bookList.add(temp);
        }
        return bookList;
    }

    public List<Book> getBooksByBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book_copies bk_cp, tbl_book bk, tbl_book_authors bk_aut, tbl_author aut, tbl_book_genres bk_gen, tbl_genre gen " +
                "WHERE bk_cp.bookId =bk.bookId AND bk.bookId = bk_aut.bookId AND bk_aut.authorId = aut.authorId " +
                "AND bk_gen.bookId = bk.bookId AND gen.genre_id = bk_gen.genre_id " +
                "AND bk_cp.branchId = ? ", new Object[] {branch.getBranchId()});
    }
    public List<Book> getBooksByAuthor(Author author) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book bk, tbl_book_authors bk_aut, tbl_author aut, tbl_book_genres bk_gen, tbl_genre gen " +
                "WHERE bk.bookId = bk_aut.bookId AND bk_aut.authorId = aut.authorId " +
                "AND bk_gen.bookId = bk.bookId AND gen.genre_id = bk_gen.genre_id  AND aut.authorId = 2 " +
                "AND aut.authorId = ?", new Object[] {author.getAuthorId()});
    }
    public List<Book> getBooksByPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        return Read("SELECT * " +
                "FROM tbl_book bk, tbl_book_authors bk_aut, tbl_author aut, tbl_book_genres bk_gen, tbl_genre gen " +
                "WHERE bk.bookId = bk_aut.bookId AND bk_aut.authorId = aut.authorId " +
                "AND bk_gen.bookId = bk.bookId AND gen.genre_id = bk_gen.genre_id  " +
                "AND pub.publisherId = ?", new Object[] {publisher.getPublisherId()} );
    }

    public Book getBookById(int bookId) throws SQLException, ClassNotFoundException {
        List<Book> bookList = Read("SELECT * " +
                "FROM tbl_book bk, tbl_book_authors bk_aut, tbl_author aut, tbl_book_genres bk_gen, tbl_genre gen " +
                "WHERE bk.bookId = bk_aut.bookId AND bk_aut.authorId = aut.authorId "+
                "AND bk_gen.bookId = bk.bookId AND gen.genre_id = bk_gen.genre_id " +
                "AND bk.bookId = ?", new Object[] {bookId});

        if (bookList.size() <= 0)
            return  null;
        else
            return bookList.get(0);
    }
}
