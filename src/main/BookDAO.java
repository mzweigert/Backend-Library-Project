package main;

import main.domain.Book;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(int idBook);
    List<Book> getBookByTitle(String title);
    boolean updateBook(Book book);
    boolean deleteBook(Book book);
    boolean addBook(Book book);


}
