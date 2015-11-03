package com.library.domain;

/**
 * Created by MATEUSZ on 2015-10-19.
 */

import java.io.Serializable;


public class BooksAuthors implements Serializable
{


    private int idBooksAuthors, idAuthor, idBook;

    public BooksAuthors()
    {

    }
    public BooksAuthors(int idAuthor, int idBook)
    {
        this.idAuthor = idAuthor;
        this.idBook = idBook;
    }


    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdBooksAuthors()
    {
        return idBooksAuthors;
    }

    public void setIdBooksAuthors(int idBooksAuthors)
    {
        this.idBooksAuthors = idBooksAuthors;
    }

}
