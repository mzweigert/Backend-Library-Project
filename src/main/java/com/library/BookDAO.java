package com.library;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(Book book);
    List<Book> getBookByTitle(Book book);
    List<Author> getBookAuthors(Book book);
    List<Reader> getBookReaders(Book book);
    int updateBook(Book book);
    int deleteBook(Book book);
    int addBook(Book book);


}
