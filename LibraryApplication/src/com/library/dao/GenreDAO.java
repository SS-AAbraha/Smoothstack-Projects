package com.library.dao;

import com.library.table.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends BaseDAO<Genre> {

    // CREATE
    public void AddGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Save("INSERT INTO tbl_genre VALUES (?, ?)",
                new Object[] {genre.getGenreId(), genre.getGenreName()});
    }
    // UPDATE
    public void Update(Genre genre) throws SQLException, ClassNotFoundException {
        Save("UPDATE tbl_genre SET genre_name = ? " +
                "WHERE genre_id = ?", new Object[] {genre.getGenreName(),genre.getGenreId()});
    }
    // DELETE
    public void Delete(Genre genre) throws SQLException, ClassNotFoundException {
        Save("DELETE FROM tbl_genre WHERE genre_id = ? ", new Object[] {genre.getGenreId()});
    }

    // READ
    public List<Genre> Read() throws SQLException, ClassNotFoundException {
        return Read("SELECT * FROM tbl_genre", null);
    }

    @Override
    public List<Genre> ExtractData(ResultSet rs) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        while(rs.next()){
            Genre temp = new Genre();
            temp.setGenreId(rs.getInt("genre_id"));
            temp.setGenreName(rs.getString("genre_name"));
            genreList.add(temp);
        }
        return genreList;
    }


}
