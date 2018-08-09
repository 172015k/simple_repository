package com.example.song.mybookinfo;

public class Row {
    private String bookTitle;
    private String bookAuthor;

    public Row(String bookTitle, String bookAuthor){
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public void setBookTitle(String bookTitle){
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String bookAuthor){
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle(){
        return bookTitle;
    }

    public String getBookAuthor(){
        return bookAuthor;
    }
}
