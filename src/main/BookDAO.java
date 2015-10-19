package main;

import main.domain.Book;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(Book book);
    List<Book> getBookByTitle(Book book);
    int updateBook(Book book);
    int deleteBook(Book book);
    int addBook(Book book);


}
