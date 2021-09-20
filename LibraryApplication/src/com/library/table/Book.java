package com.library.table;

public class Book {
    private int bookId;
    private String title;
    private Author author;
    private Genre genre;
    private int pubId;

    public Book(){
        this.author = new Author(0, "");
        this.genre = new Genre(0,"");
    }

    public Book(int bookId, String title, Author author, Genre genre, int pubId){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pubId = pubId;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author){ this.author = author; }

    public int getAuthorId() { return author.getAuthorId(); }
    public void setAuthorId(int authId) { this.author.setAuthorId(authId); }

    public String getAuthorName(){ return author.getAuthorName();  }
    public void setAuthorName(String authorName){ this.author.setAuthorName(authorName);    }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public int getGenreId() { return genre.getGenreId(); }
    public void setGenreId(int genreId) { this.genre.setGenreId(genreId); }

    public String getGenreName(){ return genre.getGenreName();  }
    public void setGenreName(String genreName){ this.genre.setGenreName(genreName);    }

    public int getPubId() { return pubId; }
    public void setPubId(int pubId) { this.pubId = pubId; }

    @Override
    public String toString() {
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("(").append(bookId).append(") ").append(title).append("\nAuthor: ").append(author.getAuthorName());
        if(genre.getGenreId() != 0)
            strOutput.append("\nGenre: ").append(genre.getGenreName());
        strOutput.append("\nPublisher ID: ").append(pubId);
        return strOutput.toString();
    }
}
